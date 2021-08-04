package com.dustinscharf.tictactectoe;

import javafx.scene.canvas.Canvas;

public class Board {
    private Field[][] fields;

    public Field[][] getFields() {
        return fields;
    }

    public Field findFieldByButton(Canvas button) {
        for (Field[] fieldRow : this.fields) {
            for (Field field : fieldRow) {
                if (field.getButton() == button) {
                    return field;
                }
            }
        }
        return null;
    }
}
