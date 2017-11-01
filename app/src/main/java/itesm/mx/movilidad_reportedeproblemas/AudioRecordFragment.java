package itesm.mx.movilidad_reportedeproblemas;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager.IByteArrayManager;
import itesm.mx.movilidad_reportedeproblemas.Services.IContainer;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AudioRecordFragment extends android.app.Fragment {
    public static final int RequestPermissionCode = 1;

    private IByteArrayManager _byteArrayManager;
    private byte[] _bytes;

    ImageButton btnRecord;
    ImageButton btnPlay;
    TextView tvDuration;
    ImageButton btnCancel;

    public static AudioRecordFragment newInstance() {
        AudioRecordFragment fragment = new AudioRecordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_audio_record, container, false);

        btnRecord =  view.findViewById(R.id.button_audioRecord_record);
        btnPlay = view.findViewById(R.id.button_audioRecord_play);
        tvDuration = view.findViewById(R.id.text_audioRecord_duration);
        btnCancel = view.findViewById(R.id.button_audioRecord_cancel);

        btnPlay.setEnabled(false);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            boolean record = true;
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {
                if (checkPermission()) {
                    if (record) {
                        btnRecord.setImageResource(R.drawable.stop);
                    } else {
                        btnRecord.setImageResource(R.drawable.record);
                        btnPlay.setEnabled(true);
                    }
                    record = !record;
                } else
                    requestPermission();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _byteArrayManager.removeByteArray(_bytes);

                ViewParent parent = view.getParent();
                ((ViewGroup)parent).removeView(view);
            }
        });

        return view;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(getActivity(), "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void onEndRecording(byte[] bytes) {
        _byteArrayManager.removeByteArray(_bytes);
        _bytes = bytes;
        _byteArrayManager.addByteArray(_bytes);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IByteArrayManager) {
            _byteArrayManager = (IByteArrayManager) context;
        } else if (context instanceof IContainer) {
            _byteArrayManager = (IByteArrayManager)((IContainer) context).getComponent(IByteArrayManager.class, GenerateReportActivity.AUDIO_CONTAINER);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IByteArrayManager or IContainer");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _byteArrayManager = null;
    }

}
