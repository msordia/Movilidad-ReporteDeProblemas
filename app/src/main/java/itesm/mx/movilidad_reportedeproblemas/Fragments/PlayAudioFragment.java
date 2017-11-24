package itesm.mx.movilidad_reportedeproblemas.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import itesm.mx.movilidad_reportedeproblemas.Models.Voicenote;
import itesm.mx.movilidad_reportedeproblemas.R;
import itesm.mx.movilidad_reportedeproblemas.Services.AudioDurationFinder;
import itesm.mx.movilidad_reportedeproblemas.Services.IAudioPlayer.AudioPlayer;
import itesm.mx.movilidad_reportedeproblemas.Services.IAudioPlayer.IAudioPlayer;

//////////////////////////////////////////////////////////
//Clase: PlayAudioFragment
// Descripción: Fragmento para reproducir audio
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class PlayAudioFragment extends android.app.Fragment {
    private static final String ARG_AUDIO = "audio";

    private IAudioPlayer _audioPlayer = new AudioPlayer();

    private Voicenote _voicenote;

    public PlayAudioFragment() { }

    public static PlayAudioFragment newInstance(Voicenote voicenote) {
        PlayAudioFragment fragment = new PlayAudioFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_AUDIO, voicenote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _voicenote = getArguments().getParcelable(ARG_AUDIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_audio, container, false);

        if (_voicenote == null)
            _voicenote = new Voicenote(new byte[0]);

        TextView tvDuration = view.findViewById(R.id.text_playAudio_duration);
        tvDuration.setText(AudioDurationFinder.getDurationString(_voicenote.getBytes().length));

        ImageButton btnPlay = view.findViewById(R.id.button_playAudio_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _audioPlayer.play(getActivity(), _voicenote.getBytes());
            }
        });

        return view;
    }
}
