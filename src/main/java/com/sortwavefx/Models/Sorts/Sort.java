package com.sortwavefx.Models.Sorts;

import com.sortwavefx.Models.SortingModel;

/**
 * An abstract class to provide abstraction and default behaviour for sorts.
 *
 * @author Samuel Ivuerah
 */
public abstract class Sort {

  /** The model to reference. */
  protected final SortingModel model;

  /** The delay to introduce between swaps and comparisons. */
  private final int delay;

  /**
   * A constructor to allow the definition of the model and to set the delay from the model.
   * @param model The model to be referenced.
   */
  public Sort(SortingModel model) {
    // TODO validation
    this.model = model;
    delay = this.model.getStepDelay();
  }

  /** A method all sorts need. */
  public abstract void start();

  /** A method to introduce the delay. */
  public void delay() {
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      // TODO handle exception.
    }
  }
}
