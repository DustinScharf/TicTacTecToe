package com.dustinscharf.tictactectoe.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Network {
    public static String getMyIP() {
//        // TODO EXTERNAL IP
//        URL whatIsMyIPUrl;
//        try {
//            whatIsMyIPUrl = new URL("http://checkip.amazonaws.com");
//        } catch (MalformedURLException e) {
//            System.err.println("Could not access IP service from checkip.amazonaws.com");
//            return null;
//        }
//
//        BufferedReader bufferedReader;
//        try {
//            bufferedReader = new BufferedReader(new InputStreamReader(whatIsMyIPUrl.openStream()));
//        } catch (IOException e) {
//            System.err.println("Could not get IP reader from checkip.amazonaws.com");
//            return null;
//        }
//
//        String ip;
//        try {
//            ip = bufferedReader.readLine();
//        } catch (IOException e) {
//            System.err.println("Could not get IP read from checkip.amazonaws.com");
//            return null;
//        }

        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Could not get local IP");
            return null;
        }

        return ip;
    }
}
