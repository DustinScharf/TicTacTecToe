package com.dustinscharf.tictactectoe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class Controller {
    @FXML
    private GridPane gridPaneBoardFieldButtons;

    @FXML
    private VBox vBoxPlayer1PlacerButtons;

    @FXML
    private VBox vBoxPlayer2PlacerButtons;

    public void testController() {
        ObservableList<Node> boardFieldButtons = this.gridPaneBoardFieldButtons.getChildren();
        boardFieldButtons.forEach(System.out::println);
    }
}
