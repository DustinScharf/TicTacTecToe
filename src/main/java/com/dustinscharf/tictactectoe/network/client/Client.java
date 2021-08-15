package com.dustinscharf.tictactectoe.network.client;

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

    public Client() {
        try {
            this.socket = new Socket("localhost", Server.STANDARD_PORT);

            this.dataInputStream = new DataInputStream(this.socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Could not init client");
            System.exit(1);
        }

        this.startClientLoop();
    }

    private void startClientLoop() {
        new Thread(this::sendMessage).start();
        new Thread(this::receiveMessage).start();
    }

    private void sendMessage() {
        while (true) {
            this.clientText = JOptionPane.showInputDialog("Message");
            try {
                this.dataOutputStream.writeUTF(this.clientText);
                this.dataOutputStream.flush();
                System.out.println("OUT: " + this.clientText);
            } catch (IOException e) {
                System.err.println("Message could not send error");
            }
        }
    }

    private void receiveMessage() {
        while (true) {
            try {
                this.serverText = this.dataInputStream.readUTF();
                System.out.println("IN: " + this.serverText);
            } catch (IOException e) {
                System.err.println("Message not received error");
            }
        }
    }
}
