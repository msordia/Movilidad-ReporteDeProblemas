package itesm.mx.movilidad_reportedeproblemas.Services.ICommentManager;
import java.util.Collection;
import java.util.HashMap;

//////////////////////////////////////////////////////////
//Clase: HashStringManager
// Descripción: Manager de los comentarios
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class HashStringManager implements IStringManager {
    private HashMap<String, Integer> _comments = new HashMap<>();
    @Override
    public Collection<String> getStrings() {
        return _comments.keySet();
    }

    @Override
    public void updateString(String previousString, String newString) {
        removeString(previousString);
        if (!_comments.containsKey(newString))
            _comments.put(newString, 0);
        _comments.put(newString, _comments.get(newString) + 1);
    }

    @Override
    public void removeString(String string) {
        if (_comments.containsKey(string)) {
            _comments.put(string, _comments.get(string) - 1);
            if (_comments.get(string) == 0)
                _comments.remove(string);
        }
    }
}
