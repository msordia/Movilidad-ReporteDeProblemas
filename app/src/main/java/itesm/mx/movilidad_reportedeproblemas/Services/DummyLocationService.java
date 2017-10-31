package itesm.mx.movilidad_reportedeproblemas.Services;

/**
 * Created by juanc on 10/31/2017.
 */

public class DummyLocationService implements ILocationService {
    @Override
    public Location getLocation() {
        Location location = new Location();
        location.Longitude = 0;
        location.Latitude = 0;
        return location;
    }
}
