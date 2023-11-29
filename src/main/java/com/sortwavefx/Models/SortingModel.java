package com.sortwavefx.Models;

import com.sortwavefx.Models.Sorts.AdaptiveBubbleSort;
import com.sortwavefx.Models.Sorts.BubbleSort;
import com.sortwavefx.Models.Sorts.Sort;
import com.sortwavefx.Models.Sorts.Sorts;
import java.util.Random;

/**
 * A model class to handle sorting logic.
 *
 * @author Samuel Ivuerah
 */
public class SortingModel {

  /** An array containing numbers to be sorted. */
  private final double[] arrayToSort;

  /** The largest number in the array. */
  private final double maxValue;

  /** The number of comparisons made during sorting. */
  private final int comparisons = 0; //TODO update UI with this

  /** The number of access made during sorting. */
  private int accesses = 0; //TODO update UI with this

  /** The number of swaps made during sorting. */
  private int swaps = 0; //TODO update UI with this

  /** The delay between steps. */
  private int stepDelay = 15; //TODO make adjustable

  /** The observer of this model. */
  private IModelObserver observer;

  /**
   * A constructor to instantiate a model, allowing for the number of items to be defined.
   * @param sortAmount The number of numbers to be made.
   */
  public SortingModel(int sortAmount) {
    // TODO: Validation
    arrayToSort = new double[sortAmount];
    // Create numbers in arrayToSort starting from 10.
    for (int i = 0; i < arrayToSort.length; i++) {
      arrayToSort[i] = (i * 10.0) + 10.0;
    }
    maxValue = arrayToSort[arrayToSort.length - 1];
  }

  /**
   * A method employing a Fisher-Yates shuffle to shuffle items in arrayToSort.
   */
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
  }

  /**
   * A method to select the requested sort to be used and start the sort.
   * @param algorithm The sort to be used.
   */
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

  /**
   * A method to swap two indexes in arrayToSort.
   * @param index1 The index of one of the parties in the swap.
   * @param index2 The index of one of the parties in the swap.
   */
  public void swap(int index1, int index2) {
    //TODO Validation
    double tmp = arrayToSort[index1];
    arrayToSort[index1] = arrayToSort[index2];
    arrayToSort[index2] = tmp;

    accesses += 3;

    notifyOnSwap(index1, index2);
  }

  /**
   * A method to compare two indexes in arrayToSort.
   * @param index1 The index of one of the parties in the comparison.
   * @param index2 The index of one of the parties in the comparison.
   * @return The boolean result for: index2 is greater than index1
   */
  public Boolean compare(int index1, int index2) {
    notifyOnCompare(index1, index2);
    return arrayToSort[index1] < arrayToSort[index2];
  }

  /**
   * A method to notify observers of a comparison made.
   * @param index1 The index of one of the parties in the comparison.
   * @param index2 The index of one of the parties in the comparison.
   */
  private void notifyOnCompare(int index1, int index2) {
    if (observer != null) {
      observer.onCompare(index1, index2);
    } else {
      // TODO throw an error.
    }
  }

  /**
   * A method to notify observers of a swap made.
   * @param index1 The index of one of the parties in the swap.
   * @param index2 The index of one of the parties in the swap.
   */
  private void notifyOnSwap(int index1, int index2) {
    if (observer != null) {
      observer.onSwap(index1, index2);
      swaps++;
    } else {
      // TODO throw an error.
    }
  }

  /**
   * A method to notify observers when shuffling is done.
   */
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
    this.stepDelay = (int) stepDelay;
  }

  public void setObserver(IModelObserver observer) {
    //TODO Validation
    this.observer = observer;
  }
}
