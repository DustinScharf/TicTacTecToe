package com.dustinscharf.tictactectoe;

import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Placers {
    private Placer[] placers;
    private Placer selectedPlacer;

    private GamePlayer owner;

    private AudioClip placerSelectedSound;

    public Placers(List<Node> placerButtonList, GamePlayer owner) {
        int placerButtonListInsertionIndex = 0;
        this.placers = new Placer[20];
        this.selectedPlacer = null;
        this.owner = owner;
        for (int i = 0; i < 20; ++i) {
            this.placers[i] = new Placer(
                    (Text) placerButtonList.get(placerButtonListInsertionIndex++),
                    i + 1,
                    this.owner
            );
        }
        this.placerSelectedSound = new AudioClip(getClass().getResource("/placerSelected.wav").toExternalForm());
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

        this.placerSelectedSound.play();

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

    public void setVisible(boolean visible) {
        for (int i = 0; i < 20; ++i) {
            if (this.placers[i].isThere()) {
                this.placers[i].getButton().setVisible(visible);
            }
        }
    }

    public void revealRandomPlacer() {
        Random random = new Random();

        boolean foundUnrevealedPlacerButton = false;
        while (!foundUnrevealedPlacerButton) {
            int testIndex = random.nextInt(20);
            if (!this.placers[testIndex].getButton().isVisible() && this.placers[testIndex].isThere()) {
                foundUnrevealedPlacerButton = true;
                this.placers[testIndex].getButton().setVisible(true);
            }
        }
    }

    public Placer getHighestPlacer() {
        Placer currentMaxPlacer = null;

        for (Placer placer : this.placers) {
            if (placer.isThere()) {
                if (Objects.isNull(currentMaxPlacer)) {
                    currentMaxPlacer = placer;
                } else if (placer.getValue() > currentMaxPlacer.getValue()) {
                    currentMaxPlacer = placer;
                }
            }
        }

        return currentMaxPlacer;
    }

    public void reset() {
        for (Placer placer : this.placers) {
            placer.reset();
        }
        this.selectedPlacer = null;
    }
}
