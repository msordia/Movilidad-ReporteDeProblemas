package itesm.mx.movilidad_reportedeproblemas.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import itesm.mx.movilidad_reportedeproblemas.Models.Category;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;

//////////////////////////////////////////////////////////
//Clase: ReportAdapter
// Descripción: Adaptador de reportes.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public class ReportAdapter extends ArrayAdapter<Report> {
    public ReportAdapter(@NonNull Context context, @NonNull List<Report> reports) {
        super(context, 0, reports);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Report report = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_report_list, parent, false);

        TextView tvId = convertView.findViewById(R.id.text_reportListAdapter_id);
        TextView tvDate = convertView.findViewById(R.id.text_reportListAdapter_date);
        TextView tvCategory = convertView.findViewById(R.id.text_reportListAdapter_category);

        tvId.setText(Long.toString(report.getId()));
        tvDate.setText(report.getDate().toString());
        tvCategory.setText(report.getCategory().getName());

        ImageView ivStatus = convertView.findViewById(R.id.image_reportListAdapter_status);
        switch (report.getStatus()) {
            case Report.STATUS_PENDING:
                ivStatus.setImageResource(R.drawable.pending); break;
            case Report.STATUS_IN_PROCESS:
                ivStatus.setImageResource(R.drawable.in_process); break;
            case Report.STATUS_SUCCESS:
                ivStatus.setImageResource(R.drawable.success); break;
            case Report.STATUS_FAILURE:
                ivStatus.setImageResource(R.drawable.failure); break;
        }

        return convertView;
    }
}