package com.sortwavefx.Models;

import com.sortwavefx.Models.Sorts.AdaptiveBubbleSort;
import com.sortwavefx.Models.Sorts.BubbleSort;
import com.sortwavefx.Models.Sorts.Sort;
import com.sortwavefx.Models.Sorts.Sorts;
import java.util.Random;

public class SortingModel {

  private final double[] arrayToSort;
  private final double maxValue;
  private final int comparisons = 0;
  //TODO update UI with this
  private int accesses = 0;
  //TODO update UI with this
  private int swaps = 0;

  //TODO make adjustable
  private int stepDelay = 15;
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
    new Thread(() -> {
      int n = arrayToSort.length - 1;
      Random rng = new Random();
      for (int i = n; i > 0; i--) {
        int j = rng.nextInt(i + 1);

        swap(i, j);

        try {
          Thread.sleep(25);
        } catch (InterruptedException e) {
          //TODO Catch exception.
        }
      }
      notifyOnShuffleComplete();
    }).start();
    accesses = 0;
    swaps = 0;
  }

  public void sort(Sorts algorithm) {
    // TODO Validation
    Sort sort;
    switch (algorithm) {
      case BUBBLE_SORT:
        sort = new BubbleSort(this);
        break;
      case ADAPTIVE_BUBBLE_SORT:
        sort = new AdaptiveBubbleSort(this);
        break;
      default:
        // TODO: Output error
        sort = new BubbleSort(this);
        break;
    }
    sort.start();
  }

  public void swap(int index1, int index2) {
    //TODO Validation
    double tmp = arrayToSort[index1];
    arrayToSort[index1] = arrayToSort[index2];
    arrayToSort[index2] = tmp;

    accesses += 3;

    notifyOnSwap(index1, index2);
  }

  public Boolean compare(int index1, int index2) {
    notifyOnCompare(index1, index2);
    return arrayToSort[index1] < arrayToSort[index2];
  }

  private void notifyOnCompare(int index1, int index2) {
    if (observer != null) {
      observer.onCompare(index1, index2);
    } else {
      // TODO throw an error.
    }
  }

  private void notifyOnSwap(int index1, int index2) {
    if (observer != null) {
      observer.onSwap(index1, index2);
      swaps++;
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

  public int getStepDelay() {
    return stepDelay;
  }

  public void setStepDelay(double stepDelay) {
    //TODO Validation
    int stepDelayInt = (int) stepDelay;
    this.stepDelay = stepDelayInt;
  }

  public void setObserver(IModelObserver observer) {
    //TODO Validation
    this.observer = observer;
  }
}
