package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.IFileSaver.DownloadsFileSaver;
import itesm.mx.movilidad_reportedeproblemas.Services.IFileSaver.IFileSaver;
import itesm.mx.movilidad_reportedeproblemas.Services.WebFileReader;

public class FileDownloadFragment extends android.app.Fragment {
    private static final String ARG_NAME = "name";

    private String _name;

    private IFileSaver _fileSaver = new DownloadsFileSaver();

    private ImageButton btnDownload;
    private TextView tvName;

    public FileDownloadFragment() { }

    public static FileDownloadFragment newInstance(String name) {
        FileDownloadFragment fragment = new FileDownloadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _name = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_download, container, false);

        btnDownload = view.findViewById(R.id.button_downloadFile);
        tvName = view.findViewById(R.id.text_downloadFile_name);

        tvName.setText(_name);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloafFile();
            }
        });

        return view;
    }

    private void DownloafFile(){
        btnDownload.setEnabled(false);
        Toast.makeText(getActivity(), "Descarga comenzada", Toast.LENGTH_SHORT).show();
        WebFileReader.readFile(WebFileReader.BASE_URL + WebFileReader.DIR_FILE + _name, new WebFileReader.WebFileHandler() {
            @Override
            public void handle(byte[] bytes) {
                _fileSaver.SaveFile(bytes, _name, new IFileSaver.IFileSavedHandler() {
                    @Override
                    public void handle(int statusCode) {
                        btnDownload.setEnabled(true);
                        if (statusCode == IFileSaver.STATUS_SUCCESS) {
                            Toast.makeText(getActivity(), "Archivo descargado exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Archivo no descargado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, getActivity());
            }
        });
    }
}
