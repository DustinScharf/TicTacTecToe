package com.dustinscharf.tictactectoe.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {
    private static final int STANDARD_PORT = 1338;

    // SERVER
    private ServerSocket serverSocket;

    // PLAYER 1
    private Socket socketPlayer1;

    private DataInputStream dataInputStreamPlayer1;
    private DataOutputStream dataOutputStreamPlayer1;

    private String textPlayer1 = "";

    // PLAYER 2
    private Socket socketPlayer2;

    private DataInputStream dataInputStreamPlayer2;
    private DataOutputStream dataOutputStreamPlayer2;

    private String textPlayer2 = "";

    public Server() throws IOException {
        System.out.println("Starting server...");

        this.serverSocket = new ServerSocket(STANDARD_PORT);

        System.out.println("Server started");

        System.out.println("Establishing clients...");

        this.socketPlayer1 = this.serverSocket.accept();
        this.dataInputStreamPlayer1 = new DataInputStream(this.socketPlayer1.getInputStream());
        this.dataOutputStreamPlayer1 = new DataOutputStream(this.socketPlayer1.getOutputStream());

        this.socketPlayer2 = this.serverSocket.accept();
        this.dataInputStreamPlayer2 = new DataInputStream(this.socketPlayer2.getInputStream());
        this.dataOutputStreamPlayer2 = new DataOutputStream(this.socketPlayer2.getOutputStream());

        System.out.println("Clients established");

        System.out.println("Server loop...");
        this.startServerLoop();
    }

    private void startServerLoop() throws IOException {
        this.receiveFromPlayer1();
        this.forwardToPlayer2();
    }

    private void receiveFromPlayer1() throws IOException {
        this.textPlayer1 = this.dataInputStreamPlayer1.readUTF();
        System.out.println("IN: " + this.textPlayer1);
    }

    private void forwardToPlayer2() throws IOException {
        if (Objects.isNull(this.textPlayer1)) {
            return;
        }

        System.out.println("OUT: " + this.textPlayer1);
        this.dataOutputStreamPlayer2.writeUTF(this.textPlayer1);
        this.dataOutputStreamPlayer2.flush();

        this.textPlayer1 = null;
    }

    private void receiveFromPlayer2() throws IOException {
        this.textPlayer2 = this.dataInputStreamPlayer2.readUTF();
        System.out.println("IN: " + this.textPlayer2);
    }

    private void forwardToPlayer1() throws IOException {
        if (Objects.isNull(this.textPlayer2)) {
            return;
        }

        System.out.println("OUT: " + this.textPlayer2);
        this.dataOutputStreamPlayer1.writeUTF(this.textPlayer2);
        this.dataOutputStreamPlayer1.flush();

        this.textPlayer2 = null;
    }
}
