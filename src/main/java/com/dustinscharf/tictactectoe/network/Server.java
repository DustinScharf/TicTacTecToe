package com.dustinscharf.tictactectoe.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // SERVER INIT
    private static ServerSocket serverSocket;

    static {
        try {
            serverSocket = new ServerSocket(3241);
        } catch (IOException e) {
            System.err.println("Server error (not creatable)");
            System.exit(1);
        }
    }

    // PLAYER 1 INIT
    private static Socket socketPlayer1;

    private static DataInputStream dataInputStreamPlayer1;
    private static DataOutputStream dataOutputStreamPlayer1;

    private static String textPlayer1 = "";

    // PLAYER 2 INIT
    private static Socket socketPlayer2;

    private static DataInputStream dataInputStreamPlayer2;
    private static DataOutputStream dataOutputStreamPlayer2;

    private static String textPlayer2 = "";

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting Server...");

        // PLAYER 1 CONNECTION BUILD
        socketPlayer1 = serverSocket.accept();
        dataInputStreamPlayer1 = new DataInputStream(socketPlayer1.getInputStream());
        dataOutputStreamPlayer1 = new DataOutputStream(socketPlayer1.getOutputStream());

        // PLAYER 2 CONNECTION BUILD
        socketPlayer2 = serverSocket.accept();
        dataInputStreamPlayer2 = new DataInputStream(socketPlayer2.getInputStream());
        dataOutputStreamPlayer2 = new DataOutputStream(socketPlayer2.getOutputStream());

        System.out.println("Clients established");

        // PLAYER 1 RECEIVING
        Runnable runnable1 = () -> {
            try {
                while (!textPlayer1.equals("STOP")) {
                    receiveAndForwardPlayer1();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                try {
                    dataInputStreamPlayer1.close();
                    socketPlayer1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        thread1.start();

        // PLAYER 2 RECEIVING
        Runnable runnable2 = () -> {
            try {
                while (!textPlayer2.equals("STOP")) {
                    receiveAndForwardPlayer2();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                try {
                    dataInputStreamPlayer2.close();
                    socketPlayer2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread2 = new Thread(runnable2);
        thread2.start();

        // SERVER CLOSE WAIT
        while (thread1.isAlive() && thread2.isAlive()) {
            Thread.sleep(1000);
        }

        // SERVER CLOSE
        serverSocket.close();
    }

    public static void receiveAndForwardPlayer1() throws IOException {
        textPlayer1 = dataInputStreamPlayer1.readUTF();
        System.out.println("INOUT: " + textPlayer1);
        dataOutputStreamPlayer2.writeUTF(textPlayer1);
        dataOutputStreamPlayer2.flush();
    }

    public static void receiveAndForwardPlayer2() throws IOException {
        textPlayer2 = dataInputStreamPlayer2.readUTF();
        System.out.println("INOUT: " + textPlayer2);
        dataOutputStreamPlayer1.writeUTF(textPlayer2);
        dataOutputStreamPlayer1.flush();
    }
}
