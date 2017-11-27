package itesm.mx.movilidad_reportedeproblemas.Services.ICommentManager;

import java.util.Collection;

//////////////////////////////////////////////////////////
//Clase: IStringManager
// Descripción: Interfaz de string manager
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public interface IStringManager {
    Collection<String> getStrings();
    void updateString(String previousString, String newString);
    void removeString(String string);
}
