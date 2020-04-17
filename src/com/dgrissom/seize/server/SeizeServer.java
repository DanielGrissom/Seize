package com.dgrissom.seize.server;

import com.dgrissom.seize.Version;
import com.dgrissom.seize.packet.PacketServerClose;
import com.dgrissom.seize.packet.PacketServerMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

// server application
public class SeizeServer {
    public static final int DEFAULT_PORT = 30000;
    private static Set<ServerConnection> connections = new HashSet<>();
    private static ServerSettings settings = null;
    private static Thread clientAcceptThread = null;
    private static boolean listening = true;

    private SeizeServer() {}

    private static void connect(ServerConnection connection) {
        connections.add(connection);
        System.out.println("Client connected (" + connections.size() + ")");
    }
    static void disconnect(ServerConnection connection) {
        connections.remove(connection);
        System.out.println("Client disconnected (" + connections.size() + ")");
    }

    private static void loadSettings() {
        settings = ServerSettings.load();
        System.out.println("Loaded server settings.");
    }
    private static void start() {
        System.out.println("Starting server...");
        // thread to accept client connections
        clientAcceptThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(settings.getPort())) {
                System.out.println("Started server on port " + settings.getPort());
                while (listening) {
                    ServerConnection connection = new ServerConnection(serverSocket.accept());
                    connection.start();
                    connect(connection);
                }
            } catch (IOException e) {
                System.err.println("Could not listen on port " + settings.getPort());
            }
        }, "ClientAcceptThread");
        clientAcceptThread.start();
    }

    private static void quit() {
        listening = false;
        for (ServerConnection connection : connections) {
            connection.sendPacket(new PacketServerClose());
            connection.disconnect();
        }
        // clientAcceptThread will still be running waiting for a connection (serverSocket.accept()),
        // so we have to forcefully exit.
        System.exit(0);
    }

    public static void main(String[] args) {
        System.out.println("Seize Server " + Version.FRIENDLY_VERSION);

        loadSettings();
        start();

        // terminal input
        Scanner sc = new Scanner(System.in);
        while (listening) {
            System.out.print(">> ");
            String in = sc.nextLine();
            if (in.equals("quit"))
                quit();
            else {
                // send message to clients
                for (ServerConnection connection : connections)
                    connection.sendPacket(new PacketServerMessage(in));
            }
        }
    }
}
