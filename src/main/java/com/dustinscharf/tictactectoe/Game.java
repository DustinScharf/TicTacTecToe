package com.dustinscharf.tictactectoe;

import animatefx.animation.*;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;

public class Game {
    private Board board;

    private GamePlayer gamePlayer1;
    private GamePlayer gamePlayer2;

    private GamePlayer currentPlayer;

    private boolean isRunning;

    private int round;
    private int playerTurns;

    private Field[] winFields;

    private AudioClip winSound;

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
                         Text textPlayer1,
                         Text textPlayer2,
                         List<Node> player1Placers,
                         List<Node> player2Placers,
                         List<Node> boardButtons) {
        this.gamePlayer1 = new GamePlayer(textPlayer1, this, player1, player1Placers, Color.SEAGREEN);
        this.gamePlayer2 = new GamePlayer(textPlayer2, this, player2, player2Placers, Color.SLATEBLUE);

        this.board = new Board(boardButtons);

        this.currentPlayer = this.gamePlayer1;
        this.currentPlayer.getTextPlayerName().setFill(this.currentPlayer.getColor());
        this.isRunning = true;

        this.round = 0;
        this.playerTurns = 0;

        this.winSound = new AudioClip(getClass().getResource("/gameWon.wav").toExternalForm());

//        this.gamePlayer1.getPlacers().setVisible(false);
//        this.gamePlayer2.getPlacers().setVisible(false);
//
//        this.currentPlayer.getPlacers().revealRandomPlacer();

        this.winFields = new Field[3];
    }

    public void switchCurrentPlayer() {
        this.currentPlayer.getTextPlayerName().setFill(Color.BLACK);

        if (this.currentPlayer == this.gamePlayer1) {
            this.currentPlayer = this.gamePlayer2;
        } else {
            this.currentPlayer = this.gamePlayer1;
            ++this.round;
        }

        ++this.playerTurns;

//        this.currentPlayer.getPlacers().revealRandomPlacer();

        this.currentPlayer.getTextPlayerName().setFill(this.currentPlayer.getColor());
    }

    public void receiveBoardClick(Field clickedField) {
        if (!this.currentPlayer.placers.hasPlacerSelected() || !this.isRunning) {
            return;
        }

        boolean success = this.currentPlayer.placers.getSelectedPlacer().place(clickedField);
        if (success) {
            if (this.checkForWinAfterPlace(clickedField)) {
                this.gameWon();
            } else {
                this.switchCurrentPlayer();
            }
        }
    }

    private void gameWon() {
        this.winSound.play();

        if (this.currentPlayer == this.gamePlayer1) this.gamePlayer2.getTextPlayerName().setFill(Color.LIGHTGRAY);
        else this.gamePlayer1.getTextPlayerName().setFill(Color.LIGHTGRAY);

        this.rotateAnimation(this.currentPlayer.getTextPlayerName());

        for (Field field : this.winFields) {
            new Tada(field.getButton()).setCycleCount(3).play();
        }

        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> reset());
        new Thread(sleeper).start();
    }

    private void rotateAnimation(Node node) {
        // Creating rotate transition
        RotateTransition rotateTransition = new RotateTransition();

        // Setting the duration for the transition
        rotateTransition.setDuration(Duration.millis(1000));

        // Setting the node for the transition
        rotateTransition.setNode(node); // this.currentPlayer.getTextPlayerName()

        // Setting the angle of the rotation
        rotateTransition.setByAngle(360);

        // Setting the cycle count for the transition
        rotateTransition.setCycleCount(3);

        // Setting auto reverse value to false
        rotateTransition.setAutoReverse(true);

        // Playing the animation
        rotateTransition.play();
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

            if (this.board.getFields()[i][2 - i].isSet() &&
                    this.board.getFields()[i][2 - i].getPlacer().getOwner() == winCheckingPlayer) {
                ++antiDiagonalCombinationCounter;
            }
        }

        if (rowCombinationCounter == 3) {
            for (int i = 0; i < 3; ++i) {
                this.winFields[i] = this.board.getFields()[placedFieldRowPos][i];
            }
            this.isRunning = false;
            return true;
        }

        if (colCombinationCounter == 3) {
            for (int i = 0; i < 3; ++i) {
                this.winFields[i] = this.board.getFields()[i][placedFieldColPos];
            }
            this.isRunning = false;
            return true;
        }

        if (diagonalCombinationCounter == 3) {
            for (int i = 0; i < 3; ++i) {
                this.winFields[i] = this.board.getFields()[i][i];
            }
            this.isRunning = false;
            return true;
        }

        if (antiDiagonalCombinationCounter == 3) {
            for (int i = 0; i < 3; ++i) {
                this.winFields[i] = this.board.getFields()[i][2 - i];
            }
            this.isRunning = false;
            return true;
        }

        return false;
    }

    public void receivePlacerClick(Placer clickedPlacer) {
        clickedPlacer.getOwner().placers.select(clickedPlacer);
    }

    public void reset() {
        this.gamePlayer1.reset();
        this.gamePlayer2.reset();

        this.board.reset();

        this.currentPlayer = this.gamePlayer1;
        this.currentPlayer.getTextPlayerName().setFill(this.currentPlayer.getColor());
        this.isRunning = true;

        this.round = 0;

//        this.gamePlayer1.getPlacers().setVisible(false);
//        this.gamePlayer2.getPlacers().setVisible(false);
//
//        this.currentPlayer.getPlacers().revealRandomPlacer();

        this.gamePlayer2.getTextPlayerName().setFill(Color.BLACK);
    }
}
