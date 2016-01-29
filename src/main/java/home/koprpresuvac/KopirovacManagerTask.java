/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.koprpresuvac;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janco1
 */
class KopirovacManagerTask implements Callable<KopirovanieResult> {

    private final int soketov;
    private String destFolder;
    private String nazovSuboru;
    private int velkostSuboru;
    private File destFile;
    private RandomAccessFile raf;
    private Semaphore writeSemafor;
    private boolean resumujeme;
    private final CopyOnWriteArrayList<ClientData> oldClientData;

    KopirovacManagerTask(int soketov, String destFolder, CopyOnWriteArrayList<ClientData> clientData) {
        this.soketov = soketov;
        this.destFolder = destFolder;
        this.oldClientData = clientData;
        if (clientData != null) {
            // resumujeme
            System.out.println("KopirovacManagerTask: resumujeme predchadzajuce kopirovanie");
            this.resumujeme = true;
        }
        Shared.clientData = new CopyOnWriteArrayList<>();
    }

    @Override
    public KopirovanieResult call() throws Exception {
        initNazovVelkostServerSuboru();
        System.out.println("KopirovacManagerTask spusteny, kopirujem subor: " + nazovSuboru + " do " + destFolder);
        ExecutorService downloaderExecutor = Executors.newFixedThreadPool(soketov);
        List<Future<?>> downloadFutures = new ArrayList<>();
        CountDownLatch downloaderGate = new CountDownLatch(soketov);

        try {
            int clientChunkSize = (int) Math.ceil(velkostSuboru / (double) soketov);
            System.out.println("KopirovacManagerTask: chunk size: " + clientChunkSize);

            // spustime downloaderov
            // kazdemu downloaderovi nastavit offset a velkost dat ktore stahuje
            for (int i = 0; i < soketov; i++) {
                if (!resumujeme) {
                    // nove kopirovanie takze spocitame offsety a velkosti chunkov clientom
                    int startOffset = i * clientChunkSize;
                    int chunk = Math.min(clientChunkSize, velkostSuboru - i * clientChunkSize); // posledny client ma velkost chunku <= chunksize
                    Shared.clientData.add(new ClientData(startOffset, startOffset, chunk));
                    DownloadClient downloader = new DownloadClient(downloaderGate, i, raf, writeSemafor, startOffset, chunk);
                    Future<?> searcherFuture = downloaderExecutor.submit(downloader);
                    downloadFutures.add(searcherFuture);
                } else {
                    int startOffset = oldClientData.get(i).zapisanychOffset;
                    int chunk = (oldClientData.get(i).startOffset + oldClientData.get(i).chunkSize) - oldClientData.get(i).zapisanychOffset;
                    Shared.clientData.add(new ClientData(startOffset, startOffset, chunk));
                    DownloadClient downloader = new DownloadClient(downloaderGate, i, raf, writeSemafor, startOffset, chunk);
                    Future<?> searcherFuture = downloaderExecutor.submit(downloader);
                    downloadFutures.add(searcherFuture);
                }
            }

            downloaderGate.await();

            System.out.println("KopirovacManagerTask: kopirovanie ukoncene");
        } catch (InterruptedException e) {
            System.out.println("KopirovacManagerTask: PRERUSENIE " + e.getMessage() + " zatvaram downloadery");
            // zatvorime downloader executora
            downloaderExecutor.shutdownNow();
//            try {
//                downloaderExecutor.awaitTermination(10, TimeUnit.SECONDS);
//            } catch (InterruptedException interruptedException) {
//                // ak sa do 10s neskoncia downloadery, asi su v deadlocku
//            }
            // pockame na vsetky downloadery ci su skoncene
            System.out.println("KopirovacManagerTask: cakam na downloadery ");
//            for (Future<?> future : downloadFutures) {
//                while (!future.isDone()) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException interruptedException) {
//                        System.out.println("Thread.sleep(100) interruptedException");
//                    }
//                }
//            }
            downloaderGate.await();
            System.out.println("KopirovacManagerTask: vsetky downloadery interruptnute");

            return new KopirovanieResult(System.currentTimeMillis(), velkostSuboru, false, Shared.clientData);// true ako uspesne
        } catch (Exception e) {
            //Logger.getLogger(KopirovacManagerTask.class.getName()).log(Level.SEVERE, null, e);
            // nastala situacia ktora by nikdy nemala nastat tak ju posunieme o urovne vyssie
            throw e;
        } finally {
            raf.close();
        }
        System.out.println("KopirovacManagerTask: riadne skoncenie ulohy, shutting downloader executor");
        downloaderExecutor.shutdownNow();// vsetky ulohy su uz skoncene takze teoreticky toto netreba

        // pripravime kopirovanie result
        return new KopirovanieResult(System.currentTimeMillis(), velkostSuboru, true, Shared.clientData);// true ako uspesne
    }

    private void initNazovVelkostServerSuboru() {
        Socket socket = null;
        try {
            // 1. pripojim sa na server
            int serverPort = Shared.SERVER_PORT;
            String ip = Shared.SERVER_IP;
            socket = new Socket(ip, serverPort);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            // 2. spytam sa na velkost suboru, ze poslem offset -1
            output.writeInt(-1);
            velkostSuboru = input.readInt();
            int dlzka = input.readInt();
            byte[] data = new byte[dlzka];
            for (int i = 0; i < dlzka; i++) {
                data[i] = input.readByte();
            }
            nazovSuboru = new String(data);
            System.out.println("KopirovacManagerTask from server: subor je: " + nazovSuboru + ", " + velkostSuboru);

            // 3. vytvorime dany subor
            this.destFile = new File(destFolder + "\\" + nazovSuboru);
            raf = new RandomAccessFile(destFile, "rw");
            raf.setLength(velkostSuboru);
            // inicializujeme semafor na zapisovanie do suboru
            writeSemafor = new Semaphore(1);
        } catch (Exception e) {
            Logger.getLogger(KopirovacManagerTask.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {/*close failed*/
                }
            }
        }
    }

}
