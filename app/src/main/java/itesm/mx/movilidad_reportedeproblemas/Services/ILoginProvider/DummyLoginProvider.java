package itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider;

import itesm.mx.movilidad_reportedeproblemas.Models.User;
import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;

/**
 * Created by juanc on 10/31/2017.
 */

public class DummyLoginProvider implements ILoginProvider {
    private static DummyLoginProvider _instance = new DummyLoginProvider();

    public static DummyLoginProvider getInstance() {
        return _instance;
    }

    private DummyLoginProvider() {};

    @Override
    public boolean login(String username, String passwrod) {
        return true;
    }

    @Override
    public User getCurrentUser() {
        return new User("A01175826", "Juan Carlos Guzman");
    }

    @Override
    public void logout() {

    }
}
