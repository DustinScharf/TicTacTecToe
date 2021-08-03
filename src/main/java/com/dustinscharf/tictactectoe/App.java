package com.dustinscharf.tictactectoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();

        controller.testController();

        Scene scene = new Scene(root);
        // scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        primaryStage.setTitle("TicTacTecToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
