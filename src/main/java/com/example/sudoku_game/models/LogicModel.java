package com.example.sudoku_game.models;

import com.example.sudoku_game.interfaces.ValidationInterface;
import com.example.sudoku_game.utils.GameTimer;
import com.example.sudoku_game.utils.ScoreManager;

/**
 * Modelo que contiene la lógica del juego Sudoku
 */
public class LogicModel {
    private BoardModel board;
    private GameTimer timer;
    private ScoreManager scoreManager;
    private ValidationInterface validator;
    private int currentDifficulty;
    private boolean gameInProgress;

    public LogicModel() {
        this.board = new BoardModel();
        this.timer = new GameTimer();
        this.scoreManager = new ScoreManager();
        this.gameInProgress = false;
        this.currentDifficulty = 1; // Dificultad fácil por defecto
    }

    /**
     * Establece la estrategia de validación a utilizar
     */
    public void setValidator(ValidationInterface validator) {
        this.validator = validator;
    }

    /**
     * Inicia un nuevo juego con la dificultad actual
     */
    public void startNewGame() {
        // Generar un nuevo tablero con la dificultad seleccionada
        board.generateBoard(currentDifficulty);

        // Reiniciar el temporizador
        timer.reset();
        timer.start();

        gameInProgress = true;
    }

    /**
     * Establece un valor en una celda específica
     * @return true si el movimiento es válido según la estrategia de validación
     */
    public boolean setCellValue(int row, int col, int value) {
        if (!gameInProgress || board.getCell(row, col).isLocked()) {
            return false;
        }

        // Validar el movimiento si hay un validador configurado
        if (validator != null && value != 0) {
            if (!validator.isValidMove(board, row, col, value)) {
                // Resaltar la celda como inválida
                board.getCell(row, col).setHighlighted(true);
                return false;
            }
        }

        // Establecer el valor y quitar el resaltado
        board.getCell(row, col).setValue(value);
        board.getCell(row, col).setHighlighted(false);

        // Comprobar si el juego ha terminado
        if (board.isBoardComplete()) {
            gameCompleted();
        }

        return true;
    }

    /**
     * Llamado cuando el juego se completa
     */
    private void gameCompleted() {
        timer.stop();
        gameInProgress = false;

        // Calcular la puntuación en función del tiempo y la dificultad
        int timeInSeconds = timer.getElapsedTimeInSeconds();
        int score = calculateScore(timeInSeconds);

        // Guardar la puntuación
        scoreManager.addScore(score, currentDifficulty);
    }

    /**
     * Calcula la puntuación basada en el tiempo y la dificultad
     */
    private int calculateScore(int timeInSeconds) {
        // Puntuación base según dificultad
        int baseScore = 0;
        switch (currentDifficulty) {
            case 1: // Fácil
                baseScore = 1000;
                break;
            case 2: // Medio
                baseScore = 2000;
                break;
            case 3: // Difícil
                baseScore = 3000;
                break;
        }

        // Penalización por tiempo: más tiempo = menos puntos
        // La fórmula se puede ajustar según necesidades
        int timePenalty = timeInSeconds * 2;

        // Calcular puntuación final (nunca menos de 0)
        return Math.max(0, baseScore - timePenalty);
    }

    /**
     * Verifica si hay conflictos en el tablero
     */
    public void checkConflicts() {
        if (validator != null) {
            for (int row = 0; row < board.getBoardSize(); row++) {
                for (int col = 0; col < board.getBoardSize(); col++) {
                    CellModel cell = board.getCell(row, col);
                    if (!cell.isEmpty() && !cell.isLocked()) {
                        boolean isValid = validator.isValidMove(board, row, col, cell.getValue());
                        cell.setHighlighted(!isValid);
                    }
                }
            }
        }
    }

    /**
     * Proporciona una pista revelando una celda
     */
    public boolean getHint() {
        // Implementar lógica para dar pistas
        // Por ejemplo, encontrar una celda vacía y revelar su valor correcto
        return false; // TODO: Implementar lógica de pistas
    }

    /**
     * Cambia la dificultad del juego
     */
    public void setDifficulty(int difficulty) {
        if (difficulty >= 1 && difficulty <= 3) {
            this.currentDifficulty = difficulty;
        }
    }

    /**
     * Devuelve el tablero actual
     */
    public BoardModel getBoard() {
        return board;
    }

    /**
     * Devuelve el temporizador del juego
     */
    public GameTimer getTimer() {
        return timer;
    }

    /**
     * Verifica si hay un juego en progreso
     */
    public boolean isGameInProgress() {
        return gameInProgress;
    }

    /**
     * Finaliza el juego actual
     */
    public void endGame() {
        timer.stop();
        gameInProgress = false;
    }

    /**
     * Devuelve la dificultad actual
     */
    public int getCurrentDifficulty() {
        return currentDifficulty;
    }

    /**
     * Clase interna que implementa la funcionalidad para
     * guardar y cargar partidas
     */
    public class GameSaver {
        public boolean saveGame(String fileName) {
            // Implementar lógica para guardar el estado del juego
            return false; // TODO: Implementar guardado
        }

        public boolean loadGame(String fileName) {
            // Implementar lógica para cargar el estado del juego
            return false; // TODO: Implementar carga
        }
    }
}
