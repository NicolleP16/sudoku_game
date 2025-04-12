package com.example.sudoku_game.controllers;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import com.example.sudoku_game.models.BoardModel;
import com.example.sudoku_game.views.GameView;
import com.example.sudoku_game.interfaces.ValidationClass;

/**
 * Clase GameController que maneja la lógica del juego y las interacciones
 * del usuario en el juego de Sudoku. Conecta la vista con el modelo,
 * valida las entradas del usuario y actualiza el tablero.
 *
 * @author Nicolle Paz
 */
public class GameController {
    private GameView view;
    private BoardModel model;
    private ValidationClass validator;

    /**
     * Constructor que inicializa el controlador con la vista dada.
     * También crea el modelo y el validador, y genera el tablero inicial.
     *
     * @param view instancia de GameView utilizada en la interfaz gráfica.
     */
    public GameController(GameView view) {

        this.view = view;
        this.model = new BoardModel();
        this.validator = new ValidationClass();
        model.generateBoard();
    }

    /**
     * Reinicia el juego generando un nuevo tablero y actualizando la vista.
     */
    public void onActionRestartGame() {
        model.generateBoard();
        populateBoard(view.getBoardGrid());
        view.getCountSixes().setText(countSixes());
    }

    /**
     * Proporciona una pista al usuario si hay disponibles.
     * Si no hay pistas (para la última celda vacía), muestra una alerta informativa.
     */
    public void onActionHintButton() {
        boolean hintGiven = model.getHint();
        if (hintGiven) {
            populateBoard(view.getBoardGrid());
            view.getCountSixes().setText(countSixes());

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sin pistas");
            alert.setHeaderText(null);
            alert.setContentText("Te quedaste sin pistas :(");
            alert.showAndWait();
        }
    }

    public String countSixes(){
        String sixCounterString = "";
        int sixCounterInt = 0, row, col;
        for (row = 0; row < model.getBoardSize(); row++){
            for (col = 0; col < model.getBoardSize(); col++){
                if(model.getCell(row, col).getValue() == 6){
                    sixCounterInt++;
                    sixCounterString = String.valueOf(sixCounterInt);
                }
            }
        }
        return sixCounterString;
    }
    /**
     * Muestra una alerta cuando el usuario ingresa un número fuera del rango válido (1 a 6).
     */
    private void showRangeAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Número inválido");
        alert.setHeaderText(null);
        alert.setContentText("Por favor ingrese un número del 1 al 6");
        alert.showAndWait();
    }

    /**
     * Muestra una alerta cuando el usuario realiza un movimiento inválido (número repetido).
     */
    private void showInvalidMoveAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Movimiento inválido");
        alert.setHeaderText(null);
        alert.setContentText("Ese número ya está en la fila, columna o bloque");
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de felicitación cuando el jugador completa correctamente el tablero.
     */
    private void showWinAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Felicidades!");
        alert.setHeaderText(null);
        alert.setContentText("¡Has completado correctamente el Sudoku!");
        alert.showAndWait();
    }

    /**
     * Llena el tablero del Sudoku en la interfaz gráfica de acuerdo al estado actual del modelo.
     * También gestiona las entradas del usuario y valida los movimientos.
     *
     * @param boardGrid el GridPane que representa el tablero de Sudoku.
     */
    public void populateBoard(GridPane boardGrid) {
        boardGrid.getChildren().clear();

        for (int row = 0; row < model.getBoardSize(); row++) {
            for (int col = 0; col < model.getBoardSize(); col++) {
                TextField cell = new TextField();
                cell.setPrefWidth(40);
                cell.setPrefHeight(40);
                cell.setStyle("-fx-font-size: 18px; -fx-alignment: center; -fx-border-color: black;");

                int boxRow = row / 2;
                int boxCol = col / 3;
                if ((boxRow + boxCol) % 2 == 0) {
                    cell.setStyle(cell.getStyle() + "-fx-background-color: #d0d0d0;");
                }

                int value = model.getCell(row, col).getValue();
                if (value != 0) {
                    cell.setText(String.valueOf(value));

                    if (model.getCell(row, col).isLocked()) {
                        // Celdas bloqueadas (originales del tablero)
                        cell.setDisable(true);
                        cell.setStyle(cell.getStyle() + "-fx-text-fill: black; -fx-font-weight: bold; -fx-opacity: 1;");
                    } else if (model.getCell(row, col).isHighlighted()) {
                        // Celdas resaltadas (pistas)
                        cell.setStyle(cell.getStyle() + "-fx-text-fill: green; -fx-font-weight: bold;");
                        // Opcional: también deshabilitar las pistas para que no se puedan editar
                        cell.setDisable(true);
                        cell.setStyle(cell.getStyle() + "-fx-opacity: 1;");
                    } else {
                        // Valores ingresados por el usuario (no resaltados, no bloqueados)
                        cell.setStyle(cell.getStyle() + "-fx-text-fill: blue;");
                    }
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
                                view.getCountSixes().setText(countSixes());
                                cell.setStyle(cell.getStyle() + "-fx-border-color: black; -fx-text-fill: blue;");
                                if (model.isBoardComplete()) {
                                    showWinAlert();
                                    onActionRestartGame();
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