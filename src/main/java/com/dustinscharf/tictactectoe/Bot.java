package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;

public class Bot extends GamePlayer {
    public Bot(Text textPlayerName, Game game, Player player, List<Node> placerButtonList, Color color) {
        super(textPlayerName, game, player, placerButtonList, color);
    }

    public boolean placeRandom() {
        Random random = new Random();
        int rand1 = random.nextInt(3);
        int rand2 = random.nextInt(3);

        // todo super ineffective and can cause errors
        while (!super.placers.select(super.placers.getPlacers()[random.nextInt(20)])) continue;

        // todo super ineffective and can cause errors
        while (!super.game.getBoard().getFields()[rand1][rand2].setPlacer(this.placers.getSelectedPlacer())) continue;

        return true; // todo board could be full or something
    }
}
