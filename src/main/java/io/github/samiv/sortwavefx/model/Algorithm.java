package io.github.samiv.sortwavefx.model;

import io.github.samiv.sortwavefx.algorithms.BubbleSort;

/**
 * An enumeration to define each sort the program can perform. Each sort will have
 * a {@link #displayName}, and {@link #algorithmType}, along with
 * a {@link #createInstance()} method overridden to create its respective {@link SortingAlgorithm}
 * object.
 *
 * @see AlgorithmType
 * @see SortingAlgorithm
 */
public enum Algorithm {
    BUBBLE_SORT("Bubble Sort", AlgorithmType.ITERATIVE) {
        @Override
        public SortingAlgorithm createInstance() {
            return new BubbleSort();
        }
    };

    private final String displayName;
    private final AlgorithmType algorithmType;

    Algorithm(String displayName, AlgorithmType algorithmType) {
        this.displayName = displayName;
        this.algorithmType = algorithmType;
    }

    public String getDisplayName() { return this.displayName; }

    public AlgorithmType getAlgorithmType() { return this.algorithmType; }

    /**
     * Factory method to create instances of {@link SortingAlgorithm}.
     * @return A new object that is an implementation of {@link SortingAlgorithm}/
     */
    public abstract SortingAlgorithm createInstance();
}
