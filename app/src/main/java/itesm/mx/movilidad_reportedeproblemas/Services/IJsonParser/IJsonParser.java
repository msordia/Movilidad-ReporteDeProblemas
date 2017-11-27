package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

//////////////////////////////////////////////////////////
//Clase: IJsonParser
// Descripción: Interfaz de JsonParser
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public interface IJsonParser<T> {
    T parse(JSONObject json) throws JSONException, ParseException;
}
