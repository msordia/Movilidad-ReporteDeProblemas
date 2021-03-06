package itesm.mx.movilidad_reportedeproblemas.Services.ILocationService;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import itesm.mx.movilidad_reportedeproblemas.Services.ILocationService.ILocationService;

//////////////////////////////////////////////////////////
//Clase: LocationService
// Descripción: Servicios de ubicacion
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class LocationService implements ILocationService {
    private double _longitude = Long.MIN_VALUE;
    private double _latitude = Long.MIN_VALUE;


    Context context;
    public LocationService(Context c){
        context = c;
    }

    public Location getLocation()
    {
        if (_longitude != Long.MIN_VALUE) {
            Location loc = new Location();
            loc.setLatitude(_latitude);
            loc.setLongitude(_longitude);
            return loc;
        }

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show();
            return null;
        }

        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, new LocationListener() {
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
            });
            Location l = new Location();
            android.location.Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            l.setLatitude(loc.getLatitude());
            l.setLongitude(loc.getLongitude());
            return l;
        }
        else{
            Toast.makeText(context, "El GPS no ha sido activado.",Toast.LENGTH_LONG).show();
        }

        return null;
    }

    @Override
    public void setLocation(double longitude, double latitude) {
        _longitude = longitude;
        _latitude = latitude;

    }
}
