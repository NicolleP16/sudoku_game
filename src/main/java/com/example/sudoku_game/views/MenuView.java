package com.example.sudoku_game.views;

import com.example.sudoku_game.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuView extends Stage {

    public MenuView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("menu-view.fxml")
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 373, 247);
        this.setTitle("Menú");
        this.setScene(scene);
    }

    public static MenuView getInstance() throws IOException {
        if (MenuViewHolder.INSTANCE == null) {
            MenuViewHolder.INSTANCE = new MenuView();
            return MenuViewHolder.INSTANCE;
        } else {
            return MenuViewHolder.INSTANCE;
        }
    }

    private static class MenuViewHolder {
        private static MenuView INSTANCE;
    }

}
