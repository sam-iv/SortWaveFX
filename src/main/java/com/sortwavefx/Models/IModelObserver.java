package com.sortwavefx.Models;

public interface IModelObserver {

  void onSwap(int index1, int index2);

  void onShuffleComplete();
}
