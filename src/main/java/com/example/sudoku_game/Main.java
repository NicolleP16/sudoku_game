package com.example.sudoku_game;

import com.example.sudoku_game.views.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal del proyecto SudokuGame.
 * Inicia la aplicación JavaFX y muestra la vista del menú principal.
 *
 * @author Nicolle Paz
 * @author Juan Pablo Escamilla
 * @version 1.0.0
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MenuView menuView = MenuView.getInstance();
        menuView.show();
    }

    public static void main(String[] args) {
        launch();
    }
}