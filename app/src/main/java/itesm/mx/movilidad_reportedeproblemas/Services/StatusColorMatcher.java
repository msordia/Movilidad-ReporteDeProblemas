package itesm.mx.movilidad_reportedeproblemas.Services;

import android.graphics.Color;

import itesm.mx.movilidad_reportedeproblemas.Models.Report;

/**
 * Created by juanc on 11/17/2017.
 */

public class StatusColorMatcher {
    public static int getColor(int status) {
        switch (status) {
            case Report.STATUS_FAILURE: return Color.RED;
            case Report.STATUS_SUCCESS: return Color.GREEN;
            case Report.STATUS_IN_PROCESS: return Color.BLUE;
            case Report.STATUS_PENDING: return Color.YELLOW;
            default: return Color.GRAY;
        }
    }
}
