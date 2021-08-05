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

    public GamePlayer getGamePlayer1() {
        return gamePlayer1;
    }

    public GamePlayer getGamePlayer2() {
        return gamePlayer2;
    }

    public void initGame(Player player1,
                         Player player2,
                         List<Node> boardButtons,
                         List<Node> player1Placers,
                         List<Node> player2Placers) {

        this.gamePlayer1 = new GamePlayer(this, player1Placers, player1);
        this.gamePlayer2 = new GamePlayer(this, player2Placers, player2);

        this.board = new Board(boardButtons);

        this.currentPlayer = this.gamePlayer1;
        this.isRunning = true;
    }

    public boolean isRunning() {
        return true; // todo
    }

    public void switchCurrentPlayer() {
        if (this.currentPlayer == this.gamePlayer1) this.currentPlayer = this.gamePlayer2;
        else this.currentPlayer = this.gamePlayer1;
    }

    public void receiveBoardClick(Field clickedField) {
        if (!this.currentPlayer.placers.hasPlacerSelected() || !this.isRunning) {
            return;
        }

        boolean success = this.currentPlayer.placers.getSelectedPlacer().place(clickedField);
        if (success) {
            if (this.checkForWin()) {
                // todo winner
            } else {
                this.switchCurrentPlayer();
            }
        }
    }

    private boolean checkForWin() {
        // todo win check
        this.isRunning = true;
        return false;
    }

    public void receivePlacerClick(Placer clickedPlacer) {
        clickedPlacer.getOwner().placers.select(clickedPlacer);
    }
}
