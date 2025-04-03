package com.example.sudoku_game.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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

        root.getChildren().addAll(boardGrid);
        Scene scene = new Scene(root, 443, 328);
        stage.setTitle("Sudoku");
        stage.setScene(scene);
    }
}