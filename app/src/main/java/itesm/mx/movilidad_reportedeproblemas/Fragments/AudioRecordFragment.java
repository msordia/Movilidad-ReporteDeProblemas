package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.TextView;

import itesm.mx.movilidad_reportedeproblemas.Activities.GenerateReportActivity;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.AudioDurationFinder;
import itesm.mx.movilidad_reportedeproblemas.Services.IAudioPlayer.AudioPlayer;
import itesm.mx.movilidad_reportedeproblemas.Services.IAudioPlayer.IAudioPlayer;
import itesm.mx.movilidad_reportedeproblemas.Services.IAudioRecorder.AudioRecorder;
import itesm.mx.movilidad_reportedeproblemas.Services.IAudioRecorder.IAudioRecorder;
import itesm.mx.movilidad_reportedeproblemas.Services.IBitmapManager.IByteArrayManager;
import itesm.mx.movilidad_reportedeproblemas.Services.IContainer;
import itesm.mx.movilidad_reportedeproblemas.Services.PermissionChecker;
//////////////////////////////////////////////////////////
//Clase: AudioRecordFragment
// Descripción: Fragmento para agregar sonido.
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public class AudioRecordFragment extends android.app.Fragment {
    private IByteArrayManager _byteArrayManager;
    private IAudioRecorder _recorder = new AudioRecorder();
    private IAudioPlayer _player = new AudioPlayer();

    ImageButton btnRecord;
    ImageButton btnPlay;
    TextView tvDuration;
    ImageButton btnCancel;

    byte[] _bytes;

    public static AudioRecordFragment newInstance() {
        AudioRecordFragment fragment = new AudioRecordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_audio_record, container, false);

        btnRecord =  view.findViewById(R.id.button_audioRecord_record);
        btnPlay = view.findViewById(R.id.button_audioRecord_play);
        tvDuration = view.findViewById(R.id.text_audioRecord_duration);
        btnCancel = view.findViewById(R.id.button_audioRecord_cancel);

        btnPlay.setEnabled(false);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            boolean record = true;
            @Override
            public void onClick(View view) {
                if (!PermissionChecker.checkPermission(getActivity(), Manifest.permission.RECORD_AUDIO)) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                    return;
                }

                if (record) {
                    _recorder.start();
                    btnRecord.setImageResource(R.drawable.stop);
                } else {
                    byte[] bytes = _recorder.stop();

                    _byteArrayManager.removeByteArray(_bytes);
                    _bytes = bytes;
                    _byteArrayManager.addByteArray(_bytes);

                    tvDuration.setText(AudioDurationFinder.getDurationString(bytes.length));

                    btnRecord.setImageResource(R.drawable.record);
                    btnPlay.setEnabled(true);
                }

                record = !record;

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _player.play(getActivity(), _bytes);
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
