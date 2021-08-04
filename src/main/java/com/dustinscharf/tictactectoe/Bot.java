package com.dustinscharf.tictactectoe;

import java.util.Random;

public class Bot extends GamePlayer {
    public boolean placeRandom() {
        Random random = new Random();
        int rand0 = random.nextInt(20);
        int rand1 = random.nextInt(3);
        int rand2 = random.nextInt(3);

        super.placers.select(super.placers.getPlacers()[rand0]);
        super.game.getBoard().getFields()[rand1][rand2].setPlacer(this.placers.getSelectedPlacer());
        return true; // todo
    }
}
