package itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider;

import itesm.mx.movilidad_reportedeproblemas.Models.User;

/**
 * Created by juanc on 10/31/2017.
 */

public interface ILoginProvider {
    boolean login(String username, String passwrod);
    User getCurrentUser();
    void logout();
    User getUser(String userId);
}
