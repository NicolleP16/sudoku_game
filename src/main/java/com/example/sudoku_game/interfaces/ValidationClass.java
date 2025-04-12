package com.example.sudoku_game.interfaces;

import com.example.sudoku_game.models.BoardModel;
import com.example.sudoku_game.models.CellModel;

/**
 * Implementación estándar de la interfaz de validación para el Sudoku 6x6
 * @author Juan Pablo Escamilla
 */
public class ValidationClass implements ValidationInterface {

    /* Tamaño del tablero, altura y anchura de cada caja del sudoku. */
    private static final int BOARD_SIZE = 6;
    private static final int BOX_HEIGHT = 2;
    private static final int BOX_WIDTH = 3;

    /**
     * Implementación que verifica si un valor se puede colocar en una posición específica
     * comprobando filas, columnas y cajas de 2x3.
     */
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


    /**
     * Implementación que verifica si el tablero completo es válido,
     * asegurando que todas las celdas estén llenas y cumplan las reglas del Sudoku.
     */
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

    /**
     * Implementación que detecta si una celda específica tiene conflictos
     * con otras celdas según las reglas del Sudoku.
     */
    @Override
    public boolean hasConflict(BoardModel board, int row, int col) {
        CellModel cell = board.getCell(row, col);
        int value = cell.getValue();

        // Si la celda está vacía o bloqueada, no hay conflicto
        if (value == 0 || cell.isLocked()) {
            return false;
        }

        // Temporalmente quitar el valor para validar
        int originalValue = value;
        cell.setValue(0);

        // Verificar si el valor original causa conflicto
        boolean isValid = isValidMove(board, row, col, originalValue);

        // Restaurar el valor
        cell.setValue(originalValue);

        // Si no es válido, hay conflicto
        return !isValid;
    }

    /**
     * Método auxiliar para verificar si una fila contiene el valor especificado
     */
    private boolean rowContains(BoardModel board, int row, int value) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board.getCell(row, col).getValue() == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método auxiliar para verificar si una columna contiene el valor especificado.
     *
     * @param board El tablero de Sudoku
     * @param col Índice de la columna a verificar (0-5)
     * @param value Valor a buscar en la columna (1-6)
     * @return {@code true} si el valor existe en la columna, {@code false} en caso contrario
     */
    private boolean colContains(BoardModel board, int col, int value) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board.getCell(row, col).getValue() == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método auxiliar para verificar si una caja contiene el valor especificado.
     *
     * @param board El tablero de Sudoku
     * @param boxRow Índice de la fila de la caja (0-2)
     * @param boxCol Índice de la columna de la caja (0-1)
     * @param value Valor a buscar en la caja (1-6)
     * @return {@code true} si el valor existe en la caja, {@code false} en caso contrario
     */
    private boolean boxContains(BoardModel board, int boxRow, int boxCol, int value) {
        int rowStart = boxRow * BOX_HEIGHT;
        int colStart = boxCol * BOX_WIDTH;

        for (int row = rowStart; row < rowStart + BOX_HEIGHT; row++) {
            for (int col = colStart; col < colStart + BOX_WIDTH; col++) {
                if (board.getCell(row, col).getValue() == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica si todas las restricciones de Sudoku se cumplen en el tablero.
     * Comprueba que no haya duplicados en filas, columnas y cajas.
     *
     * @param board El tablero de Sudoku a verificar
     * @return {@code true} si todas las restricciones se cumplen, {@code false} en caso contrario
     */
    public boolean checkAllConstraints(BoardModel board) {
        // Verificar filas
        for (int row = 0; row < BOARD_SIZE; row++) {
            boolean[] seen = new boolean[BOARD_SIZE + 1];
            for (int col = 0; col < BOARD_SIZE; col++) {
                int value = board.getCell(row, col).getValue();
                if (value != 0) {
                    if (seen[value]) {
                        return false;  // Valor duplicado en la fila
                    }
                    seen[value] = true;
                }
            }
        }

        // Verificar columnas
        for (int col = 0; col < BOARD_SIZE; col++) {
            boolean[] seen = new boolean[BOARD_SIZE + 1];
            for (int row = 0; row < BOARD_SIZE; row++) {
                int value = board.getCell(row, col).getValue();
                if (value != 0) {
                    if (seen[value]) {
                        return false;  // Valor duplicado en la columna
                    }
                    seen[value] = true;
                }
            }
        }

        // Verificar cajas 2x3
        for (int boxRow = 0; boxRow < BOARD_SIZE / BOX_HEIGHT; boxRow++) {
            for (int boxCol = 0; boxCol < BOARD_SIZE / BOX_WIDTH; boxCol++) {
                boolean[] seen = new boolean[BOARD_SIZE + 1];
                for (int row = boxRow * BOX_HEIGHT; row < (boxRow + 1) * BOX_HEIGHT; row++) {
                    for (int col = boxCol * BOX_WIDTH; col < (boxCol + 1) * BOX_WIDTH; col++) {
                        int value = board.getCell(row, col).getValue();
                        if (value != 0) {
                            if (seen[value]) {
                                return false;  // Valor duplicado en la caja
                            }
                            seen[value] = true;
                        }
                    }
                }
            }
        }

        return true;  // Todas las restricciones se cumplen
    }

}