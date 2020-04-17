package com.dgrissom.seize;

import com.dgrissom.seize.packet.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ConnectionThread extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ConnectionThread(Socket socket, String name) {
        super(name);
        this.socket = socket;
        this.out = null;
        this.in = null;
    }

    public Socket getSocket() {
        return this.socket;
    }
    public PrintWriter getOut() {
        return this.out;
    }
    public BufferedReader getIn() {
        return this.in;
    }

    public void sendPacket(Packet packet) {
        Packet.send(packet, this.out);
    }
    public <T extends Packet> T nextPacket() throws IOException {
        return Packet.nextPacket(this.in);
    }

    public abstract void beginRun() throws IOException;

    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        ) {
            this.out = out;
            this.in = in;
            beginRun();
            this.socket.close();
        } catch (IOException e) {
            //todo SocketException could mean client disconnected forcefully or server disconnected forcefully
            e.printStackTrace();
        }
    }
}
