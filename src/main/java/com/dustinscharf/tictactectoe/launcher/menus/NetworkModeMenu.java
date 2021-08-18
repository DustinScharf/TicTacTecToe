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
    public void show(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/NetworkMenu.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("TicTacTecToe");
        primaryStage.setScene(scene);
        primaryStage.show();

//        new GameLauncher().start(primaryStage, true, false);
    }

    public void toCreateGameMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        new NetworkModeHostMenu().show(stage);
    }
}
