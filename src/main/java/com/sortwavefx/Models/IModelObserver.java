package com.sortwavefx.Models;

/**
 * An interface to enable the observer pattern, allowing
 * for the controller to listen to the model in order to handle visual updates.
 *
 * @author Samuel Ivuerah
 */
public interface IModelObserver {

  // Called when swapping two indexes.
  void onSwap(int index1, int index2);

  // Called when comparing two indexes.
  void onCompare(int index1, int index2);

  // Called when shuffling has finished.
  void onShuffleComplete();
}
