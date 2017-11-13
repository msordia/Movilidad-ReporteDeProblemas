package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by juanc on 11/7/2017.
 */

public interface IJsonParser<T> {
    T parse(JSONObject json) throws JSONException, ParseException;
}
