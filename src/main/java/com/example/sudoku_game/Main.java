package com.example.sudoku_game;

import com.example.sudoku_game.views.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

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