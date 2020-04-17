package com.dgrissom.seize.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class SeizeApplication extends Application {
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
    public void closePrimaryStage() {
        this.primaryStage.close();
    }
    public void openLauncher() {
        SeizeLauncher.open(this.primaryStage);
    }

    @Override
    public void start(Stage primaryStage) {
        Seize.setApplication(this);
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest((evt) -> {
            if (Seize.getConnectionThread() != null)
                Seize.disconnect();
        });
        openLauncher();
    }

    public static void launch() {
        Application.launch();
    }
}
