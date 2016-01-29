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
class ProgressbarStatus {

    int kopirovanieProgress;
    String rychlost;
    String ostava;
    String uplynulo;

    ProgressbarStatus(int kopirovanieProgress, String elapsedTime, String etAtime, String rychlost) {
        this.kopirovanieProgress=kopirovanieProgress;
        this.ostava=etAtime;
        this.uplynulo=elapsedTime;
        this.rychlost=rychlost;
    }
    
}
