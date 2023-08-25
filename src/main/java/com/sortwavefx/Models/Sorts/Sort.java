package com.sortwavefx.Models.Sorts;

import com.sortwavefx.Models.SortingModel;

public abstract class Sort {

  protected final SortingModel model;
  private final int delay;

  public Sort(SortingModel model) {
    // TODO validation
    this.model = model;
    delay = this.model.getStepDelay();
  }

  public abstract void start();

  public void delay() {
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      // TODO handle exception.
    }
  }

  public abstract String getName();
}
