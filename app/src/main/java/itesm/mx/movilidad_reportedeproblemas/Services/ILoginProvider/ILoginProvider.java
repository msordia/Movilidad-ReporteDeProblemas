package itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider;

import android.content.Context;

import itesm.mx.movilidad_reportedeproblemas.Models.User;

/**
 * Created by juanc on 10/31/2017.
 */

public interface ILoginProvider {
    void login(String username, String password, ILoginHandler handler);
    User getCurrentUser();
    void logout();

    public interface ILoginHandler {
        void handle(String username, String name, boolean result);
        Context getContext();
    }

}
