package com.dustinscharf.tictactectoe;

import javafx.scene.text.Text;

public class PlacerChallengeArea {
    private Text player1ChallengedPlacerText;
    private Text player2ChallengedPlacerText;

    private Placer player1ChallengedPlacer;
    private Placer player2ChallengedPlacer;

    public PlacerChallengeArea(Text player1ChallengedPlacerText, Text player2ChallengedPlacerText) {
        this.player1ChallengedPlacerText = player1ChallengedPlacerText;
        this.player2ChallengedPlacerText = player2ChallengedPlacerText;
    }

    public void setPlayer1ChallengedPlacer(Placer player1ChallengedPlacer) {
        this.player1ChallengedPlacer = player1ChallengedPlacer;
    }

    public void setPlayer2ChallengedPlacer(Placer player2ChallengedPlacer) {
        this.player2ChallengedPlacer = player2ChallengedPlacer;
    }

    public GamePlayer higherPlayer() {
        GamePlayer higherPlayer;

        if (this.player1ChallengedPlacer.getValue() > this.player2ChallengedPlacer.getValue()) {
            higherPlayer = this.player1ChallengedPlacer.getOwner();
        } else if (this.player1ChallengedPlacer.getValue() < this.player2ChallengedPlacer.getValue()) {
            higherPlayer = this.player2ChallengedPlacer.getOwner();
        } else {
            return null;
        }

        return higherPlayer;
    }
}
