package com.dustinscharf.tictactectoe.launcher.menus;

import animatefx.animation.Pulse;
import animatefx.animation.Tada;
import com.dustinscharf.tictactectoe.launcher.GameLauncher;
import com.dustinscharf.tictactectoe.network.Network;
import com.dustinscharf.tictactectoe.network.server.Server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NetworkModeHostMenu {
    @FXML
    private TextField ipTextField;

    @FXML
    private Label waitingText;

    private GameLauncher gameLauncher;

    private Server server;

    private Thread serverThread; // has kill

    public Server getServer() {
        return server;
    }

    public void show(Stage primaryStage, GameLauncher gameLauncher) throws IOException {
        this.gameLauncher = gameLauncher;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HostGameMenu.fxml"));

        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("TicTacTecToe");
        primaryStage.setScene(scene);

        this.ipTextField.setText(Network.getMyIP());

        new Pulse(this.waitingText).setCycleCount(999).play();

        primaryStage.show();

        server = new Server(primaryStage);
        Runnable serverRunnable = server::start;
        serverThread = new Thread(serverRunnable);
        serverThread.start();

        this.gameLauncher.start(primaryStage, true, true, Network.getMyIP(), false); //todo check start
        this.gameLauncher.getHosterClass(this);
        System.out.println("GL2:" + this.gameLauncher);
    }

    public void kill() {
        this.serverThread.stop();
    }
}
