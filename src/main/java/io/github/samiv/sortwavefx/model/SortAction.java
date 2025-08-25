package io.github.samiv.sortwavefx.model;

/**
 * A record to carry information of a step in a sorting algorithm.
 * To act as the message between the sorting logic (data) and the UI (view).
 *
 * @param actionType The type of action that is being performed.
 * @param indices The Java VarArgs, e.g. an array of size 0 to many, of the
 *                indices associated with the action.
 */
public record SortAction(ActionType actionType, int... indices) {
}
