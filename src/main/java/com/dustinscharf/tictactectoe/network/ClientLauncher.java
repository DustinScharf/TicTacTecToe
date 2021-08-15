package com.dustinscharf.tictactectoe.network;

import java.io.IOException;

public class ClientLauncher {
    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException ioException) {
            System.err.println("Could not init client");
            System.exit(1);
        }
    }
}