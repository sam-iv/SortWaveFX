package com.sortwavefx;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

// TODO finish
public class VisualisationController {

  private final int numBars = 20;
  private final int barWidth = 20;
  private final int maxBarHeight = 200;
  @FXML
  private Pane visualisationPane;
  private List<Rectangle> bars;

  public void intialise(String selectedAlgorithm, boolean useOptimisation) {

  }

  private void generateRandomData() {
    // Generate data and initialize bars
  }

  private void startAnimation() {
    // Start the animation loop
  }

  private void animateStep() {
    // Animate a step of the sorting process
  }

  private void updateBars() {
    // Update the visualization based on data
  }

  public void onBackButtonClicked(ActionEvent actionEvent) {
  }
}
