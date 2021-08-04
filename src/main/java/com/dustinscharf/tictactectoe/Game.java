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

    public Game(Controller controller, Player player1, Player player2) {
        controller.receiveGame(this, player1, player2);
    }

    public Board getBoard() {
        return board;
    }

    public void initGame(Player player1,
                         Player player2,
                         List<Node> boardButtons,
                         List<Node> player1Placers,
                         List<Node> player2Placers) {

        this.gamePlayer1 = new GamePlayer(this, player1Placers, player1);
        this.gamePlayer2 = new GamePlayer(this, player2Placers, player2);

        this.board = new Board(boardButtons);
    }

    public boolean isRunning() {
        return true; // todo
    }

    public void switchCurrentPlayer() {
        if (this.currentPlayer == this.gamePlayer1) this.currentPlayer = this.gamePlayer2;
        else this.currentPlayer = this.gamePlayer1;
    }
}
