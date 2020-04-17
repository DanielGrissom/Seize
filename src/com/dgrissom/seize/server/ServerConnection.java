package com.dgrissom.seize.server;

import com.dgrissom.seize.packet.Packet;

import java.net.Socket;
import java.util.UUID;

// represents a server's connection to a client
class ServerConnection {
    private final UUID id;
    private ServerListenThread listenThread;
    // who are they logged in as?
    private String username;

    public ServerConnection(Socket socket) {
        this.id = UUID.randomUUID();
        this.listenThread = new ServerListenThread(this, socket);
        this.username = null;
    }

    public UUID getId() {
        return this.id;
    }
    public ServerListenThread getListenThread() {
        return this.listenThread;
    }
    public void sendPacket(Packet packet) {
        this.listenThread.sendPacket(packet);
    }
    public String getUsername() {
        return this.username;
    }
    public void loginAs(String username) {
        this.username = username;
    }

    public void start() {
        this.listenThread.start();
    }
    public void disconnect() {
        this.listenThread.disconnect();
        SeizeServer.disconnect(this);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
