package itesm.mx.movilidad_reportedeproblemas.Services;

import itesm.mx.movilidad_reportedeproblemas.Models.Report;

//////////////////////////////////////////////////////////
//Clase: StatusParser
// Descripción: Esta clase cambia el status.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class StatusParser {
    private StatusParser() {}

    public static String Parse(int status) {
        switch (status) {
            case Report.STATUS_IN_PROCESS:
                return "En proceso";
            case Report.STATUS_FAILURE:
                return "No se encontro el problema";
            case Report.STATUS_PENDING:
                return "Pendiente";
            case Report.STATUS_SUCCESS:
                return "Arreglado";
        }
        return "Estátus inválido: " + status;
    }
}
