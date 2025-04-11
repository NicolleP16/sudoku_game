package com.example.sudoku_game.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.sudoku_game.controllers.GameController;

public class GameView {
    private Stage stage;
    private GameController controller;
    private GridPane boardGrid;

    public GameView(Stage stage) {
        this.stage = stage;
        this.controller = new GameController(this);
        initializeUI();
    }

    private void initializeUI() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        controller.populateBoard(boardGrid);

        Button RestartButton = new Button("Reiniciar");
        RestartButton.setOnAction(e -> controller.onActionRestartGame());

        Button HintButton = new Button("Pista");
        HintButton.setOnAction(e -> controller.onActionHintButton());

        HBox buttonsBox = new HBox(10); // Espacio horizontal entre botones
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(RestartButton, HintButton);

        root.getChildren().addAll(boardGrid, buttonsBox);
        Scene scene = new Scene(root, 370, 320);
        stage.setTitle("Sudoku");
        stage.setScene(scene);
    }

    public GridPane getBoardGrid() {
        return boardGrid;
    }

}