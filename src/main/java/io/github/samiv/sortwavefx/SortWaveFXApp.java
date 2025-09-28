package io.github.samiv.sortwavefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;

public class SortWaveFXApp extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SortWaveFXApp.class.getResource("ui/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("SortWaveFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
