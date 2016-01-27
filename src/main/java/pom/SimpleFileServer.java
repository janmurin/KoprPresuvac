/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pom;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleFileServer {

    public final static int SOCKET_PORT = 13267;  // you may change this
    public final static String FILE_TO_SEND = "C:\\Users\\Janco1\\Documents\\NetBeansProjects\\KoprPresuvac\\input\\skuska3.mp4";  // you may change this

    public static void main(String[] args) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;
        try {
            servsock = new ServerSocket(SOCKET_PORT);
            // nacitat subor do ramky
            File myFile = new File(FILE_TO_SEND);
            System.out.println("file length: " + myFile.length());
            byte[] fileBytes = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(fileBytes, 0, fileBytes.length);
            bis.close();
            // cakat na pripojenia a posielat data
            while (true) {
                System.out.println("Waiting... verzia 2");
                try {
                    sock = servsock.accept();
                    System.out.println("Accepted connection : " + sock);
                    DataInputStream input = new DataInputStream(sock.getInputStream());
                    DataOutputStream output = new DataOutputStream(sock.getOutputStream());
                    int offset = input.readInt();
                    System.out.print("chceju offset: " + offset);
                    output.writeInt(offset);
                    int velkost = input.readInt();
                    System.out.println(" chceju velkost dat: " + velkost);
                    output.writeInt(velkost);
                    // send file
                    int skutocnaVelkost = Math.min(velkost, fileBytes.length);
                    os = sock.getOutputStream();
                    System.out.println("Sending " + FILE_TO_SEND + "(" + skutocnaVelkost + " bytes)");
//                    DataOutputStream output = new DataOutputStream(sock.getOutputStream());
//                    output.writeInt(mybytearray.length); // velkost suboru

                    os.write(fileBytes, offset, skutocnaVelkost);
                    os.flush();
                    System.out.println("Done.");
                } finally {
                    if (os != null) {
                        os.close();
                    }
                    if (sock != null) {
                        sock.close();
                    }
                }
            }
        } finally {
            if (servsock != null) {
                servsock.close();
            }
        }
    }
}
