package itesm.mx.movilidad_reportedeproblemas.Services.ILocationService;

/**
 * Created by juanc on 10/31/2017.
 */

public class DummyLocationService implements ILocationService {
    @Override
    public Location getLocation() {
        Location location = new Location();
        location.setLongitude(0);
        location.setLatitude(0);
        return location;
    }

    @Override
    public void setLocation(double longitude, double latitude) {

    }
}
