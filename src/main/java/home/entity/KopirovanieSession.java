/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.entity;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @author Janco1
 */
public class KopirovanieSession {
    
    public final long elapsedTime;
    public final CopyOnWriteArrayList<ClientData> clientData;

    public KopirovanieSession(long elapsedTime, CopyOnWriteArrayList<ClientData> clientData) {
        this.elapsedTime = elapsedTime;
        this.clientData = clientData;
    }
    
    
}
