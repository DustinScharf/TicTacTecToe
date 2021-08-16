package com.dustinscharf.tictactectoe.game;

import animatefx.animation.*;
import com.dustinscharf.tictactectoe.controller.Controller;
import com.dustinscharf.tictactectoe.network.client.Client;
import com.dustinscharf.tictactectoe.network.server.Server;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {
    private Board board;

    private PlacerChallengeAreas placerChallengeAreas;

    private GamePlayer gamePlayer1;
    private GamePlayer gamePlayer2;

    private GamePlayer currentPlayer;

    private boolean isRunning;

    private boolean inSelectionPhase;

    private int round;
    private int playerTurns;

    private Field[] winFields;

    private AudioClip winSound;
    private AudioClip resetSound;

    private AudioClip placementPhaseSound;

    private Text messageBoxText;

    private boolean onlineMode;
    private GamePlayer onlinePlayer;

    private Client client;
    private Server server;

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

    public GamePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isOnlineMode() {
        return onlineMode;
    }

    public GamePlayer getOnlinePlayer() {
        return onlinePlayer;
    }

    public Client getClient() {
        return client;
    }

    public void initGame(Player player1,
                         Player player2,
                         Text textPlayer1,
                         Text textPlayer2,
                         List<Node> player1Placers,
                         List<Node> player2Placers,
                         List<Node> boardButtons,
                         Text textPlayer1ChallengeArea,
                         Text textPlayer2ChallengeArea,
                         Text messageBoxText) {

        this.messageBoxText = messageBoxText;
        this.messageBoxText.setVisible(false);

        this.gamePlayer1 = new GamePlayer(
                textPlayer1,
                this,
                player1,
                player1Placers,
                textPlayer1ChallengeArea,
                Color.SEAGREEN
        );
        this.gamePlayer2 = new GamePlayer(
                textPlayer2,
                this,
                player2,
                player2Placers,
                textPlayer2ChallengeArea,
                Color.SLATEBLUE
        );

        this.board = new Board(boardButtons);

        this.placerChallengeAreas = new PlacerChallengeAreas(
                this.gamePlayer1.getPlacerChallengingArea(),
                this.gamePlayer2.getPlacerChallengingArea()
        );

        this.currentPlayer = this.gamePlayer1;
        this.currentPlayer.getTextPlayerName().setFill(this.currentPlayer.getColor());
        this.isRunning = true;

        this.round = 0;
        this.playerTurns = 0;

        this.winSound = new AudioClip(getClass().getResource("/gameWon.wav").toExternalForm());
        this.resetSound = new AudioClip(getClass().getResource("/gameReset.wav").toExternalForm());

        this.placementPhaseSound = new AudioClip(getClass().getResource("/placementPhase.wav").toExternalForm());

        this.onlineMode = false; // todo
        this.initClientForOnlineMode(); // todo

        this.initSelectionPhase();

//        this.gamePlayer1.getPlacers().setVisible(false);
//        this.gamePlayer2.getPlacers().setVisible(false);
//
//        this.currentPlayer.getPlacers().revealRandomPlacer();

        this.winFields = new Field[3];
    }

    private void initClientForOnlineMode() {
//        this.server = new Server();

        this.onlineMode = true;
        this.onlinePlayer = this.gamePlayer1;
        this.client = new Client();
    }

    public void switchCurrentPlayer() {
        this.currentPlayer.getTextPlayerName().setFill(Color.BLACK);

        if (this.currentPlayer == this.gamePlayer1) {
            this.currentPlayer = this.gamePlayer2;
        } else {
            this.currentPlayer = this.gamePlayer1;
        }

        ++this.round;

//        this.currentPlayer.getPlacers().revealRandomPlacer();

        this.currentPlayer.getTextPlayerName().setFill(this.currentPlayer.getColor());

        if (this.currentPlayer.isUnableToPlace()) {
            if (this.currentPlayer == this.gamePlayer1) {
                this.gameWon(this.gamePlayer2);
            } else {
                this.gameWon(this.gamePlayer1);
            }
        }
    }

    public void receiveBoardClick(Field clickedField) {
        if (!this.isRunning) {
            return;
        }

        if (!this.currentPlayer.placers.hasPlacerSelected()) {
            return;
        }

        boolean success = this.currentPlayer.placers.getSelectedPlacer().place(clickedField);
        if (success) {
            ++this.playerTurns;
            if (this.checkForWinAfterPlace(clickedField)) {
                this.gameWon(this.currentPlayer);
            } else {
                if (this.playerTurns % 2 == 0) {
                    this.initSelectionPhase();
                } else {
                    this.switchCurrentPlayer();
                }
            }
        }
    }

    private void gameWon(GamePlayer winningPlayer) {
        this.isRunning = false;

        this.winSound.play();

        this.gamePlayer1.getTextPlayerName().setFill(Color.LIGHTGRAY);
        this.gamePlayer2.getTextPlayerName().setFill(Color.LIGHTGRAY);

        winningPlayer.getTextPlayerName().setFill(winningPlayer.getColor());

        this.rotateAnimation(winningPlayer.getTextPlayerName());

        if (Objects.nonNull(this.winFields[2])) {
            for (Field field : this.winFields) {
                new Tada(field.getButton()).setCycleCount(3).play();
            }
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
            return true;
        }

        if (colCombinationCounter == 3) {
            for (int i = 0; i < 3; ++i) {
                this.winFields[i] = this.board.getFields()[i][placedFieldColPos];
            }
            return true;
        }

        if (diagonalCombinationCounter == 3) {
            for (int i = 0; i < 3; ++i) {
                this.winFields[i] = this.board.getFields()[i][i];
            }
            return true;
        }

        if (antiDiagonalCombinationCounter == 3) {
            for (int i = 0; i < 3; ++i) {
                this.winFields[i] = this.board.getFields()[i][2 - i];
            }
            return true;
        }

        return false;
    }

    public void receivePlacerClick(Placer clickedPlacer) {
        if (this.inSelectionPhase) {
            clickedPlacer.getOwner().getPlacerChallengingArea().setChallengedPlacer(clickedPlacer);
            if (this.placerChallengeAreas.isReady()) {
                this.currentPlayer = this.placerChallengeAreas.getHigherPlayer();
                if (!Objects.nonNull(this.currentPlayer)) {
                    Random random = new Random();
                    int randomIntForPlayerSelection = random.nextInt(2);
                    switch (randomIntForPlayerSelection) {
                        case 0:
                            this.currentPlayer = this.gamePlayer1;
                            break;
                        case 1:
                            this.currentPlayer = this.gamePlayer2;
                            break;
                        default:
                            System.err.println("Random number created wrong / out of bounds");
                            System.exit(1);
                            break;
                    }
                }
                this.inSelectionPhase = false;
                this.currentPlayer.getTextPlayerName().setFill(this.currentPlayer.getColor());

                if (this.currentPlayer.isUnableToPlace()) {
                    if (this.currentPlayer == this.gamePlayer1) {
                        this.gameWon(this.gamePlayer2);
                    } else {
                        this.gameWon(this.gamePlayer1);
                    }
                }
            }
        } else {
            clickedPlacer.getOwner().placers.select(clickedPlacer);
        }
    }

    public void sendMessageToScreen(String message, int seconds) {
        this.messageBoxText.setText(message);
        new FadeIn(this.messageBoxText).play();
        this.messageBoxText.setVisible(true);

        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(seconds * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> new FadeOut(this.messageBoxText).play());
        new Thread(sleeper).start();
    }

    public void initSelectionPhase() {
        this.inSelectionPhase = true;
        this.sendMessageToScreen("Selection Phase", 3);
//        this.placementPhaseSound.play();
        this.gamePlayer1.getTextPlayerName().setFill(Color.BLACK);
        this.gamePlayer2.getTextPlayerName().setFill(Color.BLACK);
    }

    public void reset() {
        this.resetSound.play();

        this.gamePlayer1.reset();
        this.gamePlayer2.reset();

        this.board.reset();

        this.currentPlayer = this.gamePlayer1;
        this.currentPlayer.getTextPlayerName().setFill(this.currentPlayer.getColor());
        this.isRunning = true;

        this.initSelectionPhase();

        this.round = 0;
        this.playerTurns = 0;

//        this.gamePlayer1.getPlacers().setVisible(false);
//        this.gamePlayer2.getPlacers().setVisible(false);
//
//        this.currentPlayer.getPlacers().revealRandomPlacer();

        this.gamePlayer2.getTextPlayerName().setFill(Color.BLACK);

        this.messageBoxText.setVisible(false);
    }
}
