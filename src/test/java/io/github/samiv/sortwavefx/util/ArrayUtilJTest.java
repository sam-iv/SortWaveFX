package io.github.samiv.sortwavefx.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArrayUtilJTest {
    @Test
    @DisplayName("Generate Sequential array of Size 10")
    public void testGenerateSequentialArraySizeTen() {
        // Setup
        int[] expectedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Test
        int[] testArray = ArrayUtil.generateArray(10);

        // Verify
        assertArrayEquals(expectedArray, testArray);
    }

    @Test
    @DisplayName("Generate Sequential array of Size 0")
    public void testGenerateSequentialArraySizeZero() {
        // Setup
        int[] expectedArray = {};

        // Test
        int[] testArray = ArrayUtil.generateArray(0);

        // Verify
        assertArrayEquals(expectedArray, testArray);
    }

   @Test
   @DisplayName("Generate Sequential array of Size 1")
   public void testGenerateSequentialArraySizeOne() {
        // Setup
       int[] expectedArray = {1};

       // Test
       int[] testArray = ArrayUtil.generateArray(1);

       // Verify
       assertArrayEquals(expectedArray, testArray);
   }

    @Test
    @DisplayName("Illegal Argument Exception thrown for negative size")
    public void testGenerateSequentialArrayNegativeSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArrayUtil.generateArray(-1);
        });
    }
}
