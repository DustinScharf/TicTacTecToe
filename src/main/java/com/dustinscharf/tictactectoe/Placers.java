package com.dustinscharf.tictactectoe;

import javafx.scene.canvas.Canvas;

public class Placers {
    private Placer[] placers;
    private Placer selectedPlacer;

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
