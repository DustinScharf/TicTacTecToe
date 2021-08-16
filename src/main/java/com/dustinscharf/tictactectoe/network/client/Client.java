package com.dustinscharf.tictactectoe.network.client;

import com.dustinscharf.tictactectoe.game.Field;
import com.dustinscharf.tictactectoe.game.Game;
import com.dustinscharf.tictactectoe.network.server.Server;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private String clientText;
    private String serverText;

    private Game controlledGame;

    public Client(Game controlledGame) {
        this.controlledGame = controlledGame;

        int connectionTries = 0;
        boolean connected;
        do {
            ++connectionTries;
            try {
                this.socket = new Socket("localhost"/*TODO*/, Server.STANDARD_PORT);
                connected = true;
            } catch (IOException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.err.println("THREAD ERROR: COULD NOT SLEEP/WAIT");
                }
                connected = false;
                if (connectionTries > 100) {
                    System.err.println("NETWORK ERROR: CLIENT COULD NOT CONNECT TO SERVER DUE TO TIMEOUT");
                    System.exit(1);
                }
            }
        } while (!connected);

        try {
            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("NETWORK ERROR: CLIENT COULD NOT CONNECT TO SERVER");
            System.exit(1);
        }

        this.startClientLoop();
    }

    private void startClientLoop() {
//        new Thread(this::sendMessage).start();
        new Thread(this::receiveMessage).start();
    }

    private void sendMessage(String message) {
        this.clientText = message;
        try {
            this.dataOutputStream.writeUTF(this.clientText);
            this.dataOutputStream.flush();
            System.out.println("OUT: " + this.clientText);
        } catch (IOException e) {
            System.err.println("NETWORK ERROR: CLIENT MESSAGE NOT SEND RIGHT");
        }
    }

    private void receiveMessage() {
        while (true) {
            try {
                this.serverText = this.dataInputStream.readUTF();
                System.out.println("IN: " + this.serverText);
                if (this.serverText.charAt(0) == 'F') {
                    int clickedRow = Character.getNumericValue(this.serverText.charAt(1));
                    int clickedCol = Character.getNumericValue(this.serverText.charAt(2));
                    this.receiveClickedFieldByCords(clickedRow, clickedCol);
                }
            } catch (IOException e) {
                System.err.println("NETWORK ERROR: MESSAGE NOT RECEIVED RIGHT BY CLIENT");
            }
        }
    }

    public void sendClickedFieldByCords(int row, int col) {
        this.sendMessage("F" + row + "" + col);
    }

    public void receiveClickedFieldByCords(int row, int col) {
        Field clickedField = this.controlledGame.getBoard().getFields()[row][col];
        this.controlledGame.receiveBoardClick(clickedField);
    }

    public void sendClickedPlacerByValue(int value) {
        this.sendMessage("P" + value);
    }

    public void receiveClickedPlacerByValue(int value) {
        this.controlledGame.receivePlacerClick(
                this.controlledGame.getOppositePlayer(
                        this.controlledGame.getOnlinePlayer()
                ).getPlacers().getPlacers()[value]
        );
    }
}
