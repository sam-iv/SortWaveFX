package com.sortwavefx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * A presenter class to display the end screen and to handle events.
 */
public class EndPresenter extends Presenter {

  /** A method to load and display the end screen. */
  @Override
  public void show() {
    if (root == null) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("End.fxml"));
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

    mainApp.getPrimaryStage().setTitle("Finished!");
    mainApp.changeScene(defaultScene);
  }

  @FXML
  private void onTestButtonClicked() {
    mainApp.getMainMenu().show();
  }
}
