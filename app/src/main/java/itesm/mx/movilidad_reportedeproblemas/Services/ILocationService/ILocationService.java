package itesm.mx.movilidad_reportedeproblemas.Services.ILocationService;

//////////////////////////////////////////////////////////
//Clase: ILocationService
// Descripción: Interfaz para los servicios de localizacion
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


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
    void setLocation(double longitude, double latitude);
}
