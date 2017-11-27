package itesm.mx.movilidad_reportedeproblemas.Services.IAudioPlayer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

//////////////////////////////////////////////////////////
//Clase: AudioPlayer
// Descripción: Reproductor de audio
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class AudioPlayer implements IAudioPlayer {
    @Override
    public void play(Context context, byte[] bytes) {
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bytes.length, AudioTrack.MODE_STATIC);
        audioTrack.write(bytes, 0, bytes.length);
        audioTrack.play();
    }
}
