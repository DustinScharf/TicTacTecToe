package com.dustinscharf.tictactectoe.launcher;

import com.dustinscharf.tictactectoe.controller.Controller;
import com.dustinscharf.tictactectoe.game.Game;
import com.dustinscharf.tictactectoe.game.Player;
import com.dustinscharf.tictactectoe.network.server.Server;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameLauncher {
    private boolean isRunning;

    public GameLauncher() {
        this.isRunning = false;
    }

    public void start(Stage primaryStage,
                      boolean online, boolean host, String hostName,
                      boolean botMode)
            throws IOException {
        if (this.isRunning) {
            return;
        }
        this.isRunning = true;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();

        Game game = new Game(controller,
                new Player("Player 1"), new Player("Player 2"),
                botMode,
                online, host, hostName,
                primaryStage);

        if (online) {
            game.setOnlinePlayer(host ? game.getGamePlayer1() : game.getGamePlayer2());
        }

        if (host) {
            Task<Void> sleeper = new Task<>() {
                @Override
                protected Void call() {
                    try {
                        while (!game.getClient().isConnectedToAnotherPlayer()) {
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        System.err.println("Thread could not sleep...");
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(event -> this.show(primaryStage, root));
            new Thread(sleeper).start();
        } else {
            this.show(primaryStage, root);
        }
    }

    public void stop() {
        if (!this.isRunning) {
            return;
        }
        this.isRunning = false;
    }

    private void show(Stage primaryStage, Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        primaryStage.setTitle("TicTacTecToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
