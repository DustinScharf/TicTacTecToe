package com.dustinscharf.tictactectoe.network;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 3241);

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String string1 = "";
        String string2;

        while (!string1.equals("STOP")) {
            System.out.print("CLIENT: ");
            string1 = bufferedReader.readLine();
            dataOutputStream.writeUTF(string1);
            dataOutputStream.flush();

            string2 = dataInputStream.readUTF();
            System.out.println("SERVER: " + string2);
        }
        bufferedReader.close();

        dataOutputStream.close();
        socket.close();
    }
}
