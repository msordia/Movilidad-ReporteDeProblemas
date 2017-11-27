package itesm.mx.movilidad_reportedeproblemas.Services.IAudioRecorder;

//////////////////////////////////////////////////////////
//Clase: IAudioRecorder
// Descripción: Interfaz de grabador de audio
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////



public interface IAudioRecorder {
    void start();
    byte[] stop();
}
