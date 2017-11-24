package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import itesm.mx.movilidad_reportedeproblemas.Activities.ReportDetailActivity;
import itesm.mx.movilidad_reportedeproblemas.Adapters.ReportAdapter;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;

//////////////////////////////////////////////////////////
//Clase: ReportListFragment
// Descripción: Fragmento con la lista de reportes.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class ReportListFragment extends ListFragment {
    private static final String ARG_LIST = "list";

    private ArrayList<Report> _reports;

    public ReportListFragment() {
    }

    public static ReportListFragment newInstance(ArrayList<Report> reports) {
        ReportListFragment fragment = new ReportListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LIST, reports);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _reports = getArguments().getParcelableArrayList(ARG_LIST);
            Collections.reverse(_reports);
        } else {
            _reports = new ArrayList<>();
        }

        setListAdapter(new ReportAdapter(getActivity(), _reports));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Report report = (Report) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), ReportDetailActivity.class);
        intent.putExtra(ReportDetailActivity.EXTRA_REPORT, report);
        startActivity(intent);
    }
}
