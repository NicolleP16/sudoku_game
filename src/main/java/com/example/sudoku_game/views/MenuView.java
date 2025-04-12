package com.example.sudoku_game.views;

import com.example.sudoku_game.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Vista del menú principal del juego Sudoku.
 * Esta clase se encarga de cargar y mostrar la interfaz gráfica del menú principal,
 * donde el usuario puede elegir entre iniciar el juego, ver las instrucciones o salir.
 * Utiliza el patrón Singleton para garantizar que solo haya una instancia de la vista del menú.
 *
 * @author Nicolle Paz
 * @version 1.0.0
 */
public class MenuView extends Stage {
    /**
     * Constructor de la vista del menú principal.
     * Carga el archivo FXML correspondiente al menú y muestra la ventana del menú.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public MenuView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("menu-view.fxml")
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 373, 247);
        this.setTitle("Menú");
        this.setScene(scene);
    }

    /**
     * Método que devuelve una instancia única de la vista del menú principal.
     * Implementa el patrón Singleton para garantizar que solo haya una instancia de MenuView.
     * Si ya existe una instancia, la devuelve; de lo contrario, crea una nueva.
     *
     * @return la instancia única de MenuView.
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public static MenuView getInstance() throws IOException {
        if (MenuViewHolder.INSTANCE == null) {
            MenuViewHolder.INSTANCE = new MenuView();
            return MenuViewHolder.INSTANCE;
        } else {
            return MenuViewHolder.INSTANCE;
        }
    }

    /**
     * Clase interna que mantiene la instancia única de MenuView.
     * Esta clase es utilizada para implementar el patrón Singleton.
     */
    private static class MenuViewHolder {
        private static MenuView INSTANCE;
    }
}