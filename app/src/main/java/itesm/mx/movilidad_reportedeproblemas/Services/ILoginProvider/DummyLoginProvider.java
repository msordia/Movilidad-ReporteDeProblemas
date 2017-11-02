package itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider;

import itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider.ILoginProvider;

/**
 * Created by juanc on 10/31/2017.
 */

public class DummyLoginProvider implements ILoginProvider {
    @Override
    public boolean login(String username, String passwrod) {
        return true;
    }

    @Override
    public String getCurrentUserId() {
        return "A01175826";
    }

    @Override
    public void logout() {

    }
}
