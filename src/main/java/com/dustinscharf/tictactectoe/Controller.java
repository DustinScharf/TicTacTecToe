package com.dustinscharf.tictactectoe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

    private Game controlledGame;

    public void receiveGame(Game game, Player player1, Player player2) {
        this.controlledGame = game;

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
        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
        if (!(clickedNode instanceof Canvas)) {
            return;
        }
        Canvas clickedCanvas = (Canvas) clickedNode;
        Field clickedField = this.controlledGame.getBoard().findFieldByButton(clickedCanvas);
        this.controlledGame.receiveBoardClick(clickedField);
    }

    public void placerClicked(MouseEvent mouseEvent) {
        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
        if (!(clickedNode instanceof Text)) {
            return;
        }
        Text clickedText = (Text) clickedNode;
        Placer clickedPlacer = this.controlledGame.getGamePlayer1().placers.findPlacerByButton(clickedText);
        if (Objects.isNull(clickedPlacer)) {
            clickedPlacer = this.controlledGame.getGamePlayer2().placers.findPlacerByButton(clickedText);
        }
        this.controlledGame.receivePlacerClick(clickedPlacer);
    }

    public void resetClicker() {
        this.controlledGame.reset();
    }

    public void guideClicker() {

    }
}
