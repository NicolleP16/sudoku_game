package com.example.sudoku_game.models;

/**
 * Modelo que representa una celda individual del tablero de Sudoku.
 * Cada celda tiene una posición (fila y columna), un valor numérico,
 * y propiedades adicionales como su estado de bloqueo y resaltado.
 */
public class CellModel {
    /** Índice de fila de la celda en el tablero (0-5) */
    private int row;

    /** Índice de columna de la celda en el tablero (0-5) */
    private int col;

    /** Valor numérico de la celda (0-6, donde 0 representa una celda vacía) */
    private int value;

    /** Indica si es una celda inicial que no se puede modificar */
    private boolean locked; // Indica si es una celda inicial que no se puede modificar

    /** Indica si la celda está resaltada, por ejemplo, por conflictos */
    private boolean isHighlighted; // Para resaltar celdas conflictivas


    /**
     * Constructor que inicializa una celda en una posición específica.
     * Por defecto, la celda está vacía, desbloqueada y no resaltada.
     *
     * @param row Índice de fila de la celda (0-5)
     * @param col Índice de columna de la celda (0-5)
     */
    public CellModel(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = 0; // 0 representa una celda vacía
        this.locked = false;
        this.isHighlighted = false;
    }

    /**
     * Obtiene el índice de fila de la celda.
     *
     * @return Índice de fila (0-5)
     */
    public int getRow() {
        return row;
    }

    /**
     * Obtiene el índice de columna de la celda.
     *
     * @return Índice de columna (0-5)
     */
    public int getCol() {
        return col;
    }

    /**
     * Obtiene el valor actual de la celda.
     *
     * @return Valor numérico (0-6, donde 0 representa una celda vacía)
     */
    public int getValue() {
        return value;
    }


    /**
     * Establece el valor numérico de la celda.
     * Solo acepta valores entre 0 y 6.
     *
     * @param value Nuevo valor para la celda (0-6)
     */
    public void setValue(int value) {
        // Solo permitir valores de 0 (vacío) a 6
        if (value >= 0 && value <= 6) {
            this.value = value;
        }
    }

    /**
     * Verifica si la celda está bloqueada (no modificable).
     * Las celdas iniciales del puzzle suelen estar bloqueadas.
     *
     * @return {@code true} si la celda está bloqueada, {@code false} en caso contrario
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Establece el estado de bloqueo de la celda.
     *
     * @param locked {@code true} para bloquear la celda, {@code false} para desbloquearla
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Verifica si la celda está resaltada.
     * El resaltado suele indicar conflictos con las reglas del Sudoku.
     *
     * @return {@code true} si la celda está resaltada, {@code false} en caso contrario
     */
    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * Establece el estado de resaltado de la celda.
     *
     * @param highlighted {@code true} para resaltar la celda, {@code false} para quitar el resaltado
     */
    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    /**
     * Verifica si la celda está vacía (valor 0).
     *
     * @return {@code true} si la celda está vacía, {@code false} si contiene un valor
     */
    public boolean isEmpty() {
        return value == 0;
    }

    /**
     * Representación textual de la celda.
     * Devuelve el valor como cadena o un espacio si está vacía.
     *
     * @return String con el valor de la celda o un espacio si está vacía
     */
    @Override
    public String toString() {
        return value == 0 ? " " : String.valueOf(value);
    }
}
