package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

import java.util.List;

public class GamePlayer {
    protected Game game;
    private Player player;
    protected Placers placers;

    public GamePlayer(Game game, List<Node> placerButtonList, Player player) {
        this.game = game;

        this.player = player;

        this.placers = new Placers(placerButtonList);
    }

    public boolean place(Field field) {
        field.setPlacer(this.placers.getSelectedPlacer());
        return true; // todo
    }
}
