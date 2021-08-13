package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GamePlayer {
    private Text textPlayerName;

    protected Game game;
    private Player player;
    protected Placers placers;

    private PlacerChallengingArea placerChallengingArea;

    private Color color;

    public GamePlayer(Text textPlayerName,
                      Game game,
                      Player player,
                      List<Node> placerButtonList,
                      Text placerChallengingAreaText,
                      Color color) {
        this.textPlayerName = textPlayerName;
        this.game = game;
        this.player = player;
        this.placers = new Placers(placerButtonList, this);
        this.color = color;

        this.placerChallengingArea = new PlacerChallengingArea(placerChallengingAreaText);

        this.textPlayerName.setText(this.player.getName());
    }

    public Text getTextPlayerName() {
        return textPlayerName;
    }

    public Placers getPlacers() {
        return placers;
    }

    public PlacerChallengingArea getPlacerChallengingArea() {
        return placerChallengingArea;
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

    public boolean isUnableToPlace() {
        if (Objects.isNull(this.placers.getHighestPlacer()) ||
                Objects.isNull(this.game.getBoard().getLowestPlacer())) {
            return false;
        }

        return this.placers.getHighestPlacer().getValue() <= this.game.getBoard().getLowestPlacer().getValue();
    }

    public void reset() {
        this.placers.reset();
    }
}
