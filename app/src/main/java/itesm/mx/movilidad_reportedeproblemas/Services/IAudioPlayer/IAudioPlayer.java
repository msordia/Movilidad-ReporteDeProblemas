package itesm.mx.movilidad_reportedeproblemas.Services.IAudioPlayer;

import android.content.Context;

//////////////////////////////////////////////////////////
//Clase: AudioPlayer
// Descripción: Interfaz de reproductor de audio
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////

public interface IAudioPlayer {
    void play(Context context, byte[] bytes);
}
