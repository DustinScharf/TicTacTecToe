package com.dustinscharf.tictactectoe.game;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

public class Field {
    private Canvas button;
    private Placer placer;

    private int boardRowPosition;
    private int boardColPosition;

    private AudioClip fieldChangedSound;

    public Field(Canvas button, int boardRowPosition, int boardColPosition) {
        this.button = button;
        this.placer = null;
        this.boardRowPosition = boardRowPosition;
        this.boardColPosition = boardColPosition;

        this.fieldChangedSound = new AudioClip(getClass().getResource("/fieldChanged.wav").toExternalForm());
    }

    public Canvas getButton() {
        return button;
    }

    public Placer getPlacer() {
        return placer;
    }

    public boolean setPlacer(Placer placer) {
        if (this.isSet() && placer.getValue() <= this.placer.getValue()) {
            return false;
        }

        this.placer = placer;

        placer.getButton().setVisible(false);

        GraphicsContext graphicsContext = this.button.getGraphicsContext2D();

        graphicsContext.clearRect(0, 0, 999, 999);

        graphicsContext.setFill(this.placer.getOwner().getColor());

        graphicsContext.setFont(new Font("System", this.placer.getValue() + 10));
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.fillText(
                this.placer.getValue() + "",
                Math.round(this.button.getWidth() / 2),
                Math.round(this.button.getHeight() / 2)
        );

        this.fieldChangedSound.play();

        return true;
    }

    public int getBoardRowPosition() {
        return boardRowPosition;
    }

    public int getBoardColPosition() {
        return boardColPosition;
    }

    public boolean isSet() {
        return Objects.nonNull(this.placer);
    }

    public void reset() {
        GraphicsContext graphicsContext = this.button.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, 999, 999);

        this.placer = null;
    }
}
