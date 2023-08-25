package com.sortwavefx.Models.Sorts;

import com.sortwavefx.Models.SortingModel;

public class BubbleSort extends Sort {

  private int delay;

  public BubbleSort(SortingModel model) {
    super(model);
  }

  @Override
  public void start() {
    new Thread(() -> {
      for (int i = 0, lastSwapIdx = 0; i < model.getSize(); i = lastSwapIdx) {
        int prevLastSwapIdx = lastSwapIdx;

        for (int j = model.getSize() - 1; j > i; j--) {
          if (model.getValue(j) < model.getValue(j - 1)) {
            model.swap(j, j - 1);
            lastSwapIdx = j;
            delay();

          }
        }
        if (prevLastSwapIdx == lastSwapIdx) {
          return;
        }
      }
    }).start();
  }

  @Override
  public String getName() {
    return "Bubble Sort";
  }
}
