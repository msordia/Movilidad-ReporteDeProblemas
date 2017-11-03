package itesm.mx.movilidad_reportedeproblemas.Services;

import itesm.mx.movilidad_reportedeproblemas.Models.Report;

public class StatusParser {
    private StatusParser() {}

    public static String Parse(int status) {
        switch (status) {
            case Report.STATUS_IN_PROCESS:
                return "En proceso";
            case Report.STATUS_FAILURE:
                return "No se encontr el problema";
            case Report.STATUS_PENDING:
                return "Pendiente";
            case Report.STATUS_SUCCESS:
                return "Arreglado";
        }
        return "Estátus inválido: " + status;
    }
}
