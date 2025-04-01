package com.example.sudoku_game.models;
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
    private static final int INITIAL_NUMBERS_PER_BOX = 2;

    private CellModel[][] board;
    private Random random;

    public BoardModel() {
        random = new Random();
        board = new CellModel[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = new CellModel(row, col);
            }
        }
    }

    public void generateBoard() {
        clearBoard();
        generateValidInitialNumbers();
        solveBoard();
        removeCells(20);
        lockInitialCells();
    }

    private void generateValidInitialNumbers() {
        for (int boxRow = 0; boxRow < BOARD_SIZE; boxRow += BOX_HEIGHT) {
            for (int boxCol = 0; boxCol < BOARD_SIZE; boxCol += BOX_WIDTH) {
                fillBoxWithInitialNumbers(boxRow, boxCol);
            }
        }
    }

    private void fillBoxWithInitialNumbers(int startRow, int startCol) {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        java.util.Collections.shuffle(numbers, random);

        int added = 0;
        for (int i = 0; i < BOX_HEIGHT; i++) {
            for (int j = 0; j < BOX_WIDTH; j++) {
                if (added >= INITIAL_NUMBERS_PER_BOX) break;
                int row = startRow + i;
                int col = startCol + j;
                board[row][col].setValue(numbers.remove(0));
                added++;
            }
        }
    }

    private void lockInitialCells() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getValue() != 0) {
                    board[row][col].setLocked(true);
                }
            }
        }
    }

    public void clearBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col].setValue(0);
                board[row][col].setLocked(false);
            }
        }
    }

    private boolean solveBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getValue() == 0) {
                    for (int num = 1; num <= BOARD_SIZE; num++) {
                        if (isValidPlacement(row, col, num)) {
                            board[row][col].setValue(num);
                            if (solveBoard()) return true;
                            board[row][col].setValue(0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void removeCells(int count) {
        List<int[]> positions = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                positions.add(new int[]{row, col});
            }
        }
        java.util.Collections.shuffle(positions, random);
        for (int i = 0; i < count && i < positions.size(); i++) {
            int row = positions.get(i)[0];
            int col = positions.get(i)[1];
            board[row][col].setValue(0);
        }
    }

    public boolean isValidPlacement(int row, int col, int num) {
        return !isInRow(row, num) && !isInColumn(col, num) && !isInBox(row - row % BOX_HEIGHT, col - col % BOX_WIDTH, num);
    }

    private boolean isInRow(int row, int num) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col].getValue() == num) return true;
        }
        return false;
    }

    private boolean isInColumn(int col, int num) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][col].getValue() == num) return true;
        }
        return false;
    }

    private boolean isInBox(int startRow, int startCol, int num) {
        for (int row = 0; row < BOX_HEIGHT; row++) {
            for (int col = 0; col < BOX_WIDTH; col++) {
                if (board[startRow + row][startCol + col].getValue() == num) return true;
            }
        }
        return false;
    }

    public boolean isBoardComplete() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getValue() == 0) return false;
            }
        }
        return checkAllConstraints();
    }

    private boolean checkAllConstraints() {
        return checkRows() && checkColumns() && checkBoxes();
    }

    private boolean checkRows() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            boolean[] seen = new boolean[BOARD_SIZE + 1];
            for (int col = 0; col < BOARD_SIZE; col++) {
                int val = board[row][col].getValue();
                if (val != 0 && seen[val]) return false;
                seen[val] = true;
            }
        }
        return true;
    }

    private boolean checkColumns() {
        for (int col = 0; col < BOARD_SIZE; col++) {
            boolean[] seen = new boolean[BOARD_SIZE + 1];
            for (int row = 0; row < BOARD_SIZE; row++) {
                int val = board[row][col].getValue();
                if (val != 0 && seen[val]) return false;
                seen[val] = true;
            }
        }
        return true;
    }

    private boolean checkBoxes() {
        for (int boxRow = 0; boxRow < BOARD_SIZE; boxRow += BOX_HEIGHT) {
            for (int boxCol = 0; boxCol < BOARD_SIZE; boxCol += BOX_WIDTH) {
                boolean[] seen = new boolean[BOARD_SIZE + 1];
                for (int row = boxRow; row < boxRow + BOX_HEIGHT; row++) {
                    for (int col = boxCol; col < boxCol + BOX_WIDTH; col++) {
                        int val = board[row][col].getValue();
                        if (val != 0 && seen[val]) return false;
                        seen[val] = true;
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