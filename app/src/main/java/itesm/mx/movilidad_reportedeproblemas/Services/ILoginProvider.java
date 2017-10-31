package itesm.mx.movilidad_reportedeproblemas.Services;

/**
 * Created by juanc on 10/31/2017.
 */

public interface ILoginProvider {
    boolean login(String username, String passwrod);
    void logout();
}
