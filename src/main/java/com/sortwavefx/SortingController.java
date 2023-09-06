package com.sortwavefx;

import com.sortwavefx.Models.IModelObserver;
import com.sortwavefx.Models.SortingModel;
import com.sortwavefx.Models.Sorts.Sorts;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SortingController implements IModelObserver {

  private final Color defaultColour = Color.web("#0099ff");
  private final Color compareColour = Color.web("#66ccff");

  @FXML
  public Canvas barCanvas;
  @FXML
  private Text sortText;
  @FXML
  private Button startButton;
  @FXML
  private Text statusText;
  @FXML
  private Text accessText;
  @FXML
  private Text swapText;
  @FXML
  private TextField amountTextField;
  @FXML
  private Slider delaySlider;
  @FXML
  private ComboBox<Sorts> sortComboBox;

  private GraphicsContext graphicsContext;
  private SortingModel sortingModel;

  @FXML
  public void initialize() {
    sortComboBox.getItems().addAll(Sorts.values());
    sortComboBox.setValue(Sorts.BUBBLE_SORT);
    graphicsContext = barCanvas.getGraphicsContext2D();
  }

  @FXML
  private void startButtonClicked() {
    sortingModel = new SortingModel(100);
    sortingModel.setObserver(this);
    generateBars();

    PauseTransition delayTransition = new PauseTransition(Duration.millis(500));
    delayTransition.setOnFinished(event -> sort());
    delayTransition.play();
  }

  private void clearCanvas() {
    graphicsContext.clearRect(0, 0, barCanvas.getWidth(), barCanvas.getHeight());
  }

  private void drawBar(double x, double y, double width, double height, Paint color) {
    graphicsContext.setFill(color);
    graphicsContext.fillRect(x, y, width, height);
  }

  private void generateBars() {
    double[] array = sortingModel.getArrayToSort();
    double width = barCanvas.getWidth() / sortingModel.getSize();
    double maxHeight = barCanvas.getHeight();
    double maxMagnitude = sortingModel.getMaxValue();

    clearCanvas();

    for (int i = 0; i < array.length; i++) {
      double height = maxHeight * (array[i] / maxMagnitude);
      double x = i * width;
      double y = barCanvas.getHeight() - height;
      drawBar(x, y, width, height, defaultColour);
    }
  }

  private void generateBarsWithCompare(int index1, int index2) {
    double[] array = sortingModel.getArrayToSort();
    double width = barCanvas.getWidth() / sortingModel.getSize();
    double maxHeight = barCanvas.getHeight();
    double maxMagnitude = sortingModel.getMaxValue();

    clearCanvas();

    for (int i = 0; i < array.length; i++) {
      double height = maxHeight * (array[i] / maxMagnitude);
      double x = i * width;
      double y = barCanvas.getHeight() - height;

      if (i == index1 || i == index2) {
        drawBar(x, y, width, height, compareColour);
      } else {
        drawBar(x, y, width, height, defaultColour);
      }
    }
  }

  private void sort() {
    sortingModel.shuffle();
  }

  public void updateSortText() {
    sortText.setText(sortComboBox.getValue().toString());
  }

  @Override
  public void onSwap(int index1, int index2) {
    generateBars();
  }

  @Override
  public void onCompare(int index1, int index2) {
    generateBarsWithCompare(index1, index2);
  }

  @Override
  public void onShuffleComplete() {
    System.out.println("Shuffle Complete!");

    PauseTransition delayTransition = new PauseTransition(Duration.millis(500));
    delayTransition.setOnFinished(event -> sortingModel.sort(sortComboBox.getValue()));
    delayTransition.play();
  }
}
