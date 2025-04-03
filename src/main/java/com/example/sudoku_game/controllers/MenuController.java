package com.example.sudoku_game.controllers;

import com.example.sudoku_game.views.GameView;
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
    private Button ExitButtom;

    @FXML
    private Button InstructionsButtom;

    @FXML
    private Button RestartButtom;

    @FXML
    private Button StartButtom;

    @FXML
    private Label SudokuGameLabel;

    @FXML
    void onActionExitButtom(ActionEvent event) {
        Stage stage = (Stage) ExitButtom.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onActionRestartButtom(ActionEvent event) {

    }

    @FXML
    void onActionStartButtom(ActionEvent event) {
        Stage stage = (Stage) StartButtom.getScene().getWindow();
        new GameView(stage);
    }

    @FXML
    void onActionInstructionsButtom(ActionEvent event) {

    }

}

