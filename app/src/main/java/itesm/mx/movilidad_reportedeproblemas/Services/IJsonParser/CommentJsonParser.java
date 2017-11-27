package itesm.mx.movilidad_reportedeproblemas.Services.IJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import itesm.mx.movilidad_reportedeproblemas.Models.Comment;

//////////////////////////////////////////////////////////
//Clase: CommentJsonParser
// Descripción: Comentarios Json
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


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
