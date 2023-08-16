package com.sortwavefx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MainMenuController {

  @FXML
  private ComboBox<String> sortingAlgorithmComboBox;
  @FXML
  private CheckBox optimisationCheckBox;
  @FXML
  private Button startButton;

  @FXML
  private void onStartButtonClicked() {
    String selectedAlgorithm = sortingAlgorithmComboBox.getValue();
    boolean useOptimisation = optimisationCheckBox.isSelected();

    VisualisationController vController = new VisualisationController();
    vController.intialise(selectedAlgorithm, useOptimisation);

    Stage stage = (Stage) startButton.getScene().getWindow();
    stage.close();
  }

}
