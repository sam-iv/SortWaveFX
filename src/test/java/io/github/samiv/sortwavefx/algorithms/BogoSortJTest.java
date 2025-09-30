package io.github.samiv.sortwavefx.algorithms;

import io.github.samiv.sortwavefx.model.ActionType;
import io.github.samiv.sortwavefx.model.SortAction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BogoSortJTest {
    BogoSort bogoSort = new BogoSort();

    @Test
    @DisplayName("Unsorted Array of Size 5")
    public void testUnsortedArray() {
        // Setup
        int[] testArray = {5, 1, 4, 2, 8};
        int[] expectedArray = testArray.clone();

        Arrays.sort(expectedArray);
        bogoSort.setup(testArray);

        // Test
        while (!bogoSort.isDone()) {
            bogoSort.step();
        }

        // Verify
        assertArrayEquals(expectedArray, bogoSort.getArray());
    }

    @Test
    @DisplayName("Already Sorted Array (Enhanced)")
    public void testSortedArray() {
        // Setup
        int[] testArray = {1, 2, 3, 4, 5};
        int[] expectedArray = testArray.clone();
        bogoSort.setup(testArray);

        // Test
        for (int i = 0; i < testArray.length - 1; i++) {
            SortAction action = bogoSort.step();
            assertNotNull(action, "Step " + (i + 1) + " should not be null");
            assertEquals(ActionType.COMPARE, action.actionType(), "Step " + (i + 1) + " should be a COMPARE");
        }

        assertTrue(bogoSort.isDone(), "isDone() should be true after checking a sorted array");

        SortAction finalStep = bogoSort.step();
        assertNull(finalStep, "The step after completion should be null");
        assertArrayEquals(expectedArray, testArray);
    }

    @Test
    @DisplayName("Array with one element")
    public void testOneElement() {
        // Setup
        int[] testArray = {21};
        int[] expectedArray = testArray.clone();
        bogoSort.setup(testArray);

        // Verify
        assertTrue(bogoSort.isDone(), "An array of size 1 should be considered sorted immediately" +
                " after setup");
        assertArrayEquals(expectedArray, bogoSort.getArray());
    }

    @Test
    @DisplayName("Empty Array")
    public void testEmptyArray() {
        // Setup
        int[] testArray = {};
        int[] expectedArray = testArray.clone();
        bogoSort.setup(testArray);

        // Verify
        assertTrue(bogoSort.isDone(), "An array of size 1 should be considered sorted immediately" +
                " after setup");
        assertArrayEquals(expectedArray, bogoSort.getArray());
    }
}
