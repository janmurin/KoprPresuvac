/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.entity;

/**
 *
 * @author Janco1
 */
public class ClientData {

    public final int startOffset;
    public final int zapisanychOffset;
    public final int chunkSize;

    public ClientData(int startOffset, int zapisanychOffset, int chunkSize) {
        this.startOffset = startOffset;
        this.zapisanychOffset = zapisanychOffset;
        this.chunkSize = chunkSize;
    }
    
    
}
