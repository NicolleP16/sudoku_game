package com.example.sudoku_game.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Clase InstructionsController para la ventana de instrucciones del juego Sudoku.
 * Esta clase maneja la interacción del usuario en la ventana de instrucciones,
 * que se presenta a través de un archivo FXML. El controlador gestiona la acción
 * de cerrar la ventana cuando el usuario hace clic en el botón "Cerrar".
 *
 * @author Nicolle Paz
 * @version 1.0.0
 */
public class InstructionsController {
    @FXML
    private Button closeButton;

    @FXML
    void onActionCloseButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}