package com.dustinscharf.tictactectoe;

import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;

public class PlacerChallengingArea {
    private Text challengedPlacerText;
    private Placer challengedPlacer;

    private boolean isSet;

    private AudioClip placerSetSound;

    public PlacerChallengingArea(Text challengedPlacerText) {
        this.challengedPlacerText = challengedPlacerText;
        this.isSet = false;

        this.placerSetSound = new AudioClip(
                getClass().getResource("/selectionPhasePlacerSelected.wav").toExternalForm()
        );
    }

    public Text getChallengedPlacerText() {
        return challengedPlacerText;
    }

    public void setChallengedPlacer(Placer challengedPlacer) {
        if (this.isSet || !challengedPlacer.isThere()) {
            return;
        }

        this.challengedPlacer = challengedPlacer;
        this.challengedPlacerText.setText(this.challengedPlacer.getValue() + "");

        this.setCensored(true);

        this.challengedPlacer.getOwner().getPlacers().setVisible(false);

        this.challengedPlacer.setThere(false);
        this.challengedPlacer.getButton().setVisible(false);

        this.placerSetSound.play();

        this.isSet = true;
    }

    public Placer getChallengedPlacer() {
        return challengedPlacer;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public void setCensored(boolean censored) {
        this.challengedPlacer.setCensored(censored);
        this.challengedPlacerText.setText(this.challengedPlacer.getButton().getText());
    }

    public void reset() {
        this.challengedPlacerText.setText("><");
        this.challengedPlacerText.setUnderline(false);
        this.challengedPlacer = null;
        this.isSet = false;
    }
}
