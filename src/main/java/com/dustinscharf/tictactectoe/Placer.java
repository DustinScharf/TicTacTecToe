package com.dustinscharf.tictactectoe;

import javafx.scene.text.Text;

public class Placer {
    private Text button;
    private boolean isThere;
    private int value;

    private GamePlayer owner;

    private String oldButtonText;
    public static final String CENSORED_TEXT = "?";

    public Placer(Text button, int value, GamePlayer owner) {
        this.button = button;
        this.isThere = true;
        this.value = value;
        this.owner = owner;
        this.oldButtonText = this.button.getText();
    }

    public Text getButton() {
        return button;
    }

    public boolean isThere() {
        return isThere;
    }

    public void setThere(boolean there) {
        isThere = there;
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
        this.button.setUnderline(false);
        return true;
    }

    public void setCensored(boolean censored) {
        if (censored) {
            this.button.setText(CENSORED_TEXT);
        } else {
            this.button.setText(this.oldButtonText);
        }
    }

    public void reset() {
        this.button.setUnderline(false);
        this.button.setVisible(true);
        this.isThere = true;
    }
}
