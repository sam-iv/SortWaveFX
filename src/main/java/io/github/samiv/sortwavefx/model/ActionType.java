package io.github.samiv.sortwavefx.model;

/**
 * An Enumeration to define types of actions a sorting algorithm can perform in a step.
 * Used by {@link SortAction} to inform the UI what to animate.
 * <p>
 * Available actions:
 * <ul>
 *     <li>{@link #COMPARE} To indicate two elements that are being compared</li>
 *     <li>{@link #SWAP} To indicate two elements that are being swapped</li>
 * </ul>
 *
 * @see SortAction
 */
public enum ActionType {
    COMPARE,
    SWAP,
}
