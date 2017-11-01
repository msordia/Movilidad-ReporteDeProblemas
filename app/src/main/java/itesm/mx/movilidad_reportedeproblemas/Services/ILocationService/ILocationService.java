package itesm.mx.movilidad_reportedeproblemas.Services.ILocationService;

/**
 * Created by juanc on 10/31/2017.
 */

public interface ILocationService {
    public class Location{
        private double longitude;
        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    Location getLocation();
}
