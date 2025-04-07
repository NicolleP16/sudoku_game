package com.example.sudoku_game.controllers;

import com.example.sudoku_game.views.GameView;
import com.example.sudoku_game.views.InstructionsView;
import com.example.sudoku_game.views.MenuView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuController {

    @FXML
    private Button ExitButton;

    @FXML
    private Button InstructionsButton;

    @FXML
    private Button RestartButton;

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
    void onActionRestartButton(ActionEvent event) {

    }

    @FXML
    void onActionStartButton(ActionEvent event) {
        Stage stage = (Stage) StartButton.getScene().getWindow();
        new GameView(stage);
    }

    @FXML
    void onActionInstructionsButton(ActionEvent event) throws IOException {
        new InstructionsView();
    }


}

