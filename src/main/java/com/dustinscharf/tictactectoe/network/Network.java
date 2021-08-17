package com.dustinscharf.tictactectoe.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Network {
    public static String getMyIP() {
        URL whatIsMyIPUrl = null;
        try {
            whatIsMyIPUrl = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            System.err.println("Could not get IP from checkip.amazonaws.com");
            return null;
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(whatIsMyIPUrl.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ip = null;
        try {
            ip = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ip;
    }
}
