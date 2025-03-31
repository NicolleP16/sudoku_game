package com.example.sudoku_game.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Modelo que representa el tablero de Sudoku 6x6
 */
public class BoardModel {
    private static final int BOARD_SIZE = 6;
    private static final int BOX_SIZE = 2; // Para un Sudoku 6x6, las cajas son de 2x3
    private static final int BOX_HEIGHT = 2;
    private static final int BOX_WIDTH = 3;

    private CellModel[][] board;
    private Random random;

    public BoardModel() {
        random = new Random();
        board = new CellModel[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    /**
     * Inicializa el tablero creando objetos CellModel para cada celda
     */
    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = new CellModel(row, col);
            }
        }
    }

    /**
     * Genera un nuevo tablero de Sudoku con un nivel de dificultad específico
     * @param difficulty Nivel de dificultad (1-fácil, 2-medio, 3-difícil)
     */
    public void generateBoard(int difficulty) {
        // Limpiar el tablero primero
        clearBoard();

        // Generar un tablero resuelto
        generateSolvedBoard();

        // Determinar cuántas celdas mostrar según la dificultad
        int cellsToRemove;
        switch (difficulty) {
            case 1: // Fácil
                cellsToRemove = 15; // Mostrar 21 celdas de 36
                break;
            case 2: // Medio
                cellsToRemove = 20; // Mostrar 16 celdas de 36
                break;
            case 3: // Difícil
                cellsToRemove = 25; // Mostrar 11 celdas de 36
                break;
            default:
                cellsToRemove = 20;
        }

        removeCells(cellsToRemove);
    }

    /**
     * Limpia todo el tablero
     */
    public void clearBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col].setValue(0);
                board[row][col].setLocked(false);
            }
        }
    }

    /**
     * Genera un tablero completamente resuelto
     */
    private void generateSolvedBoard() {
        fillDiagonalBoxes();
        solveBoard();
    }

    /**
     * Llena las cajas diagonales (que son independientes entre sí)
     */
    private void fillDiagonalBoxes() {
        for (int i = 0; i < BOARD_SIZE; i += BOX_HEIGHT) {
            fillBox(i, i);
        }
    }

    /**
     * Llena una caja específica con números del 1 al 6 de forma aleatoria
     */
    private void fillBox(int startRow, int startCol) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= BOARD_SIZE; i++) {
            numbers.add(i);
        }

        // Mezclar los números
        java.util.Collections.shuffle(numbers, random);

        int index = 0;
        for (int i = 0; i < BOX_HEIGHT; i++) {
            for (int j = 0; j < BOX_WIDTH; j++) {
                if (startRow + i < BOARD_SIZE && startCol + j < BOARD_SIZE) {
                    board[startRow + i][startCol + j].setValue(numbers.get(index++));
                }
            }
        }
    }

    /**
     * Resuelve el tablero usando backtracking
     */
    private boolean solveBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                // Si la celda está vacía
                if (board[row][col].getValue() == 0) {
                    // Probar con los números del 1 al 6
                    for (int num = 1; num <= BOARD_SIZE; num++) {
                        // Si es válido colocar el número en esta posición
                        if (isValidPlacement(row, col, num)) {
                            // Colocar el número
                            board[row][col].setValue(num);

                            // Continuar resolviendo recursivamente
                            if (solveBoard()) {
                                return true;
                            }

                            // Si no lleva a una solución, deshacer y probar otro número
                            board[row][col].setValue(0);
                        }
                    }
                    // Si ningún número funciona, retroceder
                    return false;
                }
            }
        }
        // Si llegamos aquí, todo el tablero está lleno
        return true;
    }

    /**
     * Elimina un número específico de celdas manteniendo la unicidad de la solución
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
     * Verifica si es válido colocar un número en una posición específica
     */
    public boolean isValidPlacement(int row, int col, int num) {
        return !isInRow(row, num) && !isInColumn(col, num) && !isInBox(row - row % BOX_HEIGHT, col - col % BOX_WIDTH, num);
    }

    /**
     * Verifica si un número ya está en la fila
     */
    private boolean isInRow(int row, int num) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col].getValue() == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un número ya está en la columna
     */
    private boolean isInColumn(int col, int num) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][col].getValue() == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un número ya está en la caja 2x3
     */
    private boolean isInBox(int startRow, int startCol, int num) {
        for (int row = 0; row < BOX_HEIGHT; row++) {
            for (int col = 0; col < BOX_WIDTH; col++) {
                if (board[startRow + row][startCol + col].getValue() == num) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica si el tablero está completo y correcto
     */
    public boolean isBoardComplete() {
        // Verificar que no hay celdas vacías
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getValue() == 0) {
                    return false;
                }
            }
        }

        // Verificar filas
        for (int row = 0; row < BOARD_SIZE; row++) {
            boolean[] seen = new boolean[BOARD_SIZE + 1];
            for (int col = 0; col < BOARD_SIZE; col++) {
                int value = board[row][col].getValue();
                if (value < 1 || value > BOARD_SIZE || seen[value]) {
                    return false;
                }
                seen[value] = true;
            }
        }

        // Verificar columnas
        for (int col = 0; col < BOARD_SIZE; col++) {
            boolean[] seen = new boolean[BOARD_SIZE + 1];
            for (int row = 0; row < BOARD_SIZE; row++) {
                int value = board[row][col].getValue();
                if (value < 1 || value > BOARD_SIZE || seen[value]) {
                    return false;
                }
                seen[value] = true;
            }
        }

        // Verificar cajas
        for (int boxRow = 0; boxRow < BOARD_SIZE; boxRow += BOX_HEIGHT) {
            for (int boxCol = 0; boxCol < BOARD_SIZE; boxCol += BOX_WIDTH) {
                boolean[] seen = new boolean[BOARD_SIZE + 1];
                for (int row = 0; row < BOX_HEIGHT; row++) {
                    for (int col = 0; col < BOX_WIDTH; col++) {
                        int value = board[boxRow + row][boxCol + col].getValue();
                        if (value < 1 || value > BOARD_SIZE || seen[value]) {
                            return false;
                        }
                        seen[value] = true;
                    }
                }
            }
        }

        return true;
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
}
