package com.dustinscharf.tictactectoe;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

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

        return true;
    }

    public boolean isSet() {
        return Objects.nonNull(this.placer);
    }
}
