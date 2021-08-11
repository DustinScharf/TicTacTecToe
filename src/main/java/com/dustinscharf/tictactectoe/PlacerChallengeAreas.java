package com.dustinscharf.tictactectoe;

import javafx.concurrent.Task;
import javafx.scene.text.Text;
import org.apache.commons.collections4.functors.OnePredicate;

import java.util.Objects;

public class PlacerChallengeAreas {
    private PlacerChallengingArea player1PlacerChallengingArea;
    private PlacerChallengingArea player2PlacerChallengingArea;

    public PlacerChallengeAreas(PlacerChallengingArea player1PlacerChallengingArea,
                                PlacerChallengingArea player2PlacerChallengingArea) {
        this.player1PlacerChallengingArea = player1PlacerChallengingArea;
        this.player2PlacerChallengingArea = player2PlacerChallengingArea;
    }

    public boolean isReady() {
        return this.player1PlacerChallengingArea.isSet() && this.player2PlacerChallengingArea.isSet();
    }

    public GamePlayer getHigherPlayer() {
        GamePlayer higherPlayer = null;

        if (
                this.player1PlacerChallengingArea.getChallengedPlacer().getValue() >
                        this.player2PlacerChallengingArea.getChallengedPlacer().getValue()
        ) {
            higherPlayer = this.player1PlacerChallengingArea.getChallengedPlacer().getOwner();

        } else if (
                this.player1PlacerChallengingArea.getChallengedPlacer().getValue() <
                        this.player2PlacerChallengingArea.getChallengedPlacer().getValue()
        ) {
            higherPlayer = this.player2PlacerChallengingArea.getChallengedPlacer().getOwner();

        }

        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> reset());
        new Thread(sleeper).start();

        if (Objects.nonNull(higherPlayer)) {
            higherPlayer.getPlacerChallengingArea().getChallengedPlacerText().setUnderline(true);
        }

        return higherPlayer;
    }

    public void reset() {
        this.player1PlacerChallengingArea.reset();
        this.player2PlacerChallengingArea.reset();
    }
}
