/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.koprpresuvac;

import java.io.File;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Janco1
 */
public class Shared {

    public static final File VYBRANY_SUBOR=new File("C:\\Users\\Janco1\\Documents\\NetBeansProjects\\KoprPresuvac\\input\\shawshank.mp4");
    public static final int SERVER_PORT = 6880;
    public static final String SERVER_IP = "localhost";
    public static final int BUFFER_SIZE = 10*1024*1024;
    public static List<ClientData> clientData;
}