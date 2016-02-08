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
class WriteTask {

    final byte[] data;
    final int startOffset;
    final int chunkSize;
    int zapisanychOffset;
    final int id;

    public WriteTask(byte[] data, int startOffset, int zapisanychOffset, int chunkSize, int id) {
        this.data = data;
        this.startOffset = startOffset;
        this.zapisanychOffset = zapisanychOffset;
        this.chunkSize = chunkSize;
        this.id=id;
    }

}
