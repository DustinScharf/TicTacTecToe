package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

import java.util.List;

public class Placers {
    private Placer[] placers;
    private Placer selectedPlacer;

    public Placers(List<Node> placerButtonList) {
        int placerButtonListInsertionIndex = 0;
        this.placers = new Placer[20];
        for (int i = 0; i < 20; ++i) {
            this.placers[i] = new Placer((Canvas) placerButtonList.get(placerButtonListInsertionIndex++), i + 1);
        }
    }

    public Placer[] getPlacers() {
        return placers;
    }

    public Placer getSelectedPlacer() {
        return selectedPlacer;
    }

    public void select(Placer placer) {
        this.selectedPlacer = placer;
    }

    public Placer findPlacerByButton(Canvas button) {
        for (Placer placer : this.placers) {
            if (placer.getButton() == button) {
                return placer;
            }
        }
        return null;
    }
}
