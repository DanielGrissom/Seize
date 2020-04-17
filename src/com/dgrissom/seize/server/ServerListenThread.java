package com.dgrissom.seize.server;

import com.dgrissom.seize.ConnectionThread;
import com.dgrissom.seize.Version;
import com.dgrissom.seize.packet.*;

import java.io.IOException;
import java.net.Socket;

class ServerListenThread extends ConnectionThread {
    private final ServerConnection connection;
    private boolean listening;

    ServerListenThread(ServerConnection connection, Socket socket) {
        super(socket, "ServerListenThread");
        this.connection = connection;
        this.listening = true;
    }

    void disconnect() {
        this.listening = false;
    }

    private void handle(Packet packet) {
        switch (packet.getType()) {
            case CLIENT_DISCONNECT:
                // they disconnected! stop listening
                this.listening = false;
                this.connection.disconnect();
//                disconnect();
                //todo do other stuff like set them offline
                break;
            case CLIENT_LOGIN:
                PacketClientLogin clientLogin = (PacketClientLogin) packet;
                System.out.println("Client is logging in as " + clientLogin.getUsername());
                this.connection.loginAs(clientLogin.getUsername());
                //todo do other stuff like set them online
                break;
            case CLIENT_VERSION:
                PacketClientVersion clientVersion = (PacketClientVersion) packet;
                System.out.println("Client is on version " + clientVersion.getClientVersion());

                // send them the server version, then the game info
                Packet serverVersion = new PacketServerVersion(Version.VERSION);
                sendPacket(serverVersion);

                // not the same version as us? get out of here!
                if (!clientVersion.getClientVersion().equals(Version.VERSION))
                    disconnect();
                else
                    //todo
//                    sendGameInfo();
                break;
        }
    }

    @Override
    public void beginRun() throws IOException {
        String inputLine;
        while (this.listening && (inputLine = getIn().readLine()) != null) {
            Packet packet = Packet.receive(inputLine);
            if (packet != null)
                handle(packet);
        }
    }
}
