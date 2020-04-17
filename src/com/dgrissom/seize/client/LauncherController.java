package com.dgrissom.seize.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {
    @FXML
    private TextField ipField, portField;
    @FXML
    private Button connectButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void connect() {
        System.out.println("Test");
    }
}
