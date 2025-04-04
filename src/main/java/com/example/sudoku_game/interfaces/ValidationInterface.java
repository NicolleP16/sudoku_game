package com.example.sudoku_game.interfaces;
import com.example.sudoku_game.models.BoardModel;

/**
 * Interfaz que define la estrategia de validación para el tablero de Sudoku
 */
public interface ValidationInterface {

    /**
     * Verifica si es válido colocar un número en una posición específica
     * @param board El tablero de Sudoku
     * @param row Fila donde se quiere colocar el número
     * @param col Columna donde se quiere colocar el número
     * @param value Valor que se quiere colocar
     * @return true si es válido, false en caso contrario
     */
    boolean isValidMove(BoardModel board, int row, int col, int value);

    /**
     * Verifica si todo el tablero es válido
     * @param board El tablero de Sudoku
     * @return true si el tablero cumple todas las reglas, false en caso contrario
     */
    boolean isValidBoard(BoardModel board);

    /**
     * Comprueba si hay algún conflicto para una celda específica
     * @param board El tablero de Sudoku
     * @param row Fila de la celda
     * @param col Columna de la celda
     * @return true si hay conflicto, false en caso contrario
     */
    boolean hasConflict(BoardModel board, int row, int col);
}