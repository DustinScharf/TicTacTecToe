package com.dustinscharf.tictactectoe.launcher;

import com.dustinscharf.tictactectoe.controller.Controller;
import com.dustinscharf.tictactectoe.game.Game;
import com.dustinscharf.tictactectoe.game.Player;
import com.dustinscharf.tictactectoe.network.server.Server;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/StartMenu.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("TicTacTecToe");
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
