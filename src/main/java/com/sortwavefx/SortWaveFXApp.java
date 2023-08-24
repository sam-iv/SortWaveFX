package com.sortwavefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SortWaveFXApp extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(SortWaveFXApp.class.getResource("SortingView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());

    stage.setTitle("Sorting...");
    stage.setScene(scene);
    stage.show();
  }

}
