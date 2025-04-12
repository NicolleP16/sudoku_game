package com.example.sudoku_game.models;

/**
 * Modelo que representa una celda individual en el tablero de Sudoku.
 * Esta clase gestiona el estado lógico de una celda, incluyendo su valor, posición,
 * estado de bloqueo y resaltado. Forma parte del componente Modelo en el patrón MVC.
 *
 * @author Juan Pablo Escamilla
 */
public class CellModel {
    /** Fila de la celda en el tablero (0-indexado) */
    private int row;

    /** Columna de la celda en el tablero (0-indexado) */
    private int col;

    /** Valor numérico de la celda (0 representa vacío) */
    private int value;

    /** Indica si la celda es modificable (true = bloqueada) */
    private boolean locked;

    /** Indica si la celda debe resaltarse por conflictos */
    private boolean isHighlighted;

    /**
     * Constructor para crear una celda vacía y desbloqueada.
     *
     * @param row Fila de la celda (0-indexado)
     * @param col Columna de la celda (0-indexado)
     */
    public CellModel(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = 0;
        this.locked = false;
        this.isHighlighted = false;
    }

    /**
     * Obtiene el valor actual de la celda.
     *
     * @return Valor numérico entre 0-6 (0 = vacío)
     */
    public int getValue() {
        return value;
    }

    /**
     * Establece el valor de la celda si está dentro del rango permitido.
     * Solo acepta valores entre 0-6. Valores fuera de este rango son ignorados.
     *
     * @param value Nuevo valor para la celda (0-6)
     */
    public void setValue(int value) {
        if (value >= 0 && value <= 6) {
            this.value = value;
        }
    }

    /**
     * Verifica si la celda está bloqueada contra modificaciones.
     *
     * @return true si la celda está bloqueada, false si es editable
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Establece el estado de bloqueo de la celda.
     *
     * @param locked true para bloquear la celda, false para permitir edición
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Verifica si la celda está resaltada.
     *
     * @return true si la celda está resaltada, false en caso contrario
     */
    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * Establece el estado de resaltado de la celda.
     *
     * @param highlighted true para resaltar la celda, false para quitar el resaltado
     */
    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    /**
     * Verifica si la celda está vacía.
     *
     * @return true si el valor es 0, false en cualquier otro caso
     */
    public boolean isEmpty() {
        return value == 0;
    }

    /**
     * Representación textual del valor de la celda.
     *
     * @return Espacio en blanco si está vacía (valor 0), el número correspondiente en otros casos
     */
    @Override
    public String toString() {
        return value == 0 ? " " : String.valueOf(value);
    }
}