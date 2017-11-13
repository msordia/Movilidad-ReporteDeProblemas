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

    String user;

    @Override
    public void login(String username, String password, ILoginHandler handler) {
        user = username;
        handler.handle(username, password, true);
    }

    @Override
    public User getCurrentUser() {
        return new User(user, user);
    }

    @Override
    public void logout() {
        user = "";
    }
}
