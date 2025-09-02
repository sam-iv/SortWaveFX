package io.github.samiv.sortwavefx.algorithms;

import io.github.samiv.sortwavefx.model.ActionType;
import io.github.samiv.sortwavefx.model.SortAction;
import io.github.samiv.sortwavefx.model.SortingAlgorithm;

import java.util.Random;

/**
 * An implementation of the Fisher-Yates shuffle algorithm using the {@link SortingAlgorithm} interface. Implemented
 * as a {@link SortingAlgorithm} to run a shuffle step-by-step on an array.
 *
 * @see SortingAlgorithm
 * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates Shuffle Wikipedia</a>
 */
public class FisherYatesShuffle implements SortingAlgorithm {
    private int[] array;
    private int i;
    private int j;
    private boolean isDone;
    private Random rng;

    @Override
    public void setup(int[] arrayToBeSorted) {
        this.array = arrayToBeSorted;

        if (arrayToBeSorted.length <= 1) {
            this.isDone = true;
        } else {
            this.i = arrayToBeSorted.length - 1;
            this.isDone = false;
            rng = new Random();
        }
    }

    @Override
    public SortAction step() {
        if (isDone) { return null; }

        SortAction sortAction;

        // Random integer such that 0 <= j <= i
        j = rng.nextInt(0, i + 1);

        // Exchange
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
        sortAction = new SortAction(ActionType.SWAP, i, j);

        i--;
        if (i <= 0) { isDone = true; }

        return sortAction;
    }

    @Override
    public boolean isDone() { return this.isDone; }

    @Override
    public int[] getArray() { return this.array; }

    @Override
    public String getName() { return "Fisher-Yates Shuffle"; }
}
