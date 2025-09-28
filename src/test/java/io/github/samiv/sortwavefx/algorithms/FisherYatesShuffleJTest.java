package io.github.samiv.sortwavefx.algorithms;

import io.github.samiv.sortwavefx.model.ActionType;
import io.github.samiv.sortwavefx.model.SortAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FisherYatesShuffleJTest {
    FisherYatesShuffle fisherYatesShuffle = new FisherYatesShuffle();

    @Test
    @DisplayName("Array Must Contain Same Elements")
    public void testElementsSame() {
        // Setup
        int[] testArray = {1, 2, 3, 4, 5};
        int[] expectedArray = testArray.clone();
        fisherYatesShuffle.setup(testArray);

        // Test
        while (!fisherYatesShuffle.isDone()) {
            fisherYatesShuffle.step();
        }

        // Verify
        Arrays.sort(testArray);
        assertArrayEquals(expectedArray, testArray, "Shuffled array and re-sorted array should contain the " +
                "original elements");
    }

    @Test
    @DisplayName("Array of Size of 1")
    public void testShuffleOfSizeOne() {
        // Setup
        int[] testArray = {21};
        int[] expectedArray = testArray.clone();
        fisherYatesShuffle.setup(testArray);

        // Verify
        assertTrue(fisherYatesShuffle.isDone());
        assertArrayEquals(expectedArray, fisherYatesShuffle.getArray());
    }

    @Test
    @DisplayName("Array of Size 10")
    public void testShuffleOfSizeFifty() {
        // Setup
        int[] testArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] assertArray = testArray.clone();
        fisherYatesShuffle.setup(testArray);

        // Test
        while (!fisherYatesShuffle.isDone()) {
            fisherYatesShuffle.step();
        }

        // Verify
        assertFalse(Arrays.equals(assertArray, testArray), "Shuffled array should not be the same as the " +
                "original (this can fail by random chance");
    }

    @Test
    @DisplayName("Empty Array")
    public void testShuffleOnEmptyArray() {
        // Setup
        int[] testArray = {};
        int[] expectedArray = testArray.clone();
        fisherYatesShuffle.setup(testArray);

        // Verify
        assertTrue(fisherYatesShuffle.isDone());
        assertArrayEquals(expectedArray, fisherYatesShuffle.getArray());
    }

    @Test
    @DisplayName("Shuffle of Size 5 Array Performs 4 SortAction SWAPS and then finishes")
    public void testShuffleCompletesWithCorrectNumberOfSwap() {
        // Setup
        int arraySize = 5;
        int[] testArray = {1, 2, 3, 4, 5};
        fisherYatesShuffle.setup(testArray);

        int swapCounter = 0;
        int stepCounter = 0;

        // Test
        while(!fisherYatesShuffle.isDone()) {
            SortAction step = fisherYatesShuffle.step();
            stepCounter++;

            assertNotNull(step, "Action cannot be null unless shuffle is done");
            if (step.actionType() == ActionType.SWAP) {
                swapCounter++;
            }

            int expectedAccessCount = stepCounter * 4; // Each swap is 4 accesses
            assertEquals(expectedAccessCount, step.accessCount(), "Cumulative access count should be " +
                    "4x the step number");
            assertEquals(stepCounter, step.swapCount(), "Cumulative swap count should match the step number");
            assertEquals(0, step.comparisonCount(), "Fisher-Yates should have 0 comparisons");

            if (stepCounter > arraySize) {
                fail("Shuffle conducted too many steps without finishing");
            }
        }

        // Verify
        assertEquals(arraySize - 1, swapCounter, "A shuffle of size n must have exactly n - 1 swaps");
        assertTrue(fisherYatesShuffle.isDone(), "isDone() should be true after the correct number of swaps");
    }
}
