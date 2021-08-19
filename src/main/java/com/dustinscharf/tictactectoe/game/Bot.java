package com.dustinscharf.tictactectoe.game;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;

public class Bot extends GamePlayer {
    private Random random = new Random();

    public Bot(Text textPlayerName, Game game, Player player, List<Node> placerButtonList, Text placerChallengingAreaText, Color color) {
        super(textPlayerName, game, player, placerButtonList, placerChallengingAreaText, color);
    }

//    public void sendRandomPlacerToSelectionPhase() {
//        boolean foundAndSentPlacer = false;
//        while (!foundAndSentPlacer) {
//            Placer randomPlacer = super.placers.getPlacers()[this.random.nextInt(20)];
//            foundAndSentPlacer = super.placers.select(randomPlacer);
//        }
//        super.game.receivePlacerClick();
//    }

    public void selectRandomPlacer() {
        Placer randomPlacer;
        do {
            randomPlacer = super.placers.getPlacers()[this.random.nextInt(20)];
            if (randomPlacer.isThere()) {
                super.game.receivePlacerClick(randomPlacer);
            }
        } while (!randomPlacer.isThere());
    }

    private void placeOnRandomField() {
        boolean placeable = false;
        while (!placeable) {
            Field randomField = super.game.getBoard().getFields()
                    [this.random.nextInt(3)][this.random.nextInt(3)];

            placeable = randomField.couldSetPlacer(super.placers.getSelectedPlacer());

            boolean notAlreadyOwned = true;
            if (randomField.isSet()) {
                notAlreadyOwned = randomField.getPlacer().getOwner() != super.game.getGamePlayer2();
            }

            if (placeable && notAlreadyOwned) {
                super.game.receiveBoardClick(randomField);
            }
        }
    }

    private Task<Void> createRandomTimeSleeper(int maxWaitMilliseconds) {
        return new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(random.nextInt(maxWaitMilliseconds));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    public void placeRandomWithDelay() {
        Task<Void> sleeper = this.createRandomTimeSleeper(250);
        sleeper.setOnSucceeded(event -> {
            this.selectRandomPlacer();
            Task<Void> sleeper2 = this.createRandomTimeSleeper(750);
            sleeper2.setOnSucceeded(event2 -> this.placeOnRandomField());
            new Thread(sleeper2).start();
        });
        new Thread(sleeper).start();
    }
}
