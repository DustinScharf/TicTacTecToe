package com.dustinscharf.tictactectoe.launcher.menus;

import com.dustinscharf.tictactectoe.launcher.GameLauncher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NetworkModeJoinMenu {
    @FXML
    private TextField joinIP;

    private Stage primaryStage;

    private GameLauncher gameLauncher;

    public void show(Stage primaryStage, GameLauncher gameLauncher) throws IOException {
        this.gameLauncher = gameLauncher;

        this.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JoinGameMenu.fxml"));

        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        this.primaryStage.setTitle("TicTacTecToe");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void joinNow(ActionEvent actionEvent) throws IOException {
        this.gameLauncher.start(this.primaryStage, true, false, joinIP.getText(), false);
    }

    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/StartMenu.fxml")).load()));
    }
}
