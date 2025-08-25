package io.github.samiv.sortwavefx.model;

/**
 * An interface to provide a contract of methods that all sorting algorithms must have
 * and implement.
 * <p>
 * Methods to implement:
 * <ul>
 *     <li>{@link #setup(int[])} To pass the array to be sorted to the sorting algorithm</li>
 *     <li>{@link #step()} To conduct a single step of the algorithm, returning a {@link SortAction}</li>
 *     <li>{@link #isDone()} To signal if the algorithm has finished sorting</li>
 *     <li>{@link #getArray()} A getter to get the current state of the array</li>
 *     <li>{@link #getName()} A getter to get the name of the sorting algorithm</li>
 * </ul>
 */
public interface SortingAlgorithm {

    void setup(int[] arrayToBeSorted);

    SortAction step();

    boolean isDone();

    int[] getArray();

    String getName();
}
