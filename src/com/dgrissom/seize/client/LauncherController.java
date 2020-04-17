package com.dgrissom.seize.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class LauncherController implements Initializable {
    @FXML
    private Label statusLabel;
    @FXML
    private TextField ipField, portField;
    @FXML
    private Button connectButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //todo we could just loop through the container's children maybe
    private void disableFields() {
        this.connectButton.setDisable(true);
        this.ipField.setDisable(true);
        this.portField.setDisable(true);
    }
    private void enableFields() {
        this.connectButton.setDisable(false);
        this.ipField.setDisable(false);
        this.portField.setDisable(false);
    }

    @FXML
    private void connect() {
        this.statusLabel.setText("Connecting...");
        disableFields();

        // connect in new thread
        new Thread(() -> {
            AtomicReference<String> status = new AtomicReference<>();
            AtomicReference<ConnectResult> result = new AtomicReference<>(null);
            try {
                int port = Integer.parseInt(this.portField.getText());
                if (port < 0 || port > 65535)
                    status.set("Invalid Port!");
                else {
                    status.set("");
                    ConnectResult cr = Seize.connect(this.ipField.getText(), port);
                    result.set(cr);
                    status.set(cr.getStatusText());
                }
            } catch (NumberFormatException e) {
                status.set("Invalid Port!");
            }

            Platform.runLater(() -> {
                this.statusLabel.setText(status.get());
                enableFields();

                // if we were successful, leave fields disabled. in ClientConnectionThread
                // we will open the ClientGameLogin when we receive the PacketServerGameInfo
            });
        }, "ConnectThread").start();
    }
}
