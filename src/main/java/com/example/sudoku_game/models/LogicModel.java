package com.example.sudoku_game.models;
import com.example.sudoku_game.interfaces.ValidationInterface;

/**
 * Modelo que contiene la lógica del juego Sudoku
 */
public class LogicModel {
    private BoardModel board;
    private ValidationInterface validator;
    private boolean gameInProgress;

    public LogicModel() {
        this.board = new BoardModel();
        this.gameInProgress = false;
    }

    public void setValidator(ValidationInterface validator) {
        this.validator = validator;
    }

    public void startNewGame() {
        board.generateBoard();
        gameInProgress = true;
    }

    public boolean setCellValue(int row, int col, int value) {
        if (!gameInProgress || board.getCell(row, col).isLocked()) return false;

        if (validator != null && value != 0) {
            if (!validator.isValidMove(board, row, col, value)) {
                board.getCell(row, col).setHighlighted(true);
                return false;
            }
        }

        board.getCell(row, col).setValue(value);
        board.getCell(row, col).setHighlighted(false);

        if (board.isBoardComplete()) gameCompleted();
        return true;
    }

    private void gameCompleted() {
        gameInProgress = false;
    }

    private int calculateScore(int timeInSeconds) {
        return Math.max(0, 2000 - (timeInSeconds * 2));
    }

    public void checkConflicts() {
        if (validator != null) {
            for (int row = 0; row < board.getBoardSize(); row++) {
                for (int col = 0; col < board.getBoardSize(); col++) {
                    CellModel cell = board.getCell(row, col);
                    if (!cell.isEmpty() && !cell.isLocked()) {
                        boolean isValid = validator.isValidMove(board, row, col, cell.getValue());
                        cell.setHighlighted(!isValid);
                    }
                }
            }
        }
    }

    public boolean getHint() {
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                CellModel cell = board.getCell(row, col);
                if (cell.isEmpty()) {
                    for (int num = 1; num <= 6; num++) {
                        if (validator.isValidMove(board, row, col, num)) {
                            cell.setValue(num);
                            cell.setHighlighted(true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public BoardModel getBoard() {
        return board;
    }


    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void endGame() {
        gameInProgress = false;
    }
}