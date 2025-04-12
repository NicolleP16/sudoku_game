package com.example.sudoku_game.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Vista de las instrucciones del juego Sudoku.
 * Esta clase se encarga de cargar y mostrar la interfaz gráfica con las instrucciones para jugar Sudoku.
 *
 * @author Nicolle Paz
 * @version 1.0.0
 */
public class InstructionsView {

    /**
     * Constructor de la vista de instrucciones.
     * Carga el archivo FXML correspondiente a las instrucciones y muestra una nueva ventana
     * con las instrucciones del juego.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public InstructionsView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudoku_game/instructions-view.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Instrucciones");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}