package com.example.sudoku_game.models;
/**
 * Modelo que representa una celda individual del tablero de Sudoku
 */
public class CellModel {
    private int row;
    private int col;
    private int value;
    private boolean locked; // Indica si es una celda inicial que no se puede modificar
    private boolean isHighlighted; // Para resaltar celdas conflictivas

    public CellModel(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = 0; // 0 representa una celda vacía
        this.locked = false;
        this.isHighlighted = false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        // Solo permitir valores de 0 (vacío) a 6
        if (value >= 0 && value <= 6) {
            this.value = value;
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        return value == 0 ? " " : String.valueOf(value);
    }
}
