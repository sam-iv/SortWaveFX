package io.github.samiv.sortwavefx.ui;

import io.github.samiv.sortwavefx.algorithms.BubbleSort;
import io.github.samiv.sortwavefx.algorithms.FisherYatesShuffle;
import io.github.samiv.sortwavefx.model.SortAction;
import io.github.samiv.sortwavefx.model.SortingAlgorithm;
import io.github.samiv.sortwavefx.task.SortAndVisualTask;
import io.github.samiv.sortwavefx.util.ArrayUtil;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Arrays;

/**
 * A controller for the main application view ({@code MainView.fxml}).
 * <p>
 * The class acts as the controller in an MVC (Model-View-Controller) pattern, mediating between the view and the model
 * (where the model is the {@code io.github.samiv.sortwavefx.model} and {@code io.github.samiv.sortwavefx.algorithms}
 * packages). It orchestrates the sorting visualisation by creating and managing a {@link SortAndVisualTask}.
 *
 * @see SortAndVisualTask
 */
public class MainController {
    /** The container that holds the visual {@link Rectangle} bars. */
    @FXML
    private HBox barContainer;

    private final int userSelectedSize = 50; // TODO: Make it changeable

    /** An array that is the true state for the visualisation/sort, modified in-place by {@link SortingAlgorithm}'s */
    private int[] currentArray;

    @FXML
    public void initialize() {
    }

    /**
     * A method to generate and display bars. It makes use of an {@link ArrayUtil} utility class to generate an array
     * of integers in ascending sequential order, the method uses said array to create {@link Rectangle} nodes to be
     * displayed in the {@code barContainer}.
     *
     * @param size The number of bars to be generated and displayed.
     *
     * @see ArrayUtil
     */
    private void generateAndDisplayBars(int size) {
        double maxBarHeight = barContainer.getHeight();
        double barWidth = barContainer.getWidth() / size;

        this.currentArray = ArrayUtil.generateArray(size);

        barContainer.getChildren().clear();
        barContainer.setSpacing(5);

        for (int value : this.currentArray) {
            double barHeight = maxBarHeight * ((double) value / size);

            Rectangle rectangle = new Rectangle(barWidth, barHeight);
            rectangle.setFill(Color.web("#0099ff"));
            barContainer.getChildren().add(rectangle);
        }
    }

    /**
     * A method to swap two bars in the given list to reflect a {@link io.github.samiv.sortwavefx.model.ActionType#SWAP}
     * in the sorting algorithm.
     *
     * @param bars An {@link ObservableList<Node>} of nodes from the {@code barContainer}.
     * @param index1 The first index to be swapped.
     * @param index2 The second index to be swapped.
     */
    private void swapBars(ObservableList<Node> bars, int index1, int index2) {
        if (index1 == index2) {
            return;
        }

        int higherIndex = Math.max(index1, index2);
        int lowerIndex = Math.min(index1, index2);

        Node node1 = bars.get(lowerIndex);
        Node node2 = bars.get(higherIndex);

        bars.remove(higherIndex);
        bars.remove(lowerIndex);

        bars.add(lowerIndex, node2);
        bars.add(higherIndex, node1);
    }

    /**
     * A method to animate the {@link io.github.samiv.sortwavefx.model.ActionType#COMPARE} action by temporarily
     * changing the colour of two bars. The colour change is reverted after a short pause.
     *
     * @param bar1 The first bar being compared
     * @param bar2 The second bar being compared
     */
    private void compareBars(Rectangle bar1, Rectangle bar2) {
        Paint originalColour = bar1.getFill();

        bar1.setFill(Color.YELLOW);
        bar2.setFill(Color.YELLOW);

        PauseTransition pause = new PauseTransition(Duration.millis(100));

        pause.setOnFinished(actionEvent -> {
            bar1.setFill(originalColour);
            bar2.setFill(originalColour);
        });

        pause.play();
    }

    /**
     * A method to handle the on-click event for the {@code startButton} in the view ({@code MainView.fxml}).
     * <p>
     * The method orchestrates the entire visualisation sequence:
     * <ul>
     *     <li>It generates a new set of bars by invoking {@link #generateAndDisplayBars(int)} passing
     *     {@code userSelectedSize} as the size.</li>
     *     <li>It creates and runs {@link SortAndVisualTask}s to animate both the visualisation of the shuffle and
     *      sorting, where the shuffle task is chained to the animate task; hence, after the shuffle is complete, the
     *      sort begins.</li>
     * </ul>
     *
     * @see SortAndVisualTask
     */
    @FXML
    private void startButtonClicked() {
        generateAndDisplayBars(userSelectedSize);

        SortingAlgorithm shuffle = new FisherYatesShuffle();
        shuffle.setup(this.currentArray);
        SortAndVisualTask shuffleTask = new SortAndVisualTask(shuffle, 250);

        // TODO: Hardcoded for a simple test
        SortingAlgorithm sort = new BubbleSort();
        sort.setup(this.currentArray);
        SortAndVisualTask sortTask = new SortAndVisualTask(sort, 500);

        // A listener to handle UI animations dependent on SortAction.
        ChangeListener<SortAction> animationListener = (obs, oldAction,
                                                        newAction) -> {
            if (newAction == null || newAction.indices().length < 2) {
                return;
            }

            int[] indices = newAction.indices();
            ObservableList<Node> bars = barContainer.getChildren();

            Rectangle bar1 = (Rectangle) bars.get(indices[0]);
            Rectangle bar2 = (Rectangle) bars.get(indices[1]);

            switch (newAction.actionType()) {
                case SWAP:
                    compareBars(bar1, bar2);
                    if (indices[0] != indices[1]) {
                        swapBars(bars, indices[0], indices[1]);
                        System.out.println(Arrays.toString(currentArray));
                    }
                    break;

                case COMPARE:
                    compareBars(bar1, bar2);
                    break;
            }
        };

        // Attach animation listener to both tasks.
        shuffleTask.valueProperty().addListener(animationListener);
        sortTask.valueProperty().addListener(animationListener);

        // Chain the tasks (When shuffle task succeeds, star the sort task.
        shuffleTask.setOnSucceeded(workerStateEvent -> {
            new Thread(sortTask).start();
        });

        // Start the visualisation by first running the shuffle task on a new thread.
        new Thread(shuffleTask).start();
    }
}
