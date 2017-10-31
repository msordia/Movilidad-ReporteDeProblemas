package itesm.mx.movilidad_reportedeproblemas.Services;

/**
 * Created by juanc on 10/31/2017.
 */

public interface ILocationService {
    public class Location{
        public double Longitude;
        public double Latitude;
    }



    Location getLocation();
}
