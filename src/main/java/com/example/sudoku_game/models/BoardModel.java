package com.example.sudoku_game.models;

import com.example.sudoku_game.interfaces.ValidationInterface;
import com.example.sudoku_game.interfaces.ValidationClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Modelo que representa el tablero de Sudoku 6x6
 */
public class BoardModel {
    private static final int BOARD_SIZE = 6;
    private static final int BOX_HEIGHT = 2;
    private static final int BOX_WIDTH = 3;
    private static final int NUMBERS_PER_BOX = 2; // Número de dígitos mostrados por caja

    private CellModel[][] board;
    private Random random;
    private int[][] solution;
    private ValidationInterface validator;

    public BoardModel() {
        random = new Random();
        board = new CellModel[BOARD_SIZE][BOARD_SIZE];
        validator = new ValidationClass(); // Usamos la implementación estándar
        initializeBoard();
    }

    /**
     * Inicializa el tablero creando objetos CellModel para cada celda y generando un nuevo juego
     */
    public void initializeBoard() {
        // Crear los objetos CellModel si no existen
        if (board[0][0] == null) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    board[row][col] = new CellModel(row, col);
                }
            }
        }

        // Limpiar el tablero
        this.clearBoard();

        // Generar una solución
        if (!this.generateSolution(0, 0)) {
            System.err.println("Error: No se pudo generar una solución de Sudoku válida.");
            return;
        }

        // Guardar el estado inicial (la solución completa)
        this.saveInitialState();

        // Crear el puzzle con exactamente 2 números por caja
        this.makePuzzleWith2PerBox();

        System.out.println("Tablero inicializado:");
    }

    /**
     * Limpia completamente el tablero
     */
    public void clearBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col].setValue(0);
                board[row][col].setLocked(false); // Equivalente a setEditable(true)
            }
        }
    }

    /**
     * Genera una solución usando backtracking desde una posición específica
     */
    private boolean generateSolution(int row, int col) {
        // Si hemos llegado al final del tablero, la solución está completa
        if (row == BOARD_SIZE) {
            return true;
        }

        // Calcular la siguiente posición
        int nextRow = (col == BOARD_SIZE - 1) ? row + 1 : row;
        int nextCol = (col == BOARD_SIZE - 1) ? 0 : col + 1;

        // Si la celda ya tiene un valor, continuar con la siguiente
        if (board[row][col].getValue() != 0) {
            return generateSolution(nextRow, nextCol);
        }

        // Lista de números del 1 al 6 en orden aleatorio
        List<Integer> numbers = new ArrayList<>();
        for (int num = 1; num <= BOARD_SIZE; num++) {
            numbers.add(num);
        }
        java.util.Collections.shuffle(numbers, random);

        // Probar cada número
        for (int num : numbers) {
            if (validator.isValidMove(this, row, col, num)) {
                board[row][col].setValue(num);

                if (generateSolution(nextRow, nextCol)) {
                    return true;
                }

                // Si no funciona, revertir y probar otro número
                board[row][col].setValue(0);
            }
        }

        // Si ningún número funciona, regresar false
        return false;
    }

    /**
     * Genera un nuevo tablero de Sudoku
     */
    public void generateBoard() {
        // Inicializar el tablero
        initializeBoard();
    }

    /**
     * Guarda el estado inicial (solución completa) del tablero
     */
    private void saveInitialState() {
        solution = new int[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                solution[row][col] = board[row][col].getValue();
            }
        }
    }

    /**
     * Crea el puzzle dejando exactamente 2 números por cada caja de 2x3
     */
    private void makePuzzleWith2PerBox() {
        // Primero limpiamos el tablero pero mantenemos la solución
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col].setValue(0);
                board[row][col].setLocked(false);
            }
        }

        // Para cada caja de 2x3
        for (int boxRow = 0; boxRow < BOARD_SIZE; boxRow += BOX_HEIGHT) {
            for (int boxCol = 0; boxCol < BOARD_SIZE; boxCol += BOX_WIDTH) {
                // Crear lista de posiciones en esta caja
                List<int[]> boxPositions = new ArrayList<>();
                for (int r = 0; r < BOX_HEIGHT; r++) {
                    for (int c = 0; c < BOX_WIDTH; c++) {
                        boxPositions.add(new int[]{boxRow + r, boxCol + c});
                    }
                }

                // Mezclar las posiciones
                java.util.Collections.shuffle(boxPositions, random);

                // Seleccionar exactamente 2 posiciones para mostrar
                for (int i = 0; i < NUMBERS_PER_BOX && i < boxPositions.size(); i++) {
                    int row = boxPositions.get(i)[0];
                    int col = boxPositions.get(i)[1];

                    // Restaurar el valor de la solución y bloquear la celda
                    board[row][col].setValue(solution[row][col]);
                    board[row][col].setLocked(true);
                }
            }
        }
    }

    /**
     * Elimina un número específico de celdas manteniendo la unicidad de la solución
     * Este método ya no se usa con la nueva implementación
     */
    private void removeCells(int count) {
        List<int[]> positions = new ArrayList<>();

        // Recopilar todas las posiciones del tablero
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                positions.add(new int[]{row, col});
            }
        }

        // Mezclar las posiciones
        java.util.Collections.shuffle(positions, random);

        // Recorrer las posiciones mezcladas y eliminar celdas
        for (int i = 0; i < count && i < positions.size(); i++) {
            int row = positions.get(i)[0];
            int col = positions.get(i)[1];

            // Guardar el valor para comprobar la unicidad de la solución
            int value = board[row][col].getValue();
            board[row][col].setValue(0);

            // Las celdas que quedan con valor se bloquean
            for (int r = 0; r < BOARD_SIZE; r++) {
                for (int c = 0; c < BOARD_SIZE; c++) {
                    if (board[r][c].getValue() != 0) {
                        board[r][c].setLocked(true);
                    }
                }
            }
        }
    }

    /**
     * Verifica si el tablero está completo y correcto
     */
    public boolean isBoardComplete() {
        return validator.isValidBoard(this);
    }

    public CellModel getCell(int row, int col) {
        return board[row][col];
    }

    public void setCell(int row, int col, int value) {
        if (!board[row][col].isLocked()) {
            board[row][col].setValue(value);
        }
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    /**
     * Obtiene la solución del tablero
     */
    public int[][] getSolution() {
        return solution;
    }

    /**
     * Permite cambiar el validador por otro que implemente la misma interfaz
     * @param validator Un objeto que implementa ValidationInterface
     */
    public void setValidator(ValidationInterface validator) {
        this.validator = validator;
    }

    public boolean getHint() {
        int emptyCount = 0;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getValue() == 0) {
                    emptyCount++;
                }
            }
        }

        if (emptyCount == 1) {
            return false;
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                CellModel cell = board[row][col];
                if (cell.getValue() == 0) {
                    int correctValue = solution[row][col];
                    cell.setValue(correctValue);
                    cell.setHighlighted(true);
                    return true;
                }
            }
        }


        return false;
    }
}