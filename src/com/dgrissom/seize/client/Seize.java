package com.dgrissom.seize.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

// client application
public class Seize {
    private static SeizeApplication application = null;
    private static ClientConnectionThread connectionThread = null;

    public static void main(String[] args) {
        // open the launcher
        SeizeApplication.launch();
    }

    public static SeizeApplication getApplication() {
        return application;
    }
    public static void setApplication(SeizeApplication application) {
        Seize.application = application;
    }
    public static ClientConnectionThread getConnectionThread() {
        return connectionThread;
    }

    // returns status text
    public static ConnectResult connect(String hostName, int port) {
        try {
            Socket socket = new Socket(hostName, port);
            connectionThread = new ClientConnectionThread(socket);
            connectionThread.start();
            return ConnectResult.SUCCESS;
        } catch (UnknownHostException e) {
            return ConnectResult.UNKNOWN_HOST;
        } catch (IOException e) {
            return ConnectResult.FAILED;
        }
    }

    public static void disconnect() {
        connectionThread.disconnect();
        connectionThread = null;
    }
}
