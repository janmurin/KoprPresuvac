/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.koprpresuvac;

import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janco1
 */
class RafWriterThread implements Runnable {

    private final RandomAccessFile raf;
    private final CountDownLatch writerGate;

    

    RafWriterThread(RandomAccessFile raf, CountDownLatch writerGate) {
        this.raf = raf;
        this.writerGate=writerGate;
    }

    @Override
    public void run() {
        System.out.println("spustam RafWriterThread");
        try {
            WriteTask task = Shared.writeTasky.take();
            while (task != null) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("thread interrupted");
                }
                if (task.zapisanychOffset < 0) {
                    // koncime so zapisovanim
                    break;
                }
                raf.seek(task.zapisanychOffset);
                raf.write(task.data);
                Shared.clientData.set(task.id, new ClientData(task.startOffset, task.zapisanychOffset + task.data.length, task.chunkSize));
                System.out.println("RafWriterThread: zapisanych bajtov: " + task.data.length);
//                            raf.seek(zapisanychOffset);
//            if (nacitanychBajtov == buffer.length) {
//                raf.write(buffer);
//            } else {
//                raf.write(Arrays.copyOfRange(buffer, 0, nacitanychBajtov));
//            }
//            zapisanychOffset += nacitanychBajtov;
//            // nevadi ze tento kod je v synchronized??
//            Shared.clientData.set(id, new ClientData(startOffset, zapisanychOffset, chunkSize));

                task = Shared.writeTasky.take();
            }
        } catch (InterruptedException e) {
            // prerusenie, koncime
            System.out.println("RafWriterThread: interrupted");
        } catch (Exception e) {
            Logger.getLogger(RafWriterThread.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            writerGate.countDown();
        }
    }

}
