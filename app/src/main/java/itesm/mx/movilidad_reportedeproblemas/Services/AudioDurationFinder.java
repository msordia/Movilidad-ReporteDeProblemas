package itesm.mx.movilidad_reportedeproblemas.Services;

//////////////////////////////////////////////////////////
//Clase: AudioDurationFinder
// Descripción: Se define la duración del audio
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////
public class AudioDurationFinder {
    private AudioDurationFinder() {}

    public static String getDurationString(int amountBytes) {
        double durationSeconds = amountBytes / 8000.0 / 2;
        int minutes = (int) durationSeconds / 60;
        double seconds = durationSeconds - minutes * 60;

        return String.format("%d:%05.2f", minutes, seconds);
    }
}
