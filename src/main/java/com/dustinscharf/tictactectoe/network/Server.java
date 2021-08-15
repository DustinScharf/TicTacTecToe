package com.dustinscharf.tictactectoe.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3241);

        Socket socket = serverSocket.accept();

        DataInputStream dataInputStreamPlayer1 = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStreamPlayer1 = new DataOutputStream(socket.getOutputStream());

        String textPlayer1 = "";
        // String string2 = "";

        while (!textPlayer1.equals("STOP")) {
            textPlayer1 = dataInputStreamPlayer1.readUTF();
            System.out.println("IN: " + textPlayer1);

//            System.out.print("SERVER: ");
//            dataOutputStreamPlayer1.writeUTF(string2);
//            dataOutputStreamPlayer1.flush();
        }

        dataInputStreamPlayer1.close();
        socket.close();
        serverSocket.close();
    }
}
