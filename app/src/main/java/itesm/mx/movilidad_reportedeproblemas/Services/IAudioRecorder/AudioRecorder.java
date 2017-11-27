package itesm.mx.movilidad_reportedeproblemas.Services.IAudioRecorder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//////////////////////////////////////////////////////////
//Clase: AudioRecorder
// Descripción: Grabador de audio
// Autor: Armando Aguiar y Juan Carlos Guzman
// Fecha de creación: 03/11/2017
// Fecha de última modificación: 23/11/2017
//////////////////////////////////////////////////////////


public class AudioRecorder implements IAudioRecorder {
    private AudioRecord recorder = null;
    private Thread recordingThread = null;
    private boolean isRecording = false;
    byte[] _bytes;
    Lock _lock = new ReentrantLock();

    int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    int BytesPerElement = 2; // 2 bytes in 16bit format

    private void startRecording() {
        int rate = 8000;
        short audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        short channelConfig = AudioFormat.CHANNEL_IN_MONO;
        int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);
        recorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, rate, channelConfig, audioFormat, bufferSize);

        recorder.startRecording();
        isRecording = true;
        recordingThread = new Thread(new Runnable() {
            public void run() {
                writeAudioDataToFile();
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }

    //convert short to byte
    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;

    }

    private void writeAudioDataToFile() {
        _lock.lock();
        try {
            short sData[] = new short[BufferElements2Rec];
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            while (isRecording) {
                recorder.read(sData, 0, BufferElements2Rec);
                byte bData[] = short2byte(sData);
                os.write(bData, 0, BufferElements2Rec * BytesPerElement);
            }
            try {
                _bytes = os.toByteArray();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            _lock.unlock();
        }
    }

    private void stopRecording() {
        // stops the recording activity
        if (null != recorder) {
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;
            recordingThread = null;
        }

        _lock.lock();
        byte[] bytes = _bytes;
        _lock.unlock();
    }


    @Override
    public void start() {
        startRecording();
    }

    @Override
    public byte[] stop() {
        stopRecording();
        return _bytes;
    }
}
