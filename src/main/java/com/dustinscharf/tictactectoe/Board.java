package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.List;

public class Board {
    private Field[][] fields;

    public Board(List<Node> fieldButtonList) {
        int fieldButtonListInsertionIndex = 0;

        this.fields = new Field[3][];
        for (int i = 0; i < 3; ++i) {
            this.fields[i] = new Field[3];
            for (int j = 0; j < 3; ++j) {
                this.fields[i][j] = new Field((Canvas) fieldButtonList.get(fieldButtonListInsertionIndex++));
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
}
