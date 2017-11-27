package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import itesm.mx.movilidad_reportedeproblemas.Models.Category;

//////////////////////////////////////////////////////////
//Clase: CategoryJsonParser
// Descripción: JsonParser categoria
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class CategoryJsonParser implements IJsonParser<Category> {
    public Category parse(JSONObject json) throws JSONException {
        json = json.getJSONObject("category");

        Category category = new Category(
                json.getLong("id"),
                json.getString("name")
        );

        return category;
    }
}
