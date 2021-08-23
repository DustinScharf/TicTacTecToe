package com.dustinscharf.tictactectoe.network.server;

import com.dustinscharf.tictactectoe.network.Network;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.TransferQueue;

public class Server {
    public static final int STANDARD_PORT = 1338;

    // SERVER
    private ServerSocket serverSocket;
    private boolean stayAlive;

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

    private Stage closeCheckingStage;

    private Thread closeCheckThread; // has kill

    private Thread receiveFromPlayer1Thread; // has kill

    private Thread forwardToPlayer2Thread; // has kill

    private Thread receiveFromPlayer2Thread; // has kill

    private Thread forwardToPlayer1Thread; // has kill

    public void closeCheck() {
        while (this.stayAlive) {
            this.stayAlive = this.closeCheckingStage.isShowing();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.close();
    }

    private void close() {
        System.out.println("Closing server...");
        try {
            this.sendMessageToPlayer1("C");
            this.serverSocket.close();
            if (Objects.nonNull(this.socketPlayer1)) {
                this.socketPlayer1.close();
                this.dataInputStreamPlayer1.close();
                this.dataOutputStreamPlayer1.close();
            }
            if (Objects.nonNull(this.socketPlayer2)) {
                this.sendMessageToPlayer2("C");
                this.socketPlayer2.close();
                this.dataInputStreamPlayer2.close();
                this.dataOutputStreamPlayer2.close();
            }
        } catch (IOException e) {
            System.err.println("COULD NOT CLOSE SERVER");
        } finally {
            System.out.println("Closed server");
            this.stayAlive = false;
        }
    }

    public Server(Stage closeCheckingStage) {
        this.stayAlive = true;
        this.closeCheckingStage = closeCheckingStage;
    }

    public void start() {
        Runnable closeCheckRunnable = this::closeCheck;
        closeCheckThread = new Thread(closeCheckRunnable);
        closeCheckThread.start();

        System.out.println("Starting server...");

        try {
            this.serverSocket = new ServerSocket(STANDARD_PORT);
            System.out.println("INTERNAL IP: " + Network.getMyIP());
//            System.out.println("EXTERNAL IP (ignored): " + Network.getMyIP());
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

            System.out.println("Client 1 established");
        } catch (IOException e) {
            System.err.println("Could not establish client 1");
        }

        try {
            this.socketPlayer2 = this.serverSocket.accept();
            this.dataInputStreamPlayer2 = new DataInputStream(this.socketPlayer2.getInputStream());
            this.dataOutputStreamPlayer2 = new DataOutputStream(this.socketPlayer2.getOutputStream());

            System.out.println("Client 2 established");

            System.out.println("Both clients established");

            System.out.println("Server loop...");
            this.startServerLoop();
        } catch (IOException e) {
            System.out.println("Could not establish client 2, program exit?");
        }
    }

    private void startServerLoop() {
        Runnable receiveFromPlayer1Runnable = this::receiveFromPlayer1;
        receiveFromPlayer1Thread = new Thread(receiveFromPlayer1Runnable);
        receiveFromPlayer1Thread.start();

        Runnable forwardToPlayer2Runnable = this::forwardToPlayer2;
        forwardToPlayer2Thread = new Thread(forwardToPlayer2Runnable);
        forwardToPlayer2Thread.start();

        ////////////////////////////////////////////////////////////////////////////

        Runnable receiveFromPlayer2Runnable = this::receiveFromPlayer2;
        receiveFromPlayer2Thread = new Thread(receiveFromPlayer2Runnable);
        receiveFromPlayer2Thread.start();

        Runnable forwardToPlayer1Runnable = this::forwardToPlayer1;
        forwardToPlayer1Thread = new Thread(forwardToPlayer1Runnable);
        forwardToPlayer1Thread.start();
    }

    private void receiveFromPlayer1() {
        while (this.stayAlive) {
            try {
                this.textPlayer1 = this.dataInputStreamPlayer1.readUTF();
            } catch (IOException e) {
                System.err.println("Message receive error from player 1");
            }
            System.out.println("IN: " + this.textPlayer1);
        }
    }

    private void forwardToPlayer2() {
        while (this.stayAlive) {
            if (Objects.isNull(this.textPlayer1)) {
                try {
                    Thread.sleep(25);
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
        while (this.stayAlive) {
            try {
                this.textPlayer2 = this.dataInputStreamPlayer2.readUTF();
            } catch (IOException e) {
                System.err.println("Message receive error from player 2");
            }
            System.out.println("IN: " + this.textPlayer2);
        }
    }

    private void forwardToPlayer1() {
        while (this.stayAlive) {
            if (Objects.isNull(this.textPlayer2)) {
                try {
                    Thread.sleep(25);
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

    private void sendMessageToPlayer1(String message) {
        try {
            if (Objects.nonNull(this.dataOutputStreamPlayer1)) {
                this.dataOutputStreamPlayer1.writeUTF(message);
                this.dataOutputStreamPlayer1.flush();
            }
        } catch (IOException e) {
            System.err.println("Server could not message player 1");
        }
    }

    private void sendMessageToPlayer2(String message) {
        try {
            this.dataOutputStreamPlayer2.writeUTF(message);
            this.dataOutputStreamPlayer2.flush();
        } catch (IOException e) {
            System.err.println("Server could not message player 2");
        }
    }

    public void kill() {
        this.close();
        this.closeCheckThread.stop();

        if (Objects.nonNull(this.receiveFromPlayer1Thread) && this.receiveFromPlayer1Thread.isAlive()) {
            this.receiveFromPlayer1Thread.stop();
        }
        if (Objects.nonNull(this.forwardToPlayer2Thread) && this.forwardToPlayer2Thread.isAlive()) {
            this.forwardToPlayer2Thread.stop();
        }

        if (Objects.nonNull(this.receiveFromPlayer2Thread) && this.receiveFromPlayer2Thread.isAlive()) {
            this.receiveFromPlayer2Thread.stop();
        }
        if (Objects.nonNull(this.forwardToPlayer1Thread) && this.forwardToPlayer1Thread.isAlive()) {
            this.forwardToPlayer1Thread.stop();
        }



    }
}
