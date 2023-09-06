package com.sortwavefx.Models.Sorts;

import com.sortwavefx.Models.SortingModel;

public class BubbleSort extends Sort {

  public BubbleSort(SortingModel model) {
    super(model);
  }

  @Override
  public void start() {
    new Thread(() -> {
      for (int i = 0; i < model.getSize(); i++) {
        for (int j = model.getSize() - 1; j > i; j--) {
          if (model.compare(j, j - 1)) {
            delay();
            model.swap(j, j - 1);
            delay();
          }
        }
      }
    }).start();
  }
}
