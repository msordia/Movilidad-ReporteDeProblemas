package itesm.mx.movilidad_reportedeproblemas.Services;

/**
 * Created by juanc on 10/31/2017.
 */

public class DummyLoginProvider implements ILoginProvider {
    @Override
    public boolean login(String username, String passwrod) {
        return true;
    }

    @Override
    public void logout() {

    }
}
