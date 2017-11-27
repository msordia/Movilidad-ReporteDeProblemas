package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import itesm.mx.movilidad_reportedeproblemas.Models.Image;

//////////////////////////////////////////////////////////
//Clase: ImageJsonParser
// Descripción: Se ponen los detalles de la imagen.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class ImageJsonParser implements IJsonParser<Image> {
    @Override
    public Image parse(JSONObject json) throws JSONException, ParseException {
        json = json.getJSONObject("image");

        Image image = new Image();

        image.setId(json.getLong("id"));
        image.setReportId(json.getLong("reportId"));
        image.setName(json.getString("name"));

        return image;
    }
}
