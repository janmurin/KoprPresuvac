/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.koprpresuvac;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janco1
 */
public class DownloadClient implements Runnable {

    private final CountDownLatch downloaderGate;
    private final int id;
    private final RandomAccessFile raf;
    private final Semaphore writeSemafor;
    private DataInputStream input;
    private DataOutputStream output;
    private Socket clientSocket;
    private final int startOffset;
    private final int chunkSize;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private int zapisanychOffset;

    DownloadClient(CountDownLatch downloaderGate, int i, RandomAccessFile raf, Semaphore writeSemafor, int offset, int chunkSize) {
        this.downloaderGate = downloaderGate;
        this.id = i;
        this.raf = raf;
        this.writeSemafor = writeSemafor;
        this.startOffset = offset;
        this.zapisanychOffset = offset; // offset po ktory su data zapisane
        this.chunkSize = chunkSize;
        clientSocket = null;
        try {
            clientSocket = new Socket(Shared.SERVER_IP, Shared.SERVER_PORT);
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(DownloadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("DownloadClient[" + id + "]: zacina download: startOffset: [" + startOffset + "] chunkSize: [" + chunkSize + "] ");
    }

    @Override
    public void run() {
        byte[] buffer = new byte[Shared.BUFFER_SIZE];
        int nacitanychBajtov = 0;
        try {
            // 1. posleme serveru ake data chceme
            output.writeInt(startOffset);// offset
            output.writeInt(chunkSize);// velkost dat

            InputStream is = clientSocket.getInputStream();
            System.out.println("DownloadClient[" + id + "]: buffer size: " + Shared.BUFFER_SIZE);

            // 2. citame zo socketu dokym chodia data a zapisujeme do offsetu dokym neskonci stream
            int bytesRead;
            boolean jeVCykle = false;
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("thread interrupted");
                }
                if (!jeVCykle) {
                    System.out.println("DownloadClient[" + id + "]: je v cykle");
                    jeVCykle = true;
                }
                //System.out.println("DownloadClient[" + id + "]: cita inputstream");
                // DEADLOCK: ma tomto citani zo streamu sa niekedy vlakno zasekne!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                bytesRead = is.read(buffer, nacitanychBajtov, (buffer.length - nacitanychBajtov));
                if (bytesRead >= 0) {
                    nacitanychBajtov += bytesRead;
                    //System.out.println("DownloadClient[" + id + "]: nacitanychBajtov: " + nacitanychBajtov);
                } else {
                    // skonceny stream
                    zapisData(buffer, nacitanychBajtov);
                    System.out.println("DownloadClient[" + id + "]: skonceny stream, zapisanych bajtov: " + nacitanychBajtov + " celkovo: " + (zapisanychOffset - startOffset));
                    break;
                }
                // kontrola ci uz neni plny buffer
                if (nacitanychBajtov == buffer.length) {
                    // plny buffer
                    //System.out.println("Client: [" + id + "] chce zapisat data: ");
                    zapisData(buffer, nacitanychBajtov);
                    //System.out.println("Client: [" + id + "] zapisanych bajtov: " + nacitanychBajtov);
                    nacitanychBajtov = 0;// vynulujeme buffer
                }
            }

        } catch (InterruptedException ex) {
            //Logger.getLogger(DownloadClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DownloadClient[" + id + "]: PRERUSENIE");
        } catch (Exception e) {
            Logger.getLogger(DownloadClient.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(DownloadClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            downloaderGate.countDown();
            System.out.println("DownloadClient[" + id + "]: skonceny");
        }
    }

    private void zapisData(byte[] buffer, int nacitanychBajtov) throws InterruptedException {
        if (nacitanychBajtov == 0) {
            return;
        }
        try {
            System.out.println("DownloadClient[" + id + "]: caka na writeSemafor.acquire()");
            writeSemafor.acquire();
            System.out.println("DownloadClient[" + id + "]: dostal semafor a zapisuje: " + nacitanychBajtov);
            // ked uz zapisujem data tak ich zapisem
            //synchronized (this) {
            raf.seek(zapisanychOffset);
            if (nacitanychBajtov == buffer.length) {
                raf.write(buffer);
            } else {
                raf.write(Arrays.copyOfRange(buffer, 0, nacitanychBajtov));
            }
            zapisanychOffset += nacitanychBajtov;
            // nevadi ze tento kod je v synchronized??
            Shared.clientData.set(id, new ClientData(startOffset, zapisanychOffset, chunkSize));
            //}
            writeSemafor.release();
        } catch (IOException ex) {
            Logger.getLogger(DownloadClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            //Logger.getLogger(DownloadClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("DownloadClient[" + id + "]: interrupt v zapisData");
            throw new InterruptedException();
        }
    }

}
