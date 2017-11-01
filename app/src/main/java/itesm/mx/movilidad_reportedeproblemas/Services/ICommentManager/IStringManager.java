package itesm.mx.movilidad_reportedeproblemas.Services.ICommentManager;

import java.util.Collection;

public interface IStringManager {
    Collection<String> getStrings();
    void updateString(String previousString, String newString);
    void removeString(String string);
}
