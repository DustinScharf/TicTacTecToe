package com.dustinscharf.tictactectoe;

import javafx.scene.text.Text;

public class PlacerChallengingArea {
    private Text challengedPlacerText;
    private Placer challengedPlacer;

    public PlacerChallengingArea(Text challengedPlacerText) {
        this.challengedPlacerText = challengedPlacerText;
    }

    public void setChallengedPlacer(Placer challengedPlacer) {
        this.challengedPlacer = challengedPlacer;
    }

    public Placer getChallengedPlacer() {
        return challengedPlacer;
    }
}
