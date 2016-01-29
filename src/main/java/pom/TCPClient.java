/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pom;

// TCPClient.java
// A client program implementing TCP socket
import home.koprpresuvac.Shared;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

    public static void main(String args[]) {// arguments supply message and hostname of destination  
        Socket s = null;
        try {
            int serverPort = 6880;
            String ip = "localhost";
            //String data = "Hello, How are you?";

            s = new Socket(ip, serverPort);
            DataInputStream input = new DataInputStream(s.getInputStream());
            DataOutputStream output = new DataOutputStream(s.getOutputStream());

            // spytam sa na velkost suboru
            output.writeLong(-1L);
            output.flush();
            int dlzka = input.readInt();
            byte[] data = new byte[dlzka];
            for (int i = 0; i < dlzka; i++) {
                data[i] = input.readByte();
            }
            String nazov = new String(data);
            long velkostSuboru = input.readLong();
            System.out.println("subor je: " + nazov + ", " + velkostSuboru);
            File destFile = new File("C:\\Users\\Janco1\\Desktop\\" + nazov);
            System.out.println("destfile: "+destFile.getAbsolutePath());
//            RandomAccessFile raf = new RandomAccessFile(destFile, "rw");
//            raf.setLength(velkostSuboru);
            FileOutputStream fos=new FileOutputStream(destFile);

        //byte[] buffer = new byte[(int)velkostSuboru];

//        int count;
//        while ((count = in.read(bytes)) > 0) {
//            out.write(bytes, 0, count);
//        }
            
            for (long off = 0; off < velkostSuboru; off += Shared.BUFFER_SIZE) {
                System.out.print("requesting offset: " + off);
                output.writeLong(off);
                output.flush();
                int length = input.readInt();
                if (length != data.length) {
                    data = new byte[length];
                }
                input.read(data);
                fos.write(data, 0, data.length);
//                raf.seek(off);
//                raf.write(data);
                System.out.println(", zapisanych bajtov: " + length);
            }
            //raf.close();
//            //Step 1 send length
//            System.out.println("Length" + data.length());
//            output.writeInt(data.length());
//            //Step 2 send length
//            System.out.println("Writing.......");
//            output.writeBytes(data); // UTF is a string encoding
//
//            //Step 1 read length
//            int nb = input.readInt();
//            byte[] digit = new byte[nb];
//            //Step 2 read byte
//            for (int i = 0; i < nb; i++) {
//                digit[i] = input.readByte();
//            }
//
//            String st = new String(digit);
//            System.out.println("Received: " + st);
        } catch (Exception e) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {/*close failed*/
                }
            }
        }
    }
}
