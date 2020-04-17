package com.dgrissom.seize.client;

import com.dgrissom.seize.Version;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SeizeLauncher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Seize.class.getResource("/res/launcher.fxml"));
        primaryStage.setTitle("Seize Launcher " + Version.FRIENDLY_VERSION);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    static void launch() {
        Application.launch();
    }
}
