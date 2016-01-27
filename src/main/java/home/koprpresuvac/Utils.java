/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.koprpresuvac;

import java.text.DecimalFormat;

/**
 *
 * @author Janco1
 */
public class Utils {

    public static final DecimalFormat df = new DecimalFormat("##.##");

    public static String getETAtime(long startTime, int pocet, int vsetkych) {
        // debilny java.Date mi pridava 1 hodinu navyse.... wtf
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        long trvanie = System.currentTimeMillis() - startTime;
//        double priemer = trvanie / (double) pocetInzeratov;
//        long ostava = (long) ((vsetkych - pocetInzeratov) * priemer);
//        return sdf.format(new Date(ostava));

        double rychlost = ((System.currentTimeMillis() - startTime) / 1000.0) / pocet;
        double etaTime = (vsetkych - pocet) * rychlost;
        int hodinE = (int) ((etaTime) / (3600));
        int minutE = (int) ((etaTime) / (60));
        int sekundE = (int) ((etaTime));
        sekundE %= 60;
        minutE %= 60;
        String hodinStringE = "" + hodinE;
        if (hodinE < 10) {
            hodinStringE = "0" + hodinE;
        }
        String minutStringE = "" + minutE;
        if (minutE < 10) {
            minutStringE = "0" + minutE;
        }
        String sekundStringE = "" + sekundE;
        if (sekundE < 10) {
            sekundStringE = "0" + sekundE;
        }
        return (hodinStringE + ":" + minutStringE + ":" + sekundStringE);
    }

    public static String getElapsedTime(long startTime) {
        double elapsedTime = ((System.currentTimeMillis() - startTime) / 1000.0);
        int hodinE = (int) ((elapsedTime) / (3600));
        int minutE = (int) ((elapsedTime) / (60));
        int sekundE = (int) ((elapsedTime));
        sekundE %= 60;
        minutE %= 60;
        String hodinStringE = "" + hodinE;
        if (hodinE < 10) {
            hodinStringE = "0" + hodinE;
        }
        String minutStringE = "" + minutE;
        if (minutE < 10) {
            minutStringE = "0" + minutE;
        }
        String sekundStringE = "" + sekundE;
        if (sekundE < 10) {
            sekundStringE = "0" + sekundE;
        }
        //System.out.println("ETA:" + (hodinStringE + ":" + minutStringE + ":" + sekundStringE));
        return (hodinStringE + ":" + minutStringE + ":" + sekundStringE);
    }

    static String getRychlost(long startTime, int stiahnutych) {
        double elapsedTime = ((System.currentTimeMillis() - startTime) / 1000.0);
        int sekundE = (int) ((elapsedTime));
        double stiahnutychMB = stiahnutych / (1024 * 1024);
        double megabajtovZaSekundu = stiahnutychMB / sekundE;
        return df.format(megabajtovZaSekundu) + " MB/s";
    }
}
