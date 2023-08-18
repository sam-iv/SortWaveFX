package com.sortwavefx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * A presenter class to display the main-menu and to handle settings for the visualiser and events.
 *
 * @author Samuel Ivuerah
 */
public class MainMenuPresenter extends Presenter {

  /** A method to load and display the main-menu. */
  @Override
  public void show() {

    if (root == null) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        loader.setController(this);
        root = loader.load();
      } catch (IOException e) {
        error();
        throw new RuntimeException(e);
      }
    }
    if (defaultScene == null) {
      defaultScene = new Scene(root);
    }

    mainApp.getPrimaryStage().setTitle("SortWaveFX");
    mainApp.changeScene(defaultScene);
  }

  /** The event handler invoked when the user clicks the "Visualise" button. */
  @FXML
  private void onVisualiseButtonClicked() {
    mainApp.getVisual().show();
  }
}
