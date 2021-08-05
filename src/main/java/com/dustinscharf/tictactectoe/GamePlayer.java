package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class GamePlayer {
    protected Game game;
    private Player player;
    protected Placers placers;

    private Color color;

    public GamePlayer(Game game, List<Node> placerButtonList, Player player) {
        this.game = game;
        this.player = player;
        this.placers = new Placers(placerButtonList, this);

        Random random = new Random();

        this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public Color getColor() {
        return color;
    }
}
