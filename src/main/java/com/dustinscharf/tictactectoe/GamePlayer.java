package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Random;

public class GamePlayer {
    private Text textPlayerName;

    protected Game game;
    private Player player;
    protected Placers placers;

    private Color color;

    public GamePlayer(Text textPlayerName, Game game, Player player, List<Node> placerButtonList, Color color) {
        this.textPlayerName = textPlayerName;
        this.game = game;
        this.player = player;
        this.placers = new Placers(placerButtonList, this);
        this.color = color;

        this.textPlayerName.setText(this.player.getName());
    }

    public Text getTextPlayerName() {
        return textPlayerName;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRandomColor() {
        Random random = new Random();
        this.color = Color.rgb(
                random.nextInt(127) + 63,
                random.nextInt(127) + 63,
                random.nextInt(127) + 63
        );
    }
}
