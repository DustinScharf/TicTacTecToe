package com.dustinscharf.tictactectoe;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

public class Placer {
    private Canvas button;
    private boolean isThere;
    private int value;

    public Placer(Canvas button, int value) {
        this.button = button;
        this.isThere = true;
        this.value = value;
    }

    public Canvas getButton() {
        return button;
    }

    public boolean place(Field field) {
        field.setPlacer(this);
//        this.isThere = false;
        return true; // todo
    }
}
