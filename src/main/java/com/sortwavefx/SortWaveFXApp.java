package com.sortwavefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A class that serves as the entry point for SortWaveFX.
 *
 * @author Samuel Ivuerah
 */
public class SortWaveFXApp extends Application {

  /** The primary stage. */
  private Stage primaryStage;

  /** The presenter for the main-menu. */
  private MainMenuPresenter mainMenu;

  /** The presenter for the visualisation. */
  private VisualPresenter visual;

  /** The presenter for the end screen. */
  private EndPresenter end;

  public static void main(String[] args) {
    launch(args);
  }

  /** A method responsible for initialising the presenters and showing the main-menu. */
  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;

    mainMenu = new MainMenuPresenter();
    visual = new VisualPresenter();
    end = new EndPresenter();

    mainMenu.setMainApp(this);
    visual.setMainApp(this);
    end.setMainApp(this);

    mainMenu.show();
  }

  /** A method to change the scenes on the stage. */
  public void changeScene(Scene scene) {
    primaryStage.setScene(scene);
    if (!(primaryStage.isShowing())) {
      primaryStage.show();
    }
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public MainMenuPresenter getMainMenu() {
    return mainMenu;
  }

  public VisualPresenter getVisual() {
    return visual;
  }

  public EndPresenter getEnd() {
    return end;
  }
}
