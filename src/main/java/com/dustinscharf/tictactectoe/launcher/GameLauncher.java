package com.dustinscharf.tictactectoe.launcher;

import com.dustinscharf.tictactectoe.controller.Controller;
import com.dustinscharf.tictactectoe.game.Game;
import com.dustinscharf.tictactectoe.game.Player;
import com.dustinscharf.tictactectoe.launcher.menus.NetworkModeHostMenu;
import com.dustinscharf.tictactectoe.network.server.Server;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameLauncher {
    private Game game;

    private boolean isRunning;

    private boolean online;

    private NetworkModeHostMenu networkModeHostMenu;

    public GameLauncher() {
        this.isRunning = false;
    }

    public void getHosterClass(NetworkModeHostMenu networkModeHostMenu) {
        this.networkModeHostMenu = networkModeHostMenu;
    }

    public void start(Stage primaryStage,
                      boolean online, boolean host, String hostName,
                      boolean botMode)
            throws IOException {
        if (this.isRunning) {
            return;
        }
        this.online = online;
        this.isRunning = true;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();

        this.game = new Game(this,
                controller,
                new Player("Player 1"), new Player("Player 2"),
                botMode,
                this.online, host, hostName,
                primaryStage);

        if (this.online) {
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
            if (this.online) {
                if (!this.game.getClient().hasConnectError()) {
                    this.show(primaryStage, root);
                }
            } else {
                this.show(primaryStage, root);
            }
        }
    }

    public void stop() {
        this.isRunning = false;

        // TODO nil
        if (Objects.nonNull(this.networkModeHostMenu)) {
            this.networkModeHostMenu.getServer().kill();
            this.networkModeHostMenu.kill();
            this.game.getClient().kill();
        }
//
//        if (Objects.nonNull(this.networkModeHostMenu)) {
//
//        }

        this.game = null;
    }

    private void show(Stage primaryStage, Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        primaryStage.setTitle("TicTacTecToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
