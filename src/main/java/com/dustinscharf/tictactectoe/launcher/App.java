package com.dustinscharf.tictactectoe.launcher;

import com.dustinscharf.tictactectoe.controller.Controller;
import com.dustinscharf.tictactectoe.game.Game;
import com.dustinscharf.tictactectoe.game.Player;
import com.dustinscharf.tictactectoe.network.server.Server;
import javafx.application.Application;
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
        new GameLauncher().start(primaryStage);
    }
}
