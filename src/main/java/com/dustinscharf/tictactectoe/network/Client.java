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

    public Client() throws IOException {
        this.socket = new Socket("localhost", Server.STANDARD_PORT);

        this.dataInputStream = new DataInputStream(this.socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());

        this.startClientLoop();
    }

    private void startClientLoop() throws IOException {
        this.sendMessage();
        this.receiveMessage();
    }

    private void sendMessage() throws IOException {
        this.clientText = JOptionPane.showInputDialog("Message");
        this.dataOutputStream.writeUTF(this.clientText);
        this.dataOutputStream.flush();
    }

    private void receiveMessage() throws IOException {
        this.serverText = this.dataInputStream.readUTF();
        JOptionPane.showMessageDialog(null, this.serverText);
    }
}
