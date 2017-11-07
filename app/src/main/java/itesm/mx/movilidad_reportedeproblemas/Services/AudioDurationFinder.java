package itesm.mx.movilidad_reportedeproblemas.Services;

/**
 * Created by juanc on 11/3/2017.
 */

public class AudioDurationFinder {
    private AudioDurationFinder() {}

    public static String getDurationString(int amountBytes) {
        double durationSeconds = amountBytes / 8000.0 / 2;
        int minutes = (int) durationSeconds / 60;
        double seconds = durationSeconds - minutes * 60;

        return String.format("%d:%05.2f", minutes, seconds);
    }
}
