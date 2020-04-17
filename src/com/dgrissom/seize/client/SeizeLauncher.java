package com.dgrissom.seize.client;

import com.dgrissom.seize.Version;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SeizeLauncher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Seize.class.getResource("/res/fxml/launcher.fxml"));
        primaryStage.setTitle("Seize Launcher " + Version.FRIENDLY_VERSION);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static LauncherController open(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Seize.class.getResource("/res/fxml/launcher.fxml"));
            Parent root = loader.load();
            stage.setTitle("Seize " + Version.FRIENDLY_VERSION);
            stage.setScene(new Scene(root));
            stage.show();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
