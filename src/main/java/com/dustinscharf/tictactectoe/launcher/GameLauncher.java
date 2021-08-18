package com.dustinscharf.tictactectoe.launcher;

import com.dustinscharf.tictactectoe.controller.Controller;
import com.dustinscharf.tictactectoe.game.Game;
import com.dustinscharf.tictactectoe.game.Player;
import com.dustinscharf.tictactectoe.network.server.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameLauncher {
    public void start(Stage primaryStage, boolean online, boolean host) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();

//        boolean onlineMode = false;
//        int playOnlineGame = JOptionPane.showConfirmDialog(null,
//                "Play online (or offline)?",
//                "TicTacTecToe | Launcher",
//                JOptionPane.YES_NO_OPTION);
//        if (playOnlineGame == 0) {
//            onlineMode = true;
//        }
//
//        int createOnlineGame = -1;
//        if (onlineMode) {
//            createOnlineGame = JOptionPane.showConfirmDialog(null,
//                    "Create new game (or join game)?",
//                    "TicTacTecToe | Launcher",
//                    JOptionPane.YES_NO_OPTION);
//            if (createOnlineGame == 0) {
////                new Thread(Server::new).start();
//
//                Runnable serverRunnable = () -> {
//                    final Server server = new Server();
//                    primaryStage.setOnCloseRequest(windowEvent -> server.close());
//                };
//                Thread serverThread = new Thread(serverRunnable);
//                serverThread.start();
//            }
//        }
//
//        boolean isHost = createOnlineGame == 0;

        Game game = new Game(controller,
                new Player("Player 1"), new Player("Player 2"),
                online, host);

        if (online) {
            game.setOnlinePlayer(host ? game.getGamePlayer1() : game.getGamePlayer2());
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        primaryStage.setTitle("TicTacTecToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
