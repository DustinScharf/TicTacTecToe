package com.dustinscharf.tictactectoe;

import javafx.scene.text.Text;

public class PlacerChallengeAreas {
    private PlacerChallengingArea player1PlacerChallengingArea;
    private PlacerChallengingArea player2PlacerChallengingArea;

    public PlacerChallengeAreas(PlacerChallengingArea player1PlacerChallengingArea,
                                PlacerChallengingArea player2PlacerChallengingArea) {
        this.player1PlacerChallengingArea = player1PlacerChallengingArea;
        this.player2PlacerChallengingArea = player2PlacerChallengingArea;
    }

    public GamePlayer higherPlayer() {
        GamePlayer higherPlayer;

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

        } else { // equal case
            return null;
        }

        return higherPlayer;
    }
}
