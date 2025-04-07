package com.example.sudoku_game.controllers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import com.example.sudoku_game.models.BoardModel;
import com.example.sudoku_game.views.GameView;
import com.example.sudoku_game.interfaces.ValidationClass;

public class GameController {
    private GameView view;
    private BoardModel model;
    private ValidationClass validator;

    public GameController(GameView view) {
        this.view = view;
        this.model = new BoardModel();
        this.validator = new ValidationClass();
        model.generateBoard();
    }

    public void onActionRestartGame() {
        model.generateBoard();
        populateBoard(view.getBoardGrid());
    }

    private void showRangeAlert() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Número inválido");
        alert.setHeaderText(null);
        alert.setContentText("Por favor ingrese un número del 1 al 6");
        alert.showAndWait();
    }

    private void showInvalidMoveAlert() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Movimiento inválido");
        alert.setHeaderText(null);
        alert.setContentText("Ese número ya está en la fila, columna o bloque");
        alert.showAndWait();
    }

    private void showWinAlert() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("¡Felicidades!");
        alert.setHeaderText(null);
        alert.setContentText("¡Has completado correctamente el Sudoku!");
        alert.showAndWait();
    }

    public void populateBoard(GridPane boardGrid) {
        boardGrid.getChildren().clear();
        for (int row = 0; row < model.getBoardSize(); row++) {
            for (int col = 0; col < model.getBoardSize(); col++) {
                TextField cell = new TextField();
                cell.setPrefWidth(40);
                cell.setPrefHeight(60);
                cell.setStyle("-fx-font-size: 18px; -fx-alignment: center; -fx-border-color: black;");

                int boxRow = row / 2;
                int boxCol = col / 3;
                if ((boxRow + boxCol) % 2 == 0) {
                    cell.setStyle(cell.getStyle() + "-fx-background-color: #d0d0d0;");
                }

                int value = model.getCell(row, col).getValue();
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setDisable(true);
                    cell.setStyle(cell.getStyle() + "-fx-text-fill: black; -fx-font-weight: bold; -fx-opacity: 1;");
                }

                int finalRow = row;
                int finalCol = col;
                cell.setOnKeyPressed((KeyEvent keyEvent) -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        String newValue = cell.getText().trim();

                        if (!newValue.matches("[1-6]")) {
                            cell.setStyle(cell.getStyle() + "-fx-border-color: red; -fx-border-width: 2px;");
                            showRangeAlert();
                        } else {
                            int intValue = Integer.parseInt(newValue);
                            if (validator.isValidMove(model, finalRow, finalCol, intValue)) {
                                model.setCell(finalRow, finalCol, intValue);
                                cell.setStyle(cell.getStyle() + "-fx-border-color: black;");
                                if (model.isBoardComplete()) {
                                    showWinAlert();
                                }
                            } else {
                                cell.setStyle(cell.getStyle() + "-fx-border-color: red; -fx-border-width: 2px;");
                                showInvalidMoveAlert();
                            }
                        }
                    }
                });
                boardGrid.add(cell, col, row);
            }
        }
    }
}

