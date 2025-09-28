package io.github.samiv.sortwavefx.task;

import io.github.samiv.sortwavefx.model.SortAction;
import io.github.samiv.sortwavefx.model.SortingAlgorithm;
import javafx.concurrent.Task;

/**
 * A background task to run the {@link SortingAlgorithm} one step at a time.
 * Communicating with the UI thread to display {@link SortAction} updates.
 *
 * @see SortingAlgorithm
 * @see SortAction
 */
public class SortAndVisualTask extends Task<SortAction> {
    private final SortingAlgorithm sortingAlgorithm;
    private final long delayMs;

    public SortAndVisualTask(SortingAlgorithm sortingAlgorithm, long delayMs) {
        this.sortingAlgorithm = sortingAlgorithm;
        this.delayMs = delayMs;
    }

    /**
     * A method that overrides {@link javafx.concurrent.Task}'s call method to loop
     * whilst the {@link SortingAlgorithm} hasn't completed or the visualisation hasn't been cancelled,
     * and send a {@link SortAction}'s to the UI.
     *
     * @return SortAction The sort action that describes what has happened in the algorithm.
     * @throws Exception If there is an issue whilst running the algorithm.
     */
    @Override
    @SuppressWarnings("BusyWait")
    protected SortAction call() throws Exception {
        while (!sortingAlgorithm.isDone() && !isCancelled()) {
            SortAction sortAction = sortingAlgorithm.step();
            updateValue(sortAction);
            Thread.sleep(delayMs);
        }
        return null;
    }
}
