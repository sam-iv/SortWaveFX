package io.github.samiv.sortwavefx.algorithms;

import io.github.samiv.sortwavefx.model.ActionType;
import io.github.samiv.sortwavefx.model.SortAction;
import io.github.samiv.sortwavefx.model.SortingAlgorithm;

/**
 * An implementation of the Selection Sort Algorithm using the {@link SortingAlgorithm} interface.
 * <p>
 * {@link SelectionSort} maintains the internal state to perform a single comparison/swap for every {@link #step()}
 * method invocation.
 *
 * @see SortingAlgorithm
 * @see <a href="https://en.wikipedia.org/wiki/Selection_sort">Selection Sort Wikipedia</a>
 */
public class SelectionSort implements SortingAlgorithm {
    private int[] array;
    private int i;
    private int j;
    private int minIndex;
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
            this.j = 1;
            this.minIndex = 0;
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

        // End of a pass (Swap, if needed)
        if (j >= array.length) {
            if (i != minIndex) {
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
                swapCounter++;
                accessCounter += 4;
            }

            sortAction = new SortAction(ActionType.SWAP, accessCounter, comparisonCounter, swapCounter, i, minIndex);

            // Incrementing Loop
            i++;
            minIndex = i;
            j = i + 1;

        } else {
            // Comparison
            comparisonCounter++;
            accessCounter += 2;
            sortAction = new SortAction(ActionType.COMPARE, accessCounter, comparisonCounter, swapCounter, j, minIndex);
            if (array[j] < array[minIndex]) {
                minIndex = j;
            }

            // Incrementing Loop
            j++;
        }

        // Check if sorted
        if (i >= array.length - 1) {
            isDone = true;
        }

        return sortAction;
    }

    @Override
    public boolean isDone() { return this.isDone; }

    @Override
    public int[] getArray() { return this.array; }

    @Override
    public int getAccessCount() { return accessCounter; }

    @Override
    public int getComparisonCount() { return comparisonCounter; }

    @Override
    public int getSwapCount() { return swapCounter; }

    @Override
    public String getName() { return "Selection Sort"; }
}
