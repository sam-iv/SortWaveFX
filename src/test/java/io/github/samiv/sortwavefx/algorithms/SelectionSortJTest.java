package io.github.samiv.sortwavefx.algorithms;

import io.github.samiv.sortwavefx.model.ActionType;
import io.github.samiv.sortwavefx.model.SortAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SelectionSortJTest {
    SelectionSort selectionSort = new SelectionSort();

    @Test
    @DisplayName("Unsorted Array of Size 5")
    public void testUnsortedArray() {
        // Setup
        int[] testArray = {5, 1, 4, 2, 8};
        int[] expectedArray = testArray.clone();

        Arrays.sort(expectedArray);
        selectionSort.setup(testArray);

        // Test
        while (!selectionSort.isDone()) {
            selectionSort.step();
        }

        // Verify
        assertArrayEquals(expectedArray, selectionSort.getArray());
    }

    @Test
    @DisplayName("Already Sorted Array")
    public void testSortedArray() {
        // Setup
        int[] testArray = {1, 2, 3, 4, 5};
        int[] expectedArray = testArray.clone();
        selectionSort.setup(testArray);

        // Test
        while (!selectionSort.isDone()) {
            selectionSort.step();
        }

        // Verify
        assertArrayEquals(expectedArray, selectionSort.getArray());
    }

    @Test
    @DisplayName("Array with one element")
    public void testOneElement() {
        // Setup
        int[] testArray = {21};
        int[] expectedArray = testArray.clone();
        selectionSort.setup(testArray);

        // Verify
        assertTrue(selectionSort.isDone(), "An array of size 1 should be considered sorted immediately" +
                " after setup");
        assertArrayEquals(expectedArray, selectionSort.getArray());
    }

    @Test
    @DisplayName("Empty Array")
    public void testEmptyArray() {
        // Setup
        int[] testArray = {};
        int[] expectedArray = testArray.clone();
        selectionSort.setup(testArray);

        // Verify
        assertTrue(selectionSort.isDone(), "An array of size 1 should be considered sorted immediately" +
                " after setup");
        assertArrayEquals(expectedArray, selectionSort.getArray());
    }

    @Test
    @DisplayName("SortAction Sequence for an Array of Size 3")
    public void testSortActionSequence() {
        // Setup
        int[] testArray = {3, 1, 2};
        selectionSort.setup(testArray);

        // Test & Verify

        /* Step 1: Compare 1 with 3. New minimum found.
         *
         * minIndex is updated to 1.
         *
         * There have been 2 array accesses, 1 comparison, and 0 swaps since the start.
         */
        SortAction step1 = selectionSort.step();
        assertEquals(ActionType.COMPARE, step1.actionType());
        assertArrayEquals(new int[]{1, 0}, step1.indices());

        assertEquals(2, step1.accessCount());
        assertEquals(1, step1.comparisonCount());
        assertEquals(0, step1.swapCount());

        /* Step 2: Compare 2 with 1. No new minimum.
         *
         * minIndex remains 1.
         *
         * There have been 4 array accesses, 2 comparisons, and 0 swaps since the start.
         */
        SortAction step2 = selectionSort.step();
        assertEquals(ActionType.COMPARE, step2.actionType());
        assertArrayEquals(new int[]{2, 1}, step2.indices());

        assertEquals(4, step2.accessCount());
        assertEquals(2, step2.comparisonCount());
        assertEquals(0, step2.swapCount());

        /* Step 3: First pass is over. SWAP 3 with 1.
         *
         * Array becomes {1, 3, 2}.
         *
         * There have been 8 array accesses, 2 comparisons, and 1 swap since the start.
         */
        SortAction step3 = selectionSort.step();
        assertEquals(ActionType.SWAP, step3.actionType());
        assertArrayEquals(new int[]{0, 1}, step3.indices());

        assertEquals(8, step3.accessCount());
        assertEquals(2, step3.comparisonCount());
        assertEquals(1, step3.swapCount());

        /* Step 4: Next pass starts. Compare 2 with 3. New minimum.
         *
         * minIndex is updated to 2.
         *
         * There have been 10 array accesses, 3 comparisons, and 1 swap since the start.
         */
        SortAction step4 = selectionSort.step();
        assertEquals(ActionType.COMPARE, step4.actionType());
        assertArrayEquals(new int[]{2, 1}, step4.indices());

        assertEquals(10, step4.accessCount());
        assertEquals(3, step4.comparisonCount());
        assertEquals(1, step4.swapCount());

        /* Step 5: Second pass is over. SWAP 3 with 2.
         *
         * Array becomes {1, 2, 3}.
         *
         * There have been 14 array accesses, 3 comparisons, and 2 swaps since the start.
         */
        SortAction step5 = selectionSort.step();
        assertEquals(ActionType.SWAP, step5.actionType());
        assertArrayEquals(new int[]{1, 2}, step5.indices());

        assertEquals(14, step5.accessCount());
        assertEquals(3, step5.comparisonCount());
        assertEquals(2, step5.swapCount());

        // Step 6: The sort is finished, hence, next step should set the isDone flag to true, and the sortAction null.
        SortAction step6 = selectionSort.step();

        assertTrue(selectionSort.isDone());
        assertNull(step6);
    }
}
