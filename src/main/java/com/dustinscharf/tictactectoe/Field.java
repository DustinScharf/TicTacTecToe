package com.dustinscharf.tictactectoe;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

public class Field {
    private Canvas button;
    private Placer placer;

    public Canvas getButton() {
        return button;
    }

    public boolean setPlacer(Placer placer) {
        this.placer = placer;
        return true; // todo
    }
}
