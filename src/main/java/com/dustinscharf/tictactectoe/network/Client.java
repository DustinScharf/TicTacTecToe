package com.dustinscharf.tictactectoe.network;

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
        this.sendMessage();
        this.receiveMessage();
    }

    private void sendMessage() {
        this.clientText = JOptionPane.showInputDialog("Message");
        try {
            this.dataOutputStream.writeUTF(this.clientText);
            this.dataOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Message could not send error");
        }
    }

    private void receiveMessage() {
        try {
            this.serverText = this.dataInputStream.readUTF();
        } catch (IOException e) {
            System.err.println("Message not received error");
        }
        JOptionPane.showMessageDialog(null, this.serverText);
    }
}
