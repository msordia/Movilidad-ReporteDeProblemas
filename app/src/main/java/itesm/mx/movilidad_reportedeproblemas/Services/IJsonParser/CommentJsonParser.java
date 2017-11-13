package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import itesm.mx.movilidad_reportedeproblemas.Models.Comment;

/**
 * Created by juanc on 11/7/2017.
 */

public class CommentJsonParser implements IJsonParser<Comment> {
    public Comment parse(JSONObject json) throws JSONException {
        json = json.getJSONObject("comment");

        Comment comment = new Comment();
        comment.setId(json.getLong("id"));
        comment.setReportId(json.getLong("reportId"));
        comment.setBody(json.getString("body"));

        return comment;
    }
}
