package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

import java.util.List;

public class Game {
    private Board board;

    private GamePlayer gamePlayer1;
    private GamePlayer gamePlayer2;

    private GamePlayer currentPlayer;

    private boolean isRunning;

    public Board getBoard() {
        return board;
    }

    public void initGame(List<Node> boardButtons,
                         List<Node> player1Placers,
                         List<Node> player2Placers) {
        // todo
    }

    public boolean isRunning() {
        return true; // todo
    }

    public void switchCurrentPlayer() {
        if (this.currentPlayer == this.gamePlayer1) this.currentPlayer = this.gamePlayer2;
        else this.currentPlayer = this.gamePlayer1;
    }
}
