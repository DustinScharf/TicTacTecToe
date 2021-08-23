package com.dustinscharf.tictactectoe.launcher.menus;

import com.dustinscharf.tictactectoe.launcher.GameLauncher;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NetworkModeMenu {
    private GameLauncher gameLauncher;

    public void show(Stage primaryStage, GameLauncher gameLauncher) throws IOException {
        this.gameLauncher = gameLauncher;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/NetworkMenu.fxml"));

        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("TicTacTecToe");
        primaryStage.setScene(scene);
        primaryStage.show();

//        new GameLauncher().start(primaryStage, true, false);
    }

    public void toCreateGameMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        new NetworkModeHostMenu().show(stage, this.gameLauncher);
    }

    public void toJoinGameMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        new NetworkModeJoinMenu().show(stage, this.gameLauncher);
    }

    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/StartMenu.fxml")).load()));
    }
}
