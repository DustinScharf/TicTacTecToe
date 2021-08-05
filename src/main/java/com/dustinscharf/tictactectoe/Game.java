package com.dustinscharf.tictactectoe;

import javafx.scene.Node;

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
            if (this.checkForWinAfterPlace(clickedField)) {
                System.out.println("WIN!");
            } else {
                this.switchCurrentPlayer();
            }
        }
    }

    private boolean checkForWinAfterPlace(Field placedField) {
        int rowCombinationCounter = 0;
        int colCombinationCounter = 0;
        int diagonalCombinationCounter = 0;
        int antiDiagonalCombinationCounter = 0;

        int placedFieldRowPos = placedField.getBoardRowPosition();
        int placedFieldColPos = placedField.getBoardColPosition();

        GamePlayer winCheckingPlayer = placedField.getPlacer().getOwner();

        for (int i = 0; i < 3; ++i) {
            if (this.board.getFields()[placedFieldRowPos][i].isSet() &&
                    this.board.getFields()[placedFieldRowPos][i].getPlacer().getOwner() == winCheckingPlayer) {
                ++rowCombinationCounter;
            }

            if (this.board.getFields()[i][placedFieldColPos].isSet() &&
                    this.board.getFields()[i][placedFieldColPos].getPlacer().getOwner() == winCheckingPlayer) {
                ++colCombinationCounter;
            }

            if (this.board.getFields()[i][i].isSet() &&
                    this.board.getFields()[i][i].getPlacer().getOwner() == winCheckingPlayer) {
                ++diagonalCombinationCounter;
            }

            if (this.board.getFields()[i][2-i].isSet() &&
                    this.board.getFields()[i][2-i].getPlacer().getOwner() == winCheckingPlayer) {
                ++antiDiagonalCombinationCounter;
            }
        }

        if (rowCombinationCounter == 3 || colCombinationCounter == 3 ||
                diagonalCombinationCounter == 3 || antiDiagonalCombinationCounter == 3) {
            this.isRunning = false;
            return true;
        }

        return false;
    }

    public void receivePlacerClick(Placer clickedPlacer) {
        clickedPlacer.getOwner().placers.select(clickedPlacer);
    }
}
