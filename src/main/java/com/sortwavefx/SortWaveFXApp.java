package com.sortwavefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SortWaveFXApp extends Application {

  private Stage mainStage;
  private Scene mainMenuScene;
  private Scene visualisationScene;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage mainStage) throws Exception {
    this.mainStage = mainStage;

    Parent mainMenuRoot = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
    Parent visualisationRoot = FXMLLoader.load(getClass().getResource("VisualisationScreen.fxml"));

    mainMenuScene = new Scene(mainMenuRoot);
    visualisationScene = new Scene(visualisationRoot);

    // Set initial scene
    mainStage.setScene(mainMenuScene);
    mainStage.setTitle("SortWaveFX");
    mainStage.show();
  }

  public void switchToVisualisation() {
    mainStage.setScene(visualisationScene);
  }

  public void switchToMainMenu() {
    mainStage.setScene(mainMenuScene);
  }
}
