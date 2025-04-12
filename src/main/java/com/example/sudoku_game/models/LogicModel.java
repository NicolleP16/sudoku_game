package com.example.sudoku_game.models;
import com.example.sudoku_game.interfaces.ValidationInterface;

/**
 * Modelo que contiene la lógica del juego Sudoku.
 * Esta clase actúa como intermediario entre la interfaz de usuario y el modelo del tablero,
 * gestionando el estado del juego, validando movimientos y controlando el flujo del juego.
 *
 * @author com.example.sudoku_game
 */
public class LogicModel {
    private BoardModel board;
    private ValidationInterface validator;
    private boolean gameInProgress;

    /**
     * Constructor que inicializa un nuevo modelo de lógica del juego.
     * Crea un nuevo tablero y establece el estado del juego como no iniciado.
     */
    public LogicModel() {
        this.board = new BoardModel();
        this.gameInProgress = false;
    }

    /**
     * Establece el validador para comprobar movimientos.
     *
     * @param validator El validador a utilizar
     */
    public void setValidator(ValidationInterface validator) {
        this.validator = validator;
    }

    /**
     * Inicia un nuevo juego.
     * Genera un nuevo tablero y establece el estado del juego como en progreso.
     */
    public void startNewGame() {
        board.generateBoard();
        gameInProgress = true;
    }

    /**
     * Establece un valor en una celda específica del tablero.
     * Comprueba si el movimiento es válido antes de establecer el valor. Si el valor
     * no es válido, resalta la celda para indicar un error. Si el tablero se completa
     * después del movimiento, finaliza el juego.
     *
     * @param row Índice de la fila
     * @param col Índice de la columna
     * @param value Valor a establecer (0 para borrar el valor)
     * @return {@code true} si el valor se estableció correctamente, {@code false} en caso contrario
     */
    public boolean setCellValue(int row, int col, int value) {
        if (!gameInProgress || board.getCell(row, col).isLocked()) return false;

        if (validator != null && value != 0) {
            if (!validator.isValidMove(board, row, col, value)) {
                board.getCell(row, col).setHighlighted(true);
                return false;
            }
        }

        board.getCell(row, col).setValue(value);
        board.getCell(row, col).setHighlighted(false);

        if (board.isBoardComplete()) gameCompleted();
        return true;
    }

    /**
     * Método interno que se llama cuando el juego se completa.
     */
    private void gameCompleted() {
        gameInProgress = false;
    }

    /**
     * Calcula la puntuación basada en el tiempo empleado.
     * La puntuación se calcula como 2000 menos el doble del tiempo en segundos,
     * con un mínimo de 0 puntos.
     * @param timeInSeconds Tiempo empleado en segundos
     * @return La puntuación calculada
     */
    private int calculateScore(int timeInSeconds) {
        return Math.max(0, 2000 - (timeInSeconds * 2));
    }

    /**
     * Verifica conflictos en el tablero y resalta las celdas con valores inválidos.
     * Este método comprueba cada celda no bloqueada y no vacía para verificar si su
     * valor actual es válido según las reglas del Sudoku. Las celdas con valores
     * inválidos se resaltan.
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
     * Obtiene el modelo del tablero.
     *
     * @return El modelo del tablero actual
     */
    public BoardModel getBoard() {
        return board;
    }

    /**
     * Verifica si hay un juego en progreso.
     *
     * @return {@code true} si hay un juego en progreso, {@code false} en caso contrario
     */
    public boolean isGameInProgress() {
        return gameInProgress;
    }

    /**
     * Establece el estado del juego como finalizado.
     */
    public void endGame() {
        gameInProgress = false;
    }
}