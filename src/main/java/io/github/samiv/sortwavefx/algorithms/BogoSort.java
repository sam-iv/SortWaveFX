package io.github.samiv.sortwavefx.algorithms;

import io.github.samiv.sortwavefx.model.ActionType;
import io.github.samiv.sortwavefx.model.SortAction;
import io.github.samiv.sortwavefx.model.SortingAlgorithm;

public class BogoSort implements SortingAlgorithm {
    private int[] array;
    private int i;
    private FisherYatesShuffle shuffle;
    private boolean isShuffling;
    private boolean isDone;
    private int accessCounter;
    private int comparisonCounter;
    private int swapCounter;

    @Override
    public void setup(int[] arrayToBeSorted) {
        this.array = arrayToBeSorted;

        if (arrayToBeSorted.length <= 1) {
            this.isDone = true;
        } else {
            this.i = 0;
            this.shuffle = new FisherYatesShuffle();
            this.shuffle.setup(this.array);
            this.isShuffling = false;
            this.isDone = false;
            this.accessCounter = 0;
            this.comparisonCounter = 0;
            this.swapCounter = 0;
        }
    }

    @Override
    public SortAction step() {
        if (this.isDone) {
            return null;
        }

        SortAction action;

        if (isShuffling) {
            SortAction shuffleAction = shuffle.step();

            if (shuffleAction != null) {
                accessCounter += 4;
                swapCounter++;

                action = new SortAction(shuffleAction.actionType(), this.accessCounter, this.comparisonCounter,
                        this.swapCounter, shuffleAction.indices());
            } else if (shuffle.isDone()) {
                isShuffling = false;
                i = 0;
                action = new SortAction(ActionType.COMPARE, accessCounter, comparisonCounter, swapCounter,
                        0, 0); // Dummy action, the controller will ignore SortActions with identical indices.
            } else {
                action = null;
            }
        } else {
            comparisonCounter++;
            accessCounter += 2;

            action = new SortAction(ActionType.COMPARE, accessCounter, comparisonCounter, swapCounter, i, i + 1);

            if (array[i] > array[i + 1]) {
                isShuffling = true;
                shuffle.setup(array);
            } else {
                i++;
                if (i >= array.length - 1) {
                    isDone = true;
                }
            }
        }
        return action;
    }

    @Override
    public boolean isDone() { return this.isDone; }

    @Override
    public int[] getArray() { return this.array; }

    @Override
    public int getAccessCount() { return this.accessCounter; }

    @Override
    public int getComparisonCount() { return this.comparisonCounter; }

    @Override
    public int getSwapCount() { return this.swapCounter; }

    @Override
    public String getName() { return "Bogosort"; }
}
