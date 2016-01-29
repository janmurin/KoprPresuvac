/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.koprpresuvac;

/**
 *
 * @author Janco1
 */
class ClientData {

    final int startOffset;
    final int zapisanychOffset;
    final int chunkSize;

    public ClientData(int startOffset, int zapisanychOffset, int chunkSize) {
        this.startOffset = startOffset;
        this.zapisanychOffset = zapisanychOffset;
        this.chunkSize = chunkSize;
    }
    
    
}
