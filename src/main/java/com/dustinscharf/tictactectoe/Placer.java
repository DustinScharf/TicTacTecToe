package com.dustinscharf.tictactectoe;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Placer {
    private TextArea button;
    private boolean isThere;
    private int value;

    public Placer(TextArea button, int value) {
        this.button = button;
        this.isThere = true;
        this.value = value;
    }

    public TextArea getButton() {
        return button;
    }

    public boolean place(Field field) {
        field.setPlacer(this);
//        this.isThere = false;
        return true; // todo
    }
}
