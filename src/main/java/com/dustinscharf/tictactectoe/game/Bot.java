package com.dustinscharf.tictactectoe.game;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;

public class Bot extends GamePlayer {
    private Random random = new Random();
    ;

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

    public void placeRandom() {
        this.selectRandomPlacer();
        this.placeOnRandomField();
    }
}
