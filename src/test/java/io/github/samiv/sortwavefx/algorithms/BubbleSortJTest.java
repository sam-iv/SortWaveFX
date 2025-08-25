package io.github.samiv.sortwavefx.algorithms;

import io.github.samiv.sortwavefx.model.ActionType;
import io.github.samiv.sortwavefx.model.SortAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BubbleSortJTest {

    BubbleSort bubbleSort = new BubbleSort();

    @Test
    @DisplayName("Unsorted Array of Size 5")
    public void testUnsortedArray() {
        // Setup
        int[] testArray = {5, 1, 4, 2, 8};
        bubbleSort.setup(testArray);

        // Test
        while (!bubbleSort.isDone()) {
            bubbleSort.step();
        }

        // Verify
        int[] expectedArray = {1, 2, 4, 5, 8};
        assertArrayEquals(expectedArray, bubbleSort.getArray());
    }

    @Test
    @DisplayName("Already Sorted Array")
    public void testSortedArray() {
        // Setup
        int[] testArray = {1, 2, 3, 4, 5};
        bubbleSort.setup(testArray);

        // Test
        while (!bubbleSort.isDone()) {
            bubbleSort.step();
        }

        // Verify
        assertArrayEquals(testArray, bubbleSort.getArray());
    }

    @Test
    @DisplayName("Duplicate Elements in Array")
    public void testDuplicateElements() {
        // Setup
        int[] testArray = {9, 1, 7, 1, 9};
        bubbleSort.setup(testArray);

        // Test
        while (!bubbleSort.isDone()) {
            bubbleSort.step();
        }

        // Verify
        int[] expectedArray = {1, 1, 7, 9, 9};
        assertArrayEquals(expectedArray, testArray);
    }

    @Test
    @DisplayName("Array with one element")
    public void testOneElement() {
        // Setup
        int[] testArray = {21};
        bubbleSort.setup(testArray);

        // Test
        while (!bubbleSort.isDone()) {
            bubbleSort.step();
        }

        // Verify
        assertArrayEquals(testArray, bubbleSort.getArray());
    }

    @Test
    @DisplayName("Empty Array")
    public void testEmptyArray() {
        // Setup
        int[] testArray = {};
        bubbleSort.setup(testArray);

        // Test
        while (!bubbleSort.isDone()) {
            bubbleSort.step();
        }

        // Verify
        assertArrayEquals(testArray, bubbleSort.getArray());
    }

    @Test
    @DisplayName("SortAction Sequence for an Array of Size 3")
    public void testSortActionSequence() {
        // Setup
        int[] testArray = {3, 1, 2};
        bubbleSort.setup(testArray);

        // Test & Verify

        /* Step 1: Compare 3 and 1. We should SWAP indices 0 and 1. (Test & Verify)
         *
         * Array becomes {1, 3, 2}
        */
        SortAction step1 = bubbleSort.step();
        assertEquals(ActionType.SWAP, step1.actionType());
        assertArrayEquals(new int[]{0, 1}, step1.indices());

        /* Step 2: Compare 3 and 2. We Should SWAP indices 1 and 2. (Test & Verify)
         *
         * Array becomes {1, 2, 3}
        */
        SortAction step2 = bubbleSort.step();
        assertEquals(ActionType.SWAP, step2.actionType());
        assertArrayEquals(new int[]{1, 2}, step2.indices());

        /*
         * Step 3: First Pass is done, therefore, j = 0, i++, Compare 1 and 2. No Swap,
         * so we should COMPARE indices 0 and 1. (Test & Verify)
         *
         * Array remains {1, 2, 3}
        */
        SortAction step3 = bubbleSort.step();
        assertEquals(ActionType.COMPARE, step3.actionType());
        assertArrayEquals(new int[]{0, 1}, step3.indices());

        // Step 4: The sort is finished, hence, next step should set the isDone flag to true.
        bubbleSort.step();
        assertTrue(bubbleSort.isDone());
    }
}
