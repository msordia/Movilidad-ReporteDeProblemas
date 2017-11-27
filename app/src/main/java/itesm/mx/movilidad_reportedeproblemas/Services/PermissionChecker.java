package itesm.mx.movilidad_reportedeproblemas.Services;

import android.content.Context;
import android.content.pm.PackageManager;

//////////////////////////////////////////////////////////
//Clase: PermissionChecker
// Descripción: Esta clase checa los permisos.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class PermissionChecker {
    public static boolean checkPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private PermissionChecker(){}
}
