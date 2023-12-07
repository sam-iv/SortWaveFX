package com.sortwavefx.Models.Sorts;

/**
 * An enumeration of all sorts available.
 *
 * @author Samuel Ivuerah
 */
public enum Sorts {
  BUBBLE_SORT("Bubble Sort"), ADAPTIVE_BUBBLE_SORT("Adaptive Bubble Sort"),
  BOGO_SORT("Bogo Sort");

  /** The readable name for each sort. */
  private final String readableName;

  /**
   * A constructor that sets the readable name for the sort.
   * @param readableName The readable name of the sort.
   */
  Sorts(String readableName) {
    this.readableName = readableName;
  }

  public String getReadableName() {
    return readableName;
  }

  /**
   * A custom toString method that calls getReadableName().
   * @return getReadableName().
   */
  @Override
  public String toString() {
    return getReadableName();
  }
}
