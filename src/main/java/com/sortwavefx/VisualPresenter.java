package com.sortwavefx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * A presenter class to display the visualisation and to handle events.
 *
 * @author Samuel Ivuerah
 */
public class VisualPresenter extends Presenter {

  /** A method to load and display the visualisation. */
  @Override
  public void show() {
    if (root == null) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Visual.fxml"));
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

    mainApp.getPrimaryStage().setTitle("Sorting...");
    mainApp.changeScene(defaultScene);
  }

  @FXML
  private void onTestButtonClicked() {
    mainApp.getEnd().show();
  }
}
