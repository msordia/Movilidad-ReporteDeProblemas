package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import itesm.mx.movilidad_reportedeproblemas.Adapters.ReportAdapter;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;

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
}
