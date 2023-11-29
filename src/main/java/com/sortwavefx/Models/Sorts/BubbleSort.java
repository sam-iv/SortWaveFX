package com.sortwavefx.Models.Sorts;

import com.sortwavefx.Models.SortingModel;

/**
 * A class for a bubble down bubble sort.
 *
 * @author Samuel Ivuerah
 */
public class BubbleSort extends Sort {

  /**
   * A constructor to define the model to be used.
   * @param model The model to be used.
   */
  public BubbleSort(SortingModel model) {
    super(model);
  }

  /**
   * A method that starts the sort with delays.
   */
  @Override
  public void start() {
    new Thread(() -> {
      // Loop through each element.
      for (int i = 0; i < model.getSize(); i++) {
        // Loop backwards.
        for (int j = model.getSize() - 1; j > i; j--) {
          // Compare to see if the current element is greater than the previous one.
          if (model.compare(j, j - 1)) {
            delay();
            // Swap if it is.
            model.swap(j, j - 1);
            delay();
          }
        }
      }
    }).start();
  }
}
