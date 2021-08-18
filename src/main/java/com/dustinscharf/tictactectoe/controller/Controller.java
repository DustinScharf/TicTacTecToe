package com.dustinscharf.tictactectoe.controller;

import com.dustinscharf.tictactectoe.game.Field;
import com.dustinscharf.tictactectoe.game.Game;
import com.dustinscharf.tictactectoe.game.Placer;
import com.dustinscharf.tictactectoe.game.Player;
import com.dustinscharf.tictactectoe.launcher.GameLauncher;
import com.dustinscharf.tictactectoe.launcher.menus.NetworkModeMenu;
import com.dustinscharf.tictactectoe.network.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {
    @FXML
    private Text textPlayer1;

    @FXML
    private Text textPlayer2;

    @FXML
    private GridPane gridPaneBoardFieldButtons;

    @FXML
    private VBox vBoxPlayer1PlacerButtons;

    @FXML
    private VBox vBoxPlayer2PlacerButtons;

    @FXML
    private Text challengedPlacerPlayer1;

    @FXML
    private Text challengedPlacerPlayer2;

    @FXML
    private Text messageBoxText;

    @FXML
    private TextField ipTextField;

    @FXML
    private Button resetButton;

    private Game controlledGame;

    private boolean guideIsOpen;

    public void receiveGame(Game game, Player player1, Player player2) {
        this.controlledGame = game;

        this.resetButton.setVisible(!this.controlledGame.isOnlineMode());
        this.ipTextField.setVisible(this.controlledGame.isOnlineMode() && this.controlledGame.isHost());
        this.ipTextField.setText(Network.getMyIP());

        this.guideIsOpen = false;

        List<Node> boardFieldButtons = this.gridPaneBoardFieldButtons.getChildren();

        List<Node> player1PlacersRows = this.vBoxPlayer1PlacerButtons.getChildren();
        List<Node> player1Placers = new ArrayList<>(20);
        player1Placers.addAll(((HBox) player1PlacersRows.get(1)).getChildren());
        player1Placers.addAll(((HBox) player1PlacersRows.get(0)).getChildren());

        List<Node> player2PlacersRows = this.vBoxPlayer2PlacerButtons.getChildren();
        List<Node> player2Placers = new ArrayList<>(20);
        player2Placers.addAll(((HBox) player2PlacersRows.get(0)).getChildren());
        player2Placers.addAll(((HBox) player2PlacersRows.get(1)).getChildren());

        game.initGame(
                player1, player2,
                this.textPlayer1, this.textPlayer2,
                player1Placers, player2Placers,
                boardFieldButtons,
                this.challengedPlacerPlayer1, this.challengedPlacerPlayer2,
                this.messageBoxText
        );
    }

    public void boardClicked(MouseEvent mouseEvent) {
        if (this.controlledGame.isOnlineMode() &&
                this.controlledGame.getCurrentPlayer() != this.controlledGame.getOnlinePlayer()) {
            return;
        }

        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
        if (!(clickedNode instanceof Canvas)) {
            return;
        }
        Canvas clickedCanvas = (Canvas) clickedNode;
        Field clickedField = this.controlledGame.getBoard().findFieldByButton(clickedCanvas);
        this.controlledGame.receiveBoardClick(clickedField);
        if (this.controlledGame.isOnlineMode()) {
            this.controlledGame.getClient().sendClickedFieldByCords(
                    clickedField.getBoardRowPosition(),
                    clickedField.getBoardColPosition()
            );
        }
    }

    public void placerClicked(MouseEvent mouseEvent) {
        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
        if (!(clickedNode instanceof Text)) {
            return;
        }
        Text clickedText = (Text) clickedNode;
        Placer clickedPlacer = this.controlledGame.getGamePlayer1().getPlacers().findPlacerByButton(clickedText);
        if (Objects.isNull(clickedPlacer)) {
            clickedPlacer = this.controlledGame.getGamePlayer2().getPlacers().findPlacerByButton(clickedText);
        }

        if (this.controlledGame.isOnlineMode() &&
                clickedPlacer.getOwner() != this.controlledGame.getOnlinePlayer()) {
            return;
        }

        this.controlledGame.receivePlacerClick(clickedPlacer);
        if (this.controlledGame.isOnlineMode()) {
            this.controlledGame.getClient().sendClickedPlacerByValue(clickedPlacer.getValue());
        }
    }

    public void resetClicker() {
        this.controlledGame.reset();
    }

    public void guideClicker() throws IOException {
        if (this.guideIsOpen) {
            return;
        }

        this.guideIsOpen = true;

        Stage secondaryStage = new Stage();

        secondaryStage.setMinWidth(600);
        secondaryStage.setMinHeight(400);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Guide.fxml"));
        Parent root = fxmlLoader.load();

//        Controller controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        // scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        secondaryStage.setTitle("TicTacTecToe | Guide");
        secondaryStage.setScene(scene);

        secondaryStage.setOnCloseRequest(windowEvent -> this.guideIsOpen = false);

        secondaryStage.show();
    }

    public void menuClick2Player1PC(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        new GameLauncher().start(stage, false, false);
    }

    public void menuClick2Player1Network(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        new NetworkModeMenu().show(stage);
    }
}
