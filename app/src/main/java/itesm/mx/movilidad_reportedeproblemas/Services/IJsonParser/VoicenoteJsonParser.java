package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import itesm.mx.movilidad_reportedeproblemas.Models.Voicenote;

//////////////////////////////////////////////////////////
//Clase: VoicenoteJsonParser
// Descripción: Json para notas de voz
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class VoicenoteJsonParser implements IJsonParser<Voicenote> {
    @Override
    public Voicenote parse(JSONObject json) throws JSONException, ParseException {
        json = json.getJSONObject("voicenote");

        Voicenote voicenote = new Voicenote();

        voicenote.setId(json.getLong("id"));
        voicenote.setReportId(json.getLong("reportId"));
        voicenote.setName(json.getString("name"));

        return voicenote;
    }
}
