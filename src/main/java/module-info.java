module com.example.sudoku_game {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sudoku_game.controllers to javafx.fxml;
    exports com.example.sudoku_game;
}