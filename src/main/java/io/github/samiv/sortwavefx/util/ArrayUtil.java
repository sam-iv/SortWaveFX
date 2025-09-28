package io.github.samiv.sortwavefx.util;

/**
 * A utility class for creating array for the sorting visualiser.
 * (Might be a lazy class but using it to separate responsibilities)
 */
public final class ArrayUtil {

    /** Private constructor, hence, cannot be instantiated. */
    private ArrayUtil() {}

    /**
     * A method to create a new array of sequential integers from 1.
     *
     * @param size The number of elements in the array. Must be non-negative
     * @return new int[] array with sequential numbers.
     * @throws IllegalArgumentException If {@literal size} is less than zero.
     */
    public static int[] generateArray(int size) throws IllegalArgumentException {
        if (size < 0) {
            throw new IllegalArgumentException("Array size cannot be negative.");
        }

        int[] output = new int[size];
        for (int i = 0; i < size; i++) {
            output[i] = i + 1;
        }
        return output;
    }
}
