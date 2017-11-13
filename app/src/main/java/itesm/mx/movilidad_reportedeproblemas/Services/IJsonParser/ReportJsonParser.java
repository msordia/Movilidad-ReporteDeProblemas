package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import itesm.mx.movilidad_reportedeproblemas.Models.Report;

public class ReportJsonParser implements IJsonParser<Report> {
    private SimpleDateFormat _parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Report parse(JSONObject json) throws JSONException, ParseException {
        json = json.getJSONObject("report");

        Report report = new Report();
        report.setId(json.getLong("id"));
        report.setCategoryId(json.getLong("categoryId"));
        report.setUserId(json.getString("userId"));
        report.setLongitude(json.getDouble("longitude"));
        report.setLatitude(json.getDouble("latitude"));
        report.setStatus(json.getInt("status"));
        report.setDate(_parser.parse(json.getString("date")));

        return report;
    }
}
