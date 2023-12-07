package com.sortwavefx.Models.Sorts;

import com.sortwavefx.Models.SortingModel;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A class for a bogo sort.
 *
 * @author Samuel Ivuerah
 */
public class BogoSort extends Sort {

  /** A lock to prevent concurrent modification. */
  // TODO Implement concurrent modification where possible.
  private final ReentrantLock lock = new ReentrantLock();

  /**
   * A constructor to define the model to be used.
   *
   * @param model The model to be used.
   */
  public BogoSort(SortingModel model) {
    super(model);
  }

  /**
   * A method that starts the sort with delays.
   */
  @Override
  public void start() {
    // Create a new thread to run the sort.
    new Thread(() -> {
      // Loop until the array is sorted.
      while (true) {
        // Lock the array to prevent concurrent modification.
        lock.lock();
        try {
          if (isSorted()) {
            break;
          }
        } finally {
          // Unlock the array.
          lock.unlock();
        }
        shuffle();
      }
    }).start();
  }

  /**
   * A method to shuffle the array using a fisher-yates shuffle.
   */
  private void shuffle() {
    // Lock the array to prevent concurrent modification.
    lock.lock();
    try {
      int n = model.getSize() - 1;
      Random rng = new Random();

      // Loop through the array.
      for (int i = n; i > 0; i--) {
        // Pick a random index from 0 to i.
        int j = rng.nextInt(i + 1);

        // Swap the elements.
        model.swap(i, j);

        // Introduce a delay.
        delay();
      }
    } finally {
      // Unlock the array.
      lock.unlock();
    }
  }

  /**
   * A method to check if the array is sorted.
   *
   * @return True if the array is sorted, false otherwise.
   */
  private boolean isSorted() {
    // Loop through the array.
    for (int i = 0; i < model.getSize() - 1; i++) {
      // Compare the current element to the next one.
      if (model.compare(i + 1, i)) {
        // Introduce a delay.
        delay();
        return false;
      }
    }
    return true;
  }
}