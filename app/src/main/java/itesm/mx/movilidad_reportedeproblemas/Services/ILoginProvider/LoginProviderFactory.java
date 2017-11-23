package itesm.mx.movilidad_reportedeproblemas.Services.ILoginProvider;

/**
 * Created by juanc on 11/23/2017.
 */

public final class LoginProviderFactory {
    public static ILoginProvider getDefaultInstance() {
        return DummyLoginProvider.getInstance();
    }

    private LoginProviderFactory() {}
}
