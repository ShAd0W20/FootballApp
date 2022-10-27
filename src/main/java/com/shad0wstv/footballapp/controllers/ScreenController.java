package com.shad0wstv.footballapp.controllers;

import com.shad0wstv.footballapp.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ScreenController {
    private final Parent main;

    public ScreenController(String viewString) throws Exception {
        this.main = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(viewString)));
    }

    public void activate(ActionEvent event) {
        Scene scene = new Scene(this.main, 1280, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
}
