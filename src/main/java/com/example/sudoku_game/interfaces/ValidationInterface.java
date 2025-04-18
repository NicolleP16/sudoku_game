package com.example.sudoku_game.interfaces;
import com.example.sudoku_game.models.BoardModel;

/**
 * Interfaz que define la estrategia de validación para el tablero de Sudoku
 * @author Juan Pablo Escamilla
 */
public interface ValidationInterface {

    /**
     * Verifica si es válido colocar un número en una posición específica
     *
     * @param board El tablero de Sudoku
     * @param row   Fila donde se quiere colocar el número
     * @param col   Columna donde se quiere colocar el número
     * @param value Valor que se quiere colocar
     * @return true si es válido, false en caso contrario

     */
    boolean isValidMove(BoardModel board, int row, int col, int value);

    /**
     * Verifica si todo el tablero es válido y completo.
     *
     * @param board El tablero de Sudoku a validar
     * @return {@code true} si el tablero cumple todas las reglas y está completo,
     *         {@code false} en caso contrario
     */
    boolean isValidBoard(BoardModel board);


    /**
     * Comprueba si hay algún conflicto para una celda específica.
     * Un conflicto ocurre cuando el valor actual de la celda viola alguna
     * de las reglas del Sudoku.
     *
     * @param board El tablero de Sudoku
     * @param row Fila de la celda a verificar (0-5)
     * @param col Columna de la celda a verificar (0-5)
     * @return {@code true} si hay conflicto, {@code false} en caso contrario
     */
    boolean hasConflict(BoardModel board, int row, int col);

}