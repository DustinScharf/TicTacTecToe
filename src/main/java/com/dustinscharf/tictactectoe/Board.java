package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.Objects;

public class Board {
    private Field[][] fields;

    public Board(List<Node> fieldButtonList) {
        int fieldButtonListInsertionIndex = 0;

        this.fields = new Field[3][];
        for (int row = 0; row < 3; ++row) {
            this.fields[row] = new Field[3];
            for (int col = 0; col < 3; ++col) {
                this.fields[row][col] = new Field(
                        (Canvas) fieldButtonList.get(fieldButtonListInsertionIndex++),
                        row,
                        col
                );
            }
        }
    }

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

    public Placer getHighestPlacer() {
        Placer currentMaxPlacer = null;

        for (Field[] fieldRow : this.fields) {
            for (Field field : fieldRow) {
                if (field.isSet()) {
                    if (Objects.isNull(currentMaxPlacer)) {
                        currentMaxPlacer = field.getPlacer();
                    } else if (field.getPlacer().getValue() > currentMaxPlacer.getValue()) {
                        currentMaxPlacer = field.getPlacer();
                    }
                }
            }
        }

        return currentMaxPlacer;
    }

    public void reset() {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.fields[row][col].reset();
            }
        }
    }
}
