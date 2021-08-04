package com.dustinscharf.tictactectoe;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Field {
    private Canvas button;
    private Placer placer;

    public Field(Canvas button) {
        this.button = button;
        this.placer = null;
    }

    public Canvas getButton() {
        return button;
    }

    public boolean setPlacer(Placer placer) {
        this.placer = placer;
        return true; // todo
    }
}
