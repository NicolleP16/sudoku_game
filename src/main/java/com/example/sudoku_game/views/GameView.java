package com.example.sudoku_game.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.sudoku_game.controllers.GameController;

/**
 * Vista del juego Sudoku.
 * Esta clase crea la interfaz gráfica del juego, incluyendo el tablero de Sudoku
 * y los botones para reiniciar el juego y obtener pistas.
 *
 * @author Nicolle Paz
 * @version 1.0.0
 */
public class GameView {
    private Stage stage;
    private GameController controller;
    private GridPane boardGrid;

    /**
     * Constructor de la vista del juego.
     * Inicializa el controlador y la interfaz de usuario.
     *
     * @param stage la ventana principal donde se mostrará la vista del juego.
     */
    public GameView(Stage stage) {
        this.stage = stage;
        this.controller = new GameController(this);
        initializeUI();
    }

    /**
     * Inicializa la interfaz gráfica del juego.
     * Crea y organiza los elementos visuales del tablero de Sudoku y los botones de acción.
     */
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

    /**
     * Obtiene la cuadrícula del tablero de Sudoku.
     *
     * @return el objeto GridPane que representa el tablero de Sudoku.
     */
    public GridPane getBoardGrid() {
        return boardGrid;
    }
}