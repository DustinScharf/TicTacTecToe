package com.dustinscharf.tictactectoe;

public class GamePlayer {
    protected Game game;
    private Player player;
    protected Placers placers;

    public boolean place(Field field) {
        field.setPlacer(this.placers.getSelectedPlacer());
        return true; // todo
    }
}
