package itesm.mx.movilidad_reportedeproblemas.Services;

import android.content.Context;
import android.content.pm.PackageManager;

public class PermissionChecker {
    public static boolean checkPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private PermissionChecker(){}
}
