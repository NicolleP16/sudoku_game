package com.example.sudoku_game.interfaces;

import com.example.sudoku_game.models.BoardModel;
import com.example.sudoku_game.models.CellModel;

/**
 * Implementación estándar de la interfaz de validación para el Sudoku 6x6
 *  * @author Juan Pablo Escamilla
 */
public class ValidationClass implements ValidationInterface {

    private static final int BOARD_SIZE = 6;
    private static final int BOX_HEIGHT = 2;
    private static final int BOX_WIDTH = 3;

    @Override
    public boolean isValidMove(BoardModel board, int row, int col, int value) {
        // Si el valor es 0 (celda vacía), siempre es válido
        if (value == 0) {
            return true;
        }

        // Verificar si el valor está en el rango permitido (1-6)
        if (value < 1 || value > BOARD_SIZE) {
            return false;
        }

        // Guardar el valor actual y establecerlo temporalmente a 0
        // Esto permite validar correctamente al reemplazar un valor existente
        int currentValue = board.getCell(row, col).getValue();
        board.getCell(row, col).setValue(0);

        // Verificar si el valor ya existe en la misma fila
        for (int c = 0; c < BOARD_SIZE; c++) {
            if (board.getCell(row, c).getValue() == value) {
                // Restaurar el valor original antes de retornar
                board.getCell(row, col).setValue(currentValue);
                return false;
            }
        }

        // Verificar si el valor ya existe en la misma columna
        for (int r = 0; r < BOARD_SIZE; r++) {
            if (board.getCell(r, col).getValue() == value) {
                // Restaurar el valor original antes de retornar
                board.getCell(row, col).setValue(currentValue);
                return false;
            }
        }

        // Verificar si el valor ya existe en la misma caja 2x3
        int boxRowStart = (row / BOX_HEIGHT) * BOX_HEIGHT;
        int boxColStart = (col / BOX_WIDTH) * BOX_WIDTH;

        for (int r = boxRowStart; r < boxRowStart + BOX_HEIGHT; r++) {
            for (int c = boxColStart; c < boxColStart + BOX_WIDTH; c++) {
                if (board.getCell(r, c).getValue() == value) {
                    // Restaurar el valor original antes de retornar
                    board.getCell(row, col).setValue(currentValue);
                    return false;
                }
            }
        }

        // Restaurar el valor original antes de retornar
        board.getCell(row, col).setValue(currentValue);

        // Si pasa todas las verificaciones, el movimiento es válido
        return true;
    }

    @Override
    public boolean isValidBoard(BoardModel board) {
        // Verificar que cada celda tenga un valor válido
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int value = board.getCell(row, col).getValue();

                // Si la celda está vacía, el tablero no está completo
                if (value == 0) {
                    return false;
                }

                // Guardar el valor actual
                int currentValue = value;

                // Temporalmente establecer la celda a vacía para validar
                board.getCell(row, col).setValue(0);

                // Verificar si el valor es válido en esa posición
                boolean isValid = isValidMove(board, row, col, currentValue);

                // Restaurar el valor original
                board.getCell(row, col).setValue(currentValue);

                if (!isValid) {
                    return false;
                }
            }
        }

        return true;
    }
}