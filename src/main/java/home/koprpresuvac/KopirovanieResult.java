/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.koprpresuvac;

import java.util.List;

/**
 *
 * @author Janco1
 */
class KopirovanieResult {

    final long finishTime;
    final int velkostSuboru;
    final boolean uspesne;
    final List<ClientData> clientData;

    KopirovanieResult(long currentTimeMillis, int velkostSuboru, boolean uspesne, List<ClientData> clientData) {
        this.finishTime = currentTimeMillis;
        this.velkostSuboru = velkostSuboru;
        this.uspesne = uspesne;
        this.clientData = clientData.subList(0, clientData.size());
    }

}
