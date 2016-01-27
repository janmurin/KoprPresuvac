/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pom;

import home.koprpresuvac.Utils;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SimpleFileClient {

    public final static int SOCKET_PORT = 13267;      // you may change this
    public final static String SERVER = "127.0.0.1";  // localhost
    public final static String FILE_TO_RECEIVED = "C:\\Users\\Janco1\\Desktop\\video.mp4";  // you may change this, I give a
    // different name because i don't want to
    // overwrite the one used by server...

    public static int BUFFER_SIZE = 10 * 1024 * 1024;
    // should bigger than the file to be downloaded

    public static void main(String[] args) throws IOException {

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");

            long start = System.currentTimeMillis();
            DataInputStream input = new DataInputStream(sock.getInputStream());
            DataOutputStream output = new DataOutputStream(sock.getOutputStream());
            // posleme offset z ktoreho chceme zacat a kooko dat chceme
            output.writeInt(0);// offset
            output.writeInt(1000000000);// velkost dat
            int readInt = input.readInt();
            System.out.println("readInt: "+readInt);
            readInt = input.readInt();
            System.out.println("readInt: "+readInt);
            // receive file
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            System.out.println("buffer size: " + BUFFER_SIZE);
            //input.close();
            byte[] buffer = new byte[BUFFER_SIZE];

            // budeme citat dokym nam budu chodit data
            int startOffset = 0; // pre raf
            int bytesRead;
            int current = 0;
            // dokym nemame plny buffer tak citame
            while (true) {
                bytesRead = is.read(buffer, current, (buffer.length - current));
                if (bytesRead >= 0) {
                    current += bytesRead;
                } else {
                    // skonceny stream
                    bos.write(buffer, 0, current);
                    bos.flush();
                    System.out.println("skonceny stream, zapisanych bajtov: "+current);
                    break;
                }
                // kontrola ci uz neni plny buffer
                if (current == buffer.length) {
                    bos.write(buffer, 0, current);
                    bos.flush();
                    System.out.println("zapisanych bajtov: "+current);
                    current=0;// vynulujeme buffer
                }
            }

            System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read) " + Utils.getElapsedTime(start));
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (sock != null) {
                sock.close();
            }
        }
    }

}
