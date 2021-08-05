package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

public class Placers {
    private Placer[] placers;
    private Placer selectedPlacer;

    private GamePlayer owner;

    public Placers(List<Node> placerButtonList, GamePlayer owner) {
        int placerButtonListInsertionIndex = 0;
        this.placers = new Placer[20];
        this.owner = owner;
        for (int i = 0; i < 20; ++i) {
            this.placers[i] = new Placer(
                    (Text) placerButtonList.get(placerButtonListInsertionIndex++),
                    i + 1,
                    this.owner
            );
        }
    }

    public Placer[] getPlacers() {
        return placers;
    }

    public Placer getSelectedPlacer() {
        return selectedPlacer;
    }

    public void setSelectedPlacer(Placer selectedPlacer) {
        this.selectedPlacer = selectedPlacer;
    }

    public boolean hasPlacerSelected() {
        return Objects.nonNull(this.selectedPlacer);
    }

    public boolean select(Placer placer) {
        if (!placer.isThere()) return false;

        if (this.hasPlacerSelected()) {
            this.selectedPlacer.getButton().setUnderline(false);
        }

        this.selectedPlacer = placer;
        this.selectedPlacer.getButton().setUnderline(true);

        return true;
    }

    public Placer findPlacerByButton(Text button) {
        for (Placer placer : this.placers) {
            if (placer.getButton() == button) {
                return placer;
            }
        }
        return null;
    }
}
