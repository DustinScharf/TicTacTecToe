package com.dustinscharf.tictactectoe.network.client;

import com.dustinscharf.tictactectoe.game.Field;
import com.dustinscharf.tictactectoe.game.Game;
import com.dustinscharf.tictactectoe.network.server.Server;
import javafx.concurrent.Task;
import javafx.stage.Stage;

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

    private boolean stayAlive;

    private boolean isConnectedToAnotherPlayer;

    private Stage closeCheckingStage;

    public Client(Game controlledGame, String host) {
        this.stayAlive = true;

        this.controlledGame = controlledGame;

        int connectionTries = 0;
        boolean connected;
        do {
            ++connectionTries;
            try {
                System.out.println("Try to connect to " + host + ":" + Server.STANDARD_PORT);
                this.socket = new Socket(host, Server.STANDARD_PORT);
                connected = true;
            } catch (IOException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.err.println("THREAD ERROR: COULD NOT SLEEP/WAIT");
                }
                connected = false;
                if (connectionTries > 5) {
                    JOptionPane.showMessageDialog(null,
                            "Could not connect to " + host + ", exit...",
                            "TicTacTecToe | Network Error",
                            JOptionPane.ERROR_MESSAGE);
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

        this.isConnectedToAnotherPlayer = false;

        this.startClientLoop();
    }

    public boolean isConnectedToAnotherPlayer() {
        return isConnectedToAnotherPlayer;
    }

    private void startClientLoop() {
        Runnable receiveMessageRunnable = this::receiveMessage;
        Thread receiveMessageThread = new Thread(receiveMessageRunnable);
        receiveMessageThread.start();

        this.sendMessage("S");

        Runnable closeOnConnectionLossRunnable = this::closeOnConnectionLoss;
        Thread closeOnConnectionLossThread = new Thread(closeOnConnectionLossRunnable);
        closeOnConnectionLossThread.start();
    }

    private void closeOnConnectionLoss() {
        while (this.stayAlive) {
            this.stayAlive = !this.socket.isClosed() && this.controlledGame.getCloseCheckingStage().isShowing();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.err.println("A client thread could not sleep");
            }
        }

        System.out.println("Closing client...");
        this.close();
        System.out.println("Client closed");
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
        while (this.stayAlive) {
            try {
                this.serverText = this.dataInputStream.readUTF();
                System.out.println("IN: " + this.serverText);
                if (this.serverText.charAt(0) == 'F') {
                    int clickedRow = Character.getNumericValue(this.serverText.charAt(1));
                    int clickedCol = Character.getNumericValue(this.serverText.charAt(2));
                    this.receiveClickedFieldByCords(clickedRow, clickedCol);
                } else if (this.serverText.charAt(0) == 'P') {
                    int placerValue;
                    if (this.serverText.length() == 2) {
                        placerValue = Character.getNumericValue(this.serverText.charAt(1));
                    } else {
                        placerValue = Integer.parseInt(this.serverText.substring(1, 3));
                    }
                    this.receiveClickedPlacerByValue(placerValue);
                } else if (this.serverText.charAt(0) == 'S') {
                    this.isConnectedToAnotherPlayer = true;
                } else if (this.serverText.charAt(0) == 'C') {
                    this.close();
                }
            } catch (IOException e) {
                System.out.println("Server not reachable, exit...");
                try {
                    this.socket.close();
                    this.dataInputStream.close();
                    this.dataOutputStream.close();
                } catch (IOException ex) {
                    System.err.println("ERROR: COULD NOT CLOSE CONNECTION");
                }
                this.stayAlive = false;
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
                ).getPlacers().getPlacers()[value - 1]
        );
    }

    private void close() {
        if (this.socket.isClosed()) {
            return;
        }

        this.sendMessage("C");

        this.controlledGame.sendMessageToScreen("Connection closed, exit...", 8);

        this.stayAlive = false;
        this.isConnectedToAnotherPlayer = false;
        try {
            this.socket.close();
            this.dataInputStream.close();
            this.dataOutputStream.close();
        } catch (IOException e) {
            System.err.println("COULD NOT CLOSE CLIENT");
        }

        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
