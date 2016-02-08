/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.entity;

import java.util.List;

/**
 *
 * @author Janco1
 */
public class KopirovanieResult {

    public final long finishTime;
    public final int velkostSuboru;
    public final boolean uspesne;
    public final List<ClientData> clientData;

    public KopirovanieResult(long currentTimeMillis, int velkostSuboru, boolean uspesne, List<ClientData> clientData) {
        this.finishTime = currentTimeMillis;
        this.velkostSuboru = velkostSuboru;
        this.uspesne = uspesne;
        this.clientData = clientData.subList(0, clientData.size());
    }

}
