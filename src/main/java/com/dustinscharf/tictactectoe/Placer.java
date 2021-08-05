package com.dustinscharf.tictactectoe;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class Placer {
    private Text button;
    private boolean isThere;
    private int value;

    private GamePlayer owner;

    public Placer(Text button, int value, GamePlayer owner) {
        this.button = button;
        this.isThere = true;
        this.value = value;
        this.owner = owner;
    }

    public Text getButton() {
        return button;
    }

    public boolean isThere() {
        return isThere;
    }

    public int getValue() {
        return value;
    }

    public GamePlayer getOwner() {
        return owner;
    }

    public boolean place(Field field) {
        if (!this.isThere) {
            return false;
        }

        boolean success = field.setPlacer(this);
        if (!success) {
            return false;
        }

        this.owner.placers.setSelectedPlacer(null);
        this.isThere = false;
        return true;
    }
}
