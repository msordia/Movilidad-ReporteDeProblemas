package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import itesm.mx.movilidad_reportedeproblemas.Activities.GenerateReportActivity;
import itesm.mx.movilidad_reportedeproblemas.Models.Report;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager.IByteArrayManager;
import itesm.mx.movilidad_reportedeproblemas.Services.IContainer;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.IDatabaseProvider;
import itesm.mx.movilidad_reportedeproblemas.Services.IDatabaseProvider.WebDatabaseProvider;

//////////////////////////////////////////////////////////
//Clase: MarkReportAsStartedFragment
// Descripción: Fragmento para marcar los reportes como listos.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class MarkReportAsStartedFragment extends android.app.Fragment {
    private static final String ARG_REPORT_ID = "reportId";

    private long reportId;

    private IListener _listener;
    private IDatabaseProvider _db = new WebDatabaseProvider();

    private Button btnUpdate;

    public MarkReportAsStartedFragment() {
        // Required empty public constructor
    }

    public static MarkReportAsStartedFragment newInstance(long reportId) {
        MarkReportAsStartedFragment fragment = new MarkReportAsStartedFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_REPORT_ID, reportId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reportId = getArguments().getLong(ARG_REPORT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mark_report_as_started, container, false);

        btnUpdate = view.findViewById(R.id.button_markAsStarted);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _db.updateStatus(reportId, Report.STATUS_IN_PROCESS, new IDatabaseProvider.IDbHandler<Boolean>() {
                    @Override
                    public void handle(Boolean result) {
                        _listener.handle(result);
                    }
                });
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IListener) {
            _listener = (IListener) context;
        } else if (context instanceof IContainer) {
            _listener = (IListener)((IContainer) context).getComponent(IListener.class, GenerateReportActivity.AUDIO_CONTAINER);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IByteArrayManager or IContainer");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    public interface IListener {
        void handle(boolean success);
    }
}
