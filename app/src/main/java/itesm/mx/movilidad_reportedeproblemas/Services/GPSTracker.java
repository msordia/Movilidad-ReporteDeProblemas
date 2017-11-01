package itesm.mx.movilidad_reportedeproblemas.Services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by armando on 31/10/17.
 */

public class GPSTracker implements ILocationService, LocationListener {

    Context context;
    public GPSTracker(Context c){
        context = c;
    }

    public Location getLocation()
    {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show();
            Location l = new Location();
            l.Latitude = 0;
            l.Longitude = 0;


        }
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
            Location l = new Location();
            android.location.Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            l.Latitude = loc.getLatitude();
            l.Longitude = loc.getLongitude();

            return l;
        }
        else{
            Toast.makeText(context, "Activa el GPS",Toast.LENGTH_LONG).show();

        }
        return null;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
