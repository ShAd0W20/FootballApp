package com.shad0wstv.footballapp;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class CustomPreloader extends Preloader {
    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    private Scene scene;

    private Label progress;

    private Stage stage;

    @Override
    public void init() {
        Platform.runLater(() -> {

            ImageView imageView = new ImageView(new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("/images/logo.png"))));
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setStyle("-fx-margin: 0 60 0 60px;");

            Label title = new Label("Loading, please wait...");
            title.setTextAlignment(TextAlignment.CENTER);
            title.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-margin: 20;");
            progress = new Label("0%");
            progress.setStyle("-fx-font-size: 20px;");
            progress.setTextAlignment(TextAlignment.CENTER);
            progress.setTextFill(javafx.scene.paint.Color.WHITE);

            VBox root = new VBox(imageView, title, progress);
            root.alignmentProperty().set(Pos.CENTER);
            root.setStyle("-fx-background-color: #111827;");
            root.setPrefSize(WIDTH, HEIGHT);

            scene = new Scene(root, WIDTH, HEIGHT);
        });
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        // Set preloader scene and show stage.
        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.setTitle("shad0wstv");
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("/images/logo.png"))));
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        // Handle application notification in this point (see MainApplication#init).
        if (info instanceof ProgressNotification) {
            progress.setText(((ProgressNotification) info).getProgress() + "%");
        }
    }

    @Override
    public void handleStateChangeNotification(@NotNull StateChangeNotification info) {
        // Handle state change notifications.
        StateChangeNotification.Type type = info.getType();
        if (type == StateChangeNotification.Type.BEFORE_START) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load(), 1280, 720);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            this.stage.setScene(scene);
            this.stage.show();
        }
    }
}
