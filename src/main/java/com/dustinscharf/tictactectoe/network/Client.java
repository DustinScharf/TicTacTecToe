package com.dustinscharf.tictactectoe.network;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 3241);

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String clientText = "";
//        String serverText;

        while (!clientText.equals("STOP")) {
            System.out.print("OUT: ");
            clientText = bufferedReader.readLine();
            dataOutputStream.writeUTF(clientText);
            dataOutputStream.flush();

//            serverText = dataInputStream.readUTF();
//            System.out.println("IN: " + serverText);
        }
        bufferedReader.close();

        dataOutputStream.close();
        socket.close();
    }
}
