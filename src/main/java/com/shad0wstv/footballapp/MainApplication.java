package com.shad0wstv.footballapp;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private static final int COUNT_LIMIT = 25000;

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", CustomPreloader.class.getCanonicalName());
        launch(args);
    }
    @Override
    public void init() {
        for (int i = 0; i < COUNT_LIMIT; i++) {
            int progress = (100 * i) / COUNT_LIMIT;
            notifyPreloader(new Preloader.ProgressNotification(progress));
        }
    }

    @Override
    public void start(Stage stage) {

    }
}