package com.sortwavefx.Models.Sorts;

import com.sortwavefx.Models.SortingModel;

/**
 * A class for a bubble down adaptive bubble sort.
 *
 * @author Samuel Ivuerah
 */
public class AdaptiveBubbleSort extends Sort {

  /**
   * A constructor to define the model to be used.
   * @param model The model to be used.
   */
  public AdaptiveBubbleSort(SortingModel model) {
    super(model);
  }

  /**
   * A method that starts the sort with delays.
   */
  @Override
  public void start() {
    new Thread(() -> {
      // Loop through each element.
      for (int i = 0, lastSwapIdx = 0; i < model.getSize(); i = lastSwapIdx) {
        int prevLastSwapIdx = lastSwapIdx; // Store the index of the last swap.

        // Loop backwards.
        for (int j = model.getSize() - 1; j > i; j--) {
          // Compare to see if the current element is greater than the previous one.
          if (model.compare(j, j - 1)) {
            delay();
            // Swap if it is.
            model.swap(j, j - 1);
            // Update the last swap index
            lastSwapIdx = j;
            delay();
          }
        }
        // If no swaps occurred in the last pass, exit the loop
        if (prevLastSwapIdx == lastSwapIdx) {
          return;
        }
      }
      // TODO handle ending.
    }).start();
  }
}
