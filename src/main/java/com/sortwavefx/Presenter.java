package com.sortwavefx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * An abstract class to add base functionality to Presenter classes.
 *
 * @author Samuel Ivuerah
 */
public abstract class Presenter {

  /** The main application that instantiates the presenter. */
  protected SortWaveFXApp mainApp;

  /** The default scene for the presenter. */
  protected Scene defaultScene;

  /** The root element of {@link Presenter#defaultScene} scene. */
  protected Parent root;

  protected Presenter() {
  }

  /** An abstract method allowing the presenter to "present" its default scene. */
  public abstract void show();

  /** A method to display a simple error on when exception handling. */
  public void error() {
    Alert errorAlert = new Alert(AlertType.ERROR);
    errorAlert.setHeaderText("Error :c");
    errorAlert.setContentText("https://github.com/sam-iv/SortWaveFX/issues");
    errorAlert.showAndWait();
    mainApp.getPrimaryStage().close();
  }

  public void setMainApp(SortWaveFXApp mainApp) {
    this.mainApp = mainApp;
  }
}
