package home.koprpresuvac;
// TCPServer.java
// A server program implementing TCP socket

import pom.*;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pom.SimpleFileServer.FILE_TO_SEND;

//http://www.dreamincode.net/forums/topic/139154-transfer-a-file-over-tcp/
public class FileServer {

    static File vybranySubor;

    public static void main(String args[]) {
        try {
            // nacitat subor do ramky
            vybranySubor = Shared.VYBRANY_SUBOR;
            System.out.println("server loading file " + vybranySubor);
            System.out.println("file length: " + vybranySubor.length());
            if (vybranySubor.length() > Integer.MAX_VALUE) {
                System.out.println("program nepodporuje subory vecsie ako " + Integer.MAX_VALUE);
                System.exit(0);
            }
            byte[] fileBytes = new byte[(int) vybranySubor.length()];
            FileInputStream fis = new FileInputStream(vybranySubor);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(fileBytes, 0, fileBytes.length);
            bis.close();
            // spustit server
            int serverPort = Shared.SERVER_PORT;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("file server start listening... ... ...");

            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket, fileBytes);
                System.out.println("otvaram socket: " + clientSocket);
                c.start();
            }
        } catch (IOException e) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

class Connection extends Thread {

    DataInputStream input;
    DataOutputStream output;
    Socket clientSocket;
    private byte[] fileBytes;

    Connection(Socket clientSocket, byte[] fileBytes) {
        try {
            this.clientSocket = clientSocket;
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
            this.fileBytes = fileBytes;
        } catch (IOException e) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void run() {
        try {
            // pripojil sa na server client, bud chce vediet len velkost suboru alebo chce data
            // je to na jednorazove pouzitie
            int offset = input.readInt();
            if (offset < 0) {
                // odoslem velkost suboru a nazov
                System.out.println("client chce vediet velkost suboru a nazov");
                output.writeInt(fileBytes.length);
                String nazov = FileServer.vybranySubor.getName();
                output.writeInt(nazov.length());
                output.write(nazov.getBytes());
            } else {
                // client chce rovno data od nejakeho offsetu
                int velkost = input.readInt();
                System.out.println("offset: " + offset + " velkost dat: " + velkost);
                OutputStream os = clientSocket.getOutputStream();
                System.out.println("Sending " + FILE_TO_SEND + "(" + velkost + " bytes)");
                os.write(fileBytes, offset, Math.min(velkost, fileBytes.length - offset));
                os.flush();
                System.out.println("Done.");
            }
            output.close();
            input.close();
            System.out.println("Fileserver: zatvaram socket " + clientSocket);
            System.out.println("");

        } catch (Exception e) {
            if (clientSocket.isClosed()) {
                System.out.println("clientSocket: " + clientSocket + " closed");
            } else {
                Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/
                Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
