package com.dustinscharf.tictactectoe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Controller {
    @FXML
    private GridPane gridPaneBoardFieldButtons;

    @FXML
    private VBox vBoxPlayer1PlacerButtons;

    @FXML
    private VBox vBoxPlayer2PlacerButtons;

    private Game controlledGame;

    public void receiveGame(Game game, Player player1, Player player2) {
        this.controlledGame = game;

        List<Node> boardFieldButtons = this.gridPaneBoardFieldButtons.getChildren();
        System.out.println(boardFieldButtons);

        List<Node> player1PlacersRows = this.vBoxPlayer1PlacerButtons.getChildren();
        List<Node> player1Placers = new ArrayList<>(20);
        player1Placers.addAll(((HBox) player1PlacersRows.get(1)).getChildren());
        player1Placers.addAll(((HBox) player1PlacersRows.get(0)).getChildren());

        List<Node> player2PlacersRows = this.vBoxPlayer2PlacerButtons.getChildren();
        List<Node> player2Placers = new ArrayList<>(20);
        player2Placers.addAll(((HBox) player2PlacersRows.get(0)).getChildren());
        player2Placers.addAll(((HBox) player2PlacersRows.get(1)).getChildren());

        game.initGame(player1, player2, boardFieldButtons, player1Placers, player2Placers);
    }

    public void boardClicked(MouseEvent mouseEvent) { // todo
        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();

        System.out.println(clickedNode);

        GraphicsContext graphicsContext = ((Canvas) clickedNode).getGraphicsContext2D();
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.setLineWidth(5);
        graphicsContext.strokeLine(40, 10, 10, 40);
    }

    public void testController() { // todo
//        ObservableList<Node> boardFieldButtons = this.gridPaneBoardFieldButtons.getChildren();
//        boardFieldButtons.forEach(System.out::println);
//        System.out.println(this.vBoxPlayer1PlacerButtons.getChildren());
    }
}
