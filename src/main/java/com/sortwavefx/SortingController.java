package com.sortwavefx;

import com.sortwavefx.Models.IModelObserver;
import com.sortwavefx.Models.SortingModel;
import com.sortwavefx.Models.Sorts.Sorts;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SortingController implements IModelObserver {

  private final Color defaultColour = Color.BLUE;
  @FXML
  public Canvas barCanvas;
  private PauseTransition swapDelay;
  @FXML
  private Text sortName;
  @FXML
  private Button startButton;
  private SortingModel sortingModel;

  @FXML
  private void startButtonClicked() {
    sortingModel = new SortingModel(50);
    sortingModel.setObserver(this);
    generateBars();

    PauseTransition delayTransition = new PauseTransition(Duration.millis(500));
    delayTransition.setOnFinished(event -> sort());
    delayTransition.play();
  }

  private void generateBars() {
    double[] array = sortingModel.getArrayToSort();

    GraphicsContext graphicsContext = barCanvas.getGraphicsContext2D();
    graphicsContext.clearRect(0, 0, barCanvas.getWidth(), barCanvas.getHeight());

    double width = barCanvas.getWidth() / sortingModel.getSize();

    double maxHeight = barCanvas.getHeight();

    double maxMagnitude = sortingModel.getMaxValue();

    for (int i = 0; i < array.length; i++) {
      double height = maxHeight * (array[i] / maxMagnitude);

      double x = i * width;
      double y = barCanvas.getHeight() - height;

      graphicsContext.setFill(defaultColour);
      graphicsContext.fillRect(x, y, width, height);
    }
  }

  private void sort() {
    sortingModel.shuffle();
  }

  @Override
  public void onSwap(int index1, int index2) {
    generateBars();
  }

  @Override
  public void onShuffleComplete() {
    //sortingModel.sort(Sorts.BUBBLE_SORT);
    System.out.println("Shuffle Complete!");

    PauseTransition delayTransition = new PauseTransition(Duration.millis(500));
    delayTransition.setOnFinished(event -> sortingModel.sort(Sorts.BUBBLE_SORT));
    delayTransition.play();
  }

}
