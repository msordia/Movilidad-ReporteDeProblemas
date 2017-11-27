package itesm.mx.movilidad_reportedeproblemas.Services;

import android.graphics.Color;

import itesm.mx.movilidad_reportedeproblemas.Models.Report;

//////////////////////////////////////////////////////////
//Clase: StatusColotMatcher
// Descripción: Esta clase cambia el color del estatus.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


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
