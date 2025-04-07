package com.example.sudoku_game.controllers;

import com.example.sudoku_game.views.GameView;
import com.example.sudoku_game.views.InstructionsView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class MenuController {

    @FXML
    private Button ExitButton;

    @FXML
    private Button InstructionsButton;

    @FXML
    private Button StartButton;

    @FXML
    private Label SudokuGameLabel;

    @FXML
    void onActionExitButton(ActionEvent event) {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onActionStartButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Listo para iniciar el juego?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) StartButton.getScene().getWindow();
            new GameView(stage);
            stage.show();
        }
    }

    @FXML
    void onActionInstructionsButton(ActionEvent event) throws IOException {
        new InstructionsView();
    }


}

