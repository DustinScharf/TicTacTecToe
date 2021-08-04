package com.dustinscharf.tictactectoe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    private GridPane gridPaneBoardFieldButtons;

    @FXML
    private VBox vBoxPlayer1PlacerButtons;

    @FXML
    private VBox vBoxPlayer2PlacerButtons;

    private Game controlledGame;

    public void receiveGame(Game game) {
        this.controlledGame = game;
    }

    public void boardClicked(MouseEvent mouseEvent) {
        System.out.println("Clicked!");
        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
        System.out.println(clickedNode);

        GraphicsContext graphicsContext = ((Canvas) clickedNode).getGraphicsContext2D();
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.setStroke(Color.BLUE);
        graphicsContext.setLineWidth(5);
        graphicsContext.strokeLine(40, 10, 10, 40);
    }

    public void testController() {
        ObservableList<Node> boardFieldButtons = this.gridPaneBoardFieldButtons.getChildren();
        boardFieldButtons.forEach(System.out::println);
    }
}
