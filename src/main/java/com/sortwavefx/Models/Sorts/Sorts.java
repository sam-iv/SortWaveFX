package com.sortwavefx.Models.Sorts;

public enum Sorts {
  BUBBLE_SORT("Bubble Sort"), ADAPTIVE_BUBBLE_SORT("Adaptive Bubble Sort");
  private final String readableName;

  Sorts(String readableName) {
    this.readableName = readableName;
  }

  public String getReadableName() {
    return readableName;
  }

  @Override
  public String toString() {
    return getReadableName();
  }
}
