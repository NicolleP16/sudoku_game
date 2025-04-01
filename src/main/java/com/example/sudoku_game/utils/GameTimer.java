package com.example.sudoku_game.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase que gestiona el tiempo de juego para el Sudoku
 */
public class GameTimer {
    private Timer timer;
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;
    private TimerTask timerTask;

    // Interfaz para notificar actualizaciones de tiempo
    public interface TimerUpdateListener {
        void onTimerUpdate(int seconds);
    }

    private TimerUpdateListener updateListener;

    /**
     * Constructor que inicializa el temporizador
     */
    public GameTimer() {
        this.startTime = 0;
        this.elapsedTime = 0;
        this.isRunning = false;
    }

    /**
     * Establece un listener para recibir actualizaciones del temporizador
     * @param listener El objeto que implementa la interfaz TimerUpdateListener
     */
    public void setUpdateListener(TimerUpdateListener listener) {
        this.updateListener = listener;
    }

    /**
     * Inicia el temporizador
     */
    public void start() {
        if (isRunning) {
            return;
        }

        isRunning = true;
        startTime = System.currentTimeMillis() - elapsedTime;

        // Configurar un timer que actualiza cada segundo
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                elapsedTime = System.currentTimeMillis() - startTime;

                // Notificar al listener si existe
                if (updateListener != null) {
                    updateListener.onTimerUpdate(getElapsedTimeInSeconds());
                }
            }
        };

        // Programar la tarea para que se ejecute cada segundo
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    /**
     * Detiene el temporizador
     */
    public void stop() {
        if (!isRunning) {
            return;
        }

        isRunning = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Pausa el temporizador sin restablecer el tiempo transcurrido
     */
    public void pause() {
        if (!isRunning) {
            return;
        }

        isRunning = false;
        elapsedTime = System.currentTimeMillis() - startTime;

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Restablece el temporizador a cero
     */
    public void reset() {
        stop();
        elapsedTime = 0;
    }

    /**
     * Devuelve el tiempo transcurrido en milisegundos
     * @return tiempo en milisegundos
     */
    public long getElapsedTimeInMillis() {
        if (isRunning) {
            return System.currentTimeMillis() - startTime;
        } else {
            return elapsedTime;
        }
    }

    /**
     * Devuelve el tiempo transcurrido en segundos
     * @return tiempo en segundos
     */
    public int getElapsedTimeInSeconds() {
        return (int) (getElapsedTimeInMillis() / 1000);
    }

    /**
     * Devuelve el tiempo formateado como minutos:segundos
     * @return Cadena con formato "MM:SS"
     */
    public String getFormattedTime() {
        int totalSeconds = getElapsedTimeInSeconds();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Verifica si el temporizador está en funcionamiento
     * @return true si está en funcionamiento, false en caso contrario
     */
    public boolean isRunning() {
        return isRunning;
    }
}