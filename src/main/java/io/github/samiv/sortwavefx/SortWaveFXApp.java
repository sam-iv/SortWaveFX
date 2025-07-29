package io.github.samiv.sortwavefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SortWaveFXApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("SortWaveFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
