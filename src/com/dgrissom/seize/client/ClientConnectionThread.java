package com.dgrissom.seize.client;

import com.dgrissom.seize.ConnectionThread;
import com.dgrissom.seize.Version;
import com.dgrissom.seize.packet.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.net.Socket;

class ClientConnectionThread extends ConnectionThread {
    private boolean connected;

    ClientConnectionThread(Socket socket) {
        super(socket, "ClientThread");
        this.connected = true;
    }

    public boolean isConnected() {
        return this.connected;
    }
    void disconnect() {
        sendPacket(new PacketClientDisconnect());
        this.connected = false;
        Platform.exit();
    }

    // returns false if versions are different
    private boolean connectionHandshake() throws IOException {
        // send client version packet
        Packet clientVersion = new PacketClientVersion(Version.VERSION);
        sendPacket(clientVersion);

        // receive server version packet
        PacketServerVersion serverVersion = nextPacket();
        if (serverVersion == null)
            return false;

        System.out.println("Server version: " + serverVersion.getServerVersion());

        return serverVersion.getServerVersion().equals(Version.VERSION);
    }

    @Override
    public void beginRun() throws IOException {
        if (!connectionHandshake()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Server's version is not the same as ours!", ButtonType.OK);
                alert.show();
            });
            return;
        }

        Packet received;
        while (this.connected && (received = nextPacket()) != null) {
            switch (received.getType()) {
                case SERVER_CLOSE:
                    this.connected = false;
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The server has been closed.", ButtonType.OK);
                        alert.showAndWait();
                        // just exit.
                        //todo bring launcher back up
                        Seize.getApplication().closePrimaryStage();
                    });
                    break;
                case SERVER_MESSAGE:
                    PacketServerMessage message = (PacketServerMessage) received;
                    System.out.println("[Server] " + message.getMessage());
                    break;
            }
        }
    }
}
