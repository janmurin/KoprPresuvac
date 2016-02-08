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
public class ProgressbarStatus {

    public int kopirovanieProgress;
    public String rychlost;
    public String ostava;
    public String uplynulo;

    public ProgressbarStatus(int kopirovanieProgress, String elapsedTime, String etAtime, String rychlost) {
        this.kopirovanieProgress=kopirovanieProgress;
        this.ostava=etAtime;
        this.uplynulo=elapsedTime;
        this.rychlost=rychlost;
    }
    
}
