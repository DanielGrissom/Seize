package com.dgrissom.seize.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable {
    @FXML
    private AnchorPane container;
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // resize canvas with window
        this.canvas.widthProperty().bind(this.container.widthProperty());
        this.canvas.heightProperty().bind(this.container.heightProperty());
        // allow keyboard input
        this.canvas.setFocusTraversable(true);
    }
}
