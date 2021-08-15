package com.dustinscharf.tictactectoe.network;

import java.io.IOException;

public class ServerLauncher {
    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException ioException) {
            System.err.println("Could not start server");
            System.exit(1);
        }
    }
}