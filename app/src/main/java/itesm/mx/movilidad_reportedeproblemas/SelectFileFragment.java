package itesm.mx.movilidad_reportedeproblemas;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import itesm.mx.movilidad_reportedeproblemas.Services.ICommentManager.IStringManager;
import itesm.mx.movilidad_reportedeproblemas.Services.IContainer;

public class SelectFileFragment extends android.app.Fragment {
    private final static String ARG_FILE = "filePath";

    private IStringManager _stringManager;
    private String _filePath;

    private TextView tvFilePath;

    public SelectFileFragment() { }

    public static SelectFileFragment newInstance(String filePath) {
        SelectFileFragment fragment = new SelectFileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILE, filePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            _filePath = getArguments().getString(ARG_FILE);
        }

        _stringManager.updateString(null, _filePath);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_select_file, container, false);

        tvFilePath = view.findViewById(R.id.text_attachFile_selected);
        tvFilePath.setText(_filePath);

        ImageButton btnCancel = view.findViewById(R.id.button_attachFile_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeView(view);
                _stringManager.removeString(_filePath);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IStringManager) {
            _stringManager = (IStringManager) context;
        } else if (context instanceof IContainer) {
            _stringManager = (IStringManager)((IContainer) context).getComponent(IStringManager.class, GenerateReportActivity.PATH_CONTAINER);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IStringManager or IContainer");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _stringManager = null;
    }
}
