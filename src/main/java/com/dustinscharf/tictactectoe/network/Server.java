package com.dustinscharf.tictactectoe.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.TransferQueue;

public class Server {
    public static final int STANDARD_PORT = 1338;

    // SERVER
    private ServerSocket serverSocket;

    // PLAYER 1
    private Socket socketPlayer1;

    private DataInputStream dataInputStreamPlayer1;
    private DataOutputStream dataOutputStreamPlayer1;

    private String textPlayer1;

    // PLAYER 2
    private Socket socketPlayer2;

    private DataInputStream dataInputStreamPlayer2;
    private DataOutputStream dataOutputStreamPlayer2;

    private String textPlayer2;

    public Server() {
        System.out.println("Starting server...");

        try {
            this.serverSocket = new ServerSocket(STANDARD_PORT);
        } catch (IOException e) {
            System.err.println("Could not start server");
            System.exit(1);
        }

        System.out.println("Server started");

        System.out.println("Establishing clients...");

        try {
            this.socketPlayer1 = this.serverSocket.accept();
            this.dataInputStreamPlayer1 = new DataInputStream(this.socketPlayer1.getInputStream());
            this.dataOutputStreamPlayer1 = new DataOutputStream(this.socketPlayer1.getOutputStream());
        } catch (IOException e) {
            System.err.println("Could not establish client 1");
            System.exit(1);
        }

        try {
            this.socketPlayer2 = this.serverSocket.accept();
            this.dataInputStreamPlayer2 = new DataInputStream(this.socketPlayer2.getInputStream());
            this.dataOutputStreamPlayer2 = new DataOutputStream(this.socketPlayer2.getOutputStream());
        } catch (IOException e) {
            System.err.println("Could not establish client 2");
            System.exit(1);
        }

        System.out.println("Clients established");

        System.out.println("Server loop...");
        this.startServerLoop();
    }

    private void startServerLoop() {
        new Thread(this::receiveFromPlayer1).start();
        new Thread(this::forwardToPlayer2).start();

        new Thread(this::receiveFromPlayer2).start();
        new Thread(this::forwardToPlayer1).start();
    }

    private void receiveFromPlayer1() {
        while (true) {
            try {
                this.textPlayer1 = this.dataInputStreamPlayer1.readUTF();
            } catch (IOException e) {
                System.err.println("Message receive error from player 1");
            }
            System.out.println("IN: " + this.textPlayer1);
        }
    }

    private void forwardToPlayer2() {
        while (true) {
            if (Objects.isNull(this.textPlayer1)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("Thread could not sleep");
                }
                continue;
            }

            System.out.println("OUT: " + this.textPlayer1);
            try {
                this.dataOutputStreamPlayer2.writeUTF(this.textPlayer1);
                this.dataOutputStreamPlayer2.flush();
            } catch (IOException e) {
                System.err.println("Message send error to player 2");
            }

            this.textPlayer1 = null;
        }
    }

    private void receiveFromPlayer2() {
        while (true) {
            try {
                this.textPlayer2 = this.dataInputStreamPlayer2.readUTF();
            } catch (IOException e) {
                System.err.println("Message receive error from player 2");
            }
            System.out.println("IN: " + this.textPlayer2);
        }
    }

    private void forwardToPlayer1() {
        while (true) {
            if (Objects.isNull(this.textPlayer2)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("Thread could not sleep");
                }
                continue;
            }

            System.out.println("OUT: " + this.textPlayer2);
            try {
                this.dataOutputStreamPlayer1.writeUTF(this.textPlayer2);
                this.dataOutputStreamPlayer1.flush();
            } catch (IOException e) {
                System.err.println("Message send error to player 1");
            }

            this.textPlayer2 = null;
        }
    }
}
