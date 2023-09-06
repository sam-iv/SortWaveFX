package com.sortwavefx.Models.Sorts;

import com.sortwavefx.Models.SortingModel;

public class AdaptiveBubbleSort extends Sort {

  public AdaptiveBubbleSort(SortingModel model) {
    super(model);
  }

  @Override
  public void start() {
    new Thread(() -> {
      for (int i = 0, lastSwapIdx = 0; i < model.getSize(); i = lastSwapIdx) {
        int prevLastSwapIdx = lastSwapIdx;

        for (int j = model.getSize() - 1; j > i; j--) {
          if (model.compare(j, j - 1)) {
            delay();
            model.swap(j, j - 1);
            lastSwapIdx = j;
            delay();
          }
        }
        if (prevLastSwapIdx == lastSwapIdx) {
          return;
        }
      }
      // TODO handle ending.
    }).start();
  }
}
