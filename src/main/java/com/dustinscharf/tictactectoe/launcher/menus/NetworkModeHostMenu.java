package com.dustinscharf.tictactectoe.launcher.menus;

import animatefx.animation.Pulse;
import animatefx.animation.Tada;
import com.dustinscharf.tictactectoe.network.Network;
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

    public void show(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/HostGameMenu.fxml"));

        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("TicTacTecToe");
        primaryStage.setScene(scene);

        this.ipTextField.setText(Network.getMyIP());

        new Pulse(this.waitingText).setCycleCount(999).play();

        primaryStage.show();

//        new GameLauncher().start(primaryStage, true, false);
    }
}
