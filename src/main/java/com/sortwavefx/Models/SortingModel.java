package com.sortwavefx.Models;

import com.sortwavefx.Models.Sorts.BubbleSort;
import com.sortwavefx.Models.Sorts.Sorts;
import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class SortingModel {

  private final double[] arrayToSort;
  private final double maxValue;
  private int accesses = 0;
  private int swaps = 0;
  private IModelObserver observer;

  public SortingModel(int sortAmount) {
    // TODO: Validation
    arrayToSort = new double[sortAmount];
    for (int i = 0; i < arrayToSort.length; i++) {
      arrayToSort[i] = (i * 10.0) + 10.0;
    }
    maxValue = arrayToSort[arrayToSort.length - 1];
  }

  public void shuffle() {
    int numberOfSwaps = arrayToSort.length - 1;
    shuffleRecursive(numberOfSwaps);
  }

  private void shuffleRecursive(int i) {
    if (i > 0) {
      Random r = new Random();
      int j = r.nextInt(i + 1);
      swap(i, j);

      // Introduce a delay after each swap
      PauseTransition pauseTransition = new PauseTransition(Duration.millis(100));
      pauseTransition.setOnFinished(event -> {
        notifyOnSwap(i, j);
        shuffleRecursive(i - 1);
      });
      pauseTransition.play();
    } else {
      swaps = 0;
      accesses = 0;
      notifyOnShuffleComplete();
    }
  }

  public void sort(Sorts algorithm) {
    // TODO Validation
    switch (algorithm) {
      case BUBBLE_SORT:
        BubbleSort sort = new BubbleSort();
        //sort.run(this);
        break;
      default:
        // TODO: Output error
        break;
    }
  }

  public void swap(int index1, int index2) {
    //TODO Validation
    double tmp = arrayToSort[index1];
    arrayToSort[index1] = arrayToSort[index2];
    arrayToSort[index2] = tmp;

    accesses += 3;
    swaps++;
  }

  public void notifyOnSwap(int index1, int index2) {
    if (observer != null) {
      observer.onSwap(index1, index2);
    } else {
      // TODO throw an error.
    }
  }

  private void notifyOnShuffleComplete() {
    if (observer != null) {
      observer.onShuffleComplete();
    } else {
      // TODO throw an error
    }
  }

  public int getSize() {
    return arrayToSort.length;
  }

  public double getValue(int index) {
    //TODO Validation
    accesses++;
    return arrayToSort[index];
  }

  public double[] getArrayToSort() {
    return arrayToSort;
  }

  public double getMaxValue() {
    return maxValue;
  }

  public void setObserver(IModelObserver observer) {
    //TODO Validation
    this.observer = observer;
  }
}
