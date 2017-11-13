package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import itesm.mx.movilidad_reportedeproblemas.Models.Voicenote;

/**
 * Created by juanc on 11/7/2017.
 */

public class VoicenoteJsonParser implements IJsonParser<Voicenote> {
    @Override
    public Voicenote parse(JSONObject json) throws JSONException, ParseException {
        json = json.getJSONObject("voicenote");

        Voicenote voicenote = new Voicenote();

        voicenote.setId(json.getLong("id"));
        voicenote.setReportId(json.getLong("reportId"));
        voicenote.setBytes(StringByteParser.parseToBytes(json.getString("bytes")));

        return voicenote;
    }
}
