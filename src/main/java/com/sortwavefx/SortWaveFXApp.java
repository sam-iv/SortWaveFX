package com.sortwavefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * An application class to act as the entry point of the SortWaveFXApp application.
 *
 * @author Samuel Ivuerah
 */
public class SortWaveFXApp extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * A method to be the entry point of SortWaveFXApp by overriding the start method of the
   * application class.
   *
   * @param stage The primary stage for the application.
   * @throws Exception A general exception to be thrown if something goes wrong.
   */
  @Override
  public void start(Stage stage) throws Exception {
    // Loading the .fxml file, (the view).
    FXMLLoader fxmlLoader = new FXMLLoader(SortWaveFXApp.class.getResource("SortingView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());

    // Applying the title and scene, and displaying it.
    stage.setTitle("SortWaveFX");
    stage.setScene(scene);
    stage.show();
  }
}
