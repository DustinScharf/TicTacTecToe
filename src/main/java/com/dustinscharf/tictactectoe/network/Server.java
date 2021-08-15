package com.dustinscharf.tictactectoe.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3241);

        Socket socket = serverSocket.accept();

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String string1 = "";
        String string2;

        while (!string1.equals("STOP")) {
            string1 = dataInputStream.readUTF();
            System.out.println("CLIENT: " + string1);

            string2 = bufferedReader.readLine();
            dataOutputStream.writeUTF(string2);
            dataOutputStream.flush();
        }
        bufferedReader.close();

        dataInputStream.close();
        socket.close();
        serverSocket.close();
    }
}
