package io.github.samiv.sortwavefx.algorithms;

import io.github.samiv.sortwavefx.model.ActionType;
import io.github.samiv.sortwavefx.model.SortAction;
import io.github.samiv.sortwavefx.model.SortingAlgorithm;

/**
 * An implementation of the Bubble Sort Algorithm using the {@link SortingAlgorithm} interface.
 * <p>
 * {@link BubbleSort} maintains the internal state to perform a single comparison and, if needed, swap for every
 * {@link #step()} method invocation.
 *
 * @see SortingAlgorithm
 * @see <a href="https://en.wikipedia.org/wiki/Bubble_sort">Bubble Sort Wikipedia</a>
 */
public class BubbleSort implements SortingAlgorithm {
    private int[] array;
    private int i;
    private int j;
    private boolean isDone;
    private int accessCounter;
    private int comparisonCounter;
    private int swapCounter;

    @Override
    public void setup(int[] arrayToBeSorted) {
        this.array = arrayToBeSorted;

        // Arrays of sizes 0 or 1 are already sorted
        if (arrayToBeSorted.length <= 1) {
            this.isDone = true;
        } else {
            this.i = 0;
            this.j = 0;
            this.isDone = false;
            this.accessCounter = 0;
            this.comparisonCounter = 0;
            this.swapCounter = 0;
        }
    }

    @Override
    public SortAction step() {
        if (isDone) {
            return null;
        }

        SortAction sortAction;

        // Comparison
        comparisonCounter++;
        accessCounter += 2;
        if (array[j] > array[j + 1]) {
            int temp = array[j];
            array[j] = array[j + 1];
            array[j + 1] = temp;
            swapCounter++;
            accessCounter += 4;
            sortAction = new SortAction(ActionType.SWAP, accessCounter, comparisonCounter, swapCounter,
                    j, j + 1);
        } else {
            sortAction = new SortAction(ActionType.COMPARE, accessCounter, comparisonCounter, swapCounter,
                    j, j + 1);
        }

        // Incrementing Loop
        j++;
        if (j >= array.length - 1 - i) {
            i++;
            j = 0;
        }

        // Check if sorted
        if (i >= array.length - 1) {
            isDone = true;
        }

        return sortAction;
    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public int[] getArray() {
        return this.array;
    }

    @Override
    public int getAccessCount() { return accessCounter; }

    @Override
    public int getComparisonCount() { return comparisonCounter; }

    @Override
    public int getSwapCount() { return swapCounter; }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}
