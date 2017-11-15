package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import itesm.mx.movilidad_reportedeproblemas.Models.UploadedFile;

/**
 * Created by juanc on 11/7/2017.
 */

public class FileJsonParser implements IJsonParser<UploadedFile> {
    public UploadedFile parse(JSONObject json)  throws JSONException, ParseException {
        json = json.getJSONObject("file");

        UploadedFile file = new UploadedFile();

        file.setId(json.getLong("id"));
        file.setReportId(json.getLong("reportId"));
        file.setName(json.getString("name"));

        return file;
    }
}
