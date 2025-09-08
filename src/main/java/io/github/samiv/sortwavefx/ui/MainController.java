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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Objects;

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
    /** The container that holds the visual {@link Region} bars. */
    @FXML
    private HBox barContainer;

    private final int userSelectedSize = 30; // TODO: Make it changeable

    /** An array that is the true state for the visualisation/sort, modified in-place by {@link SortingAlgorithm}'s */
    private int[] currentArray;

    private MediaPlayer swapMediaPlayer;
    private MediaPlayer compareMediaPlayer;

    private final String BAR_STYLE_ORIGINAL = "-fx-background-color: #0099ff;";
    private final String BAR_STYLE_COMPARE = "-fx-background-color: #66ccff;";

    @FXML
    public void initialize() {
        // Sound setup
        String swapSoundPath = "/io/github/samiv/sortwavefx/sounds/swap.wav";
        String compareSoundPath = "/io/github/samiv/sortwavefx/sounds/compare.wav";

        try {
            Media swapMedia = new Media(Objects.requireNonNull(getClass()
                    .getResource(swapSoundPath), swapSoundPath).toExternalForm());

            Media compareMedia = new Media(Objects.requireNonNull(getClass()
                    .getResource(compareSoundPath), compareSoundPath).toExternalForm());

            this.swapMediaPlayer = new MediaPlayer(swapMedia);
            this.compareMediaPlayer = new MediaPlayer(compareMedia);
        } catch (Exception e) {
            System.err.println("Couldn't load sound files: " + e.getMessage());
        }
    }

    /**
     * A method to generate and display bars. It makes use of an {@link ArrayUtil} utility class to generate an array
     * of integers in ascending sequential order, the method uses said array to create {@link Region} nodes to be
     * displayed in the {@code barContainer}.
     *
     * @param size The number of bars to be generated and displayed.
     *
     * @see ArrayUtil
     */
    private void generateAndDisplayBars(int size) {
        double maxBarHeight = barContainer.getHeight();

        this.currentArray = ArrayUtil.generateArray(size);

        barContainer.getChildren().clear();
        barContainer.setSpacing(5);

        for (int value : this.currentArray) {
            Region bar = new Region();

            // Allow barContainer to control each bar's width
            HBox.setHgrow(bar, Priority.ALWAYS);

            bar.setStyle(BAR_STYLE_ORIGINAL);

            double barHeight = maxBarHeight * ((double) value / size);
            bar.setPrefHeight(barHeight);
            barContainer.getChildren().add(bar);
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
    private void compareBars(Region bar1, Region bar2) {
        bar1.setStyle(BAR_STYLE_COMPARE);
        bar2.setStyle(BAR_STYLE_COMPARE);

        PauseTransition pause = new PauseTransition(Duration.millis(10));

        pause.setOnFinished(actionEvent -> {
            bar1.setStyle(BAR_STYLE_ORIGINAL);
            bar2.setStyle(BAR_STYLE_ORIGINAL);
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
        SortAndVisualTask shuffleTask = new SortAndVisualTask(shuffle, 50);

        // TODO: Hardcoded for a simple test
        SortingAlgorithm sort = new BubbleSort();
        sort.setup(this.currentArray);
        SortAndVisualTask sortTask = new SortAndVisualTask(sort, 50);

        // A listener to handle UI animations dependent on SortAction.
        ChangeListener<SortAction> animationListener = (obs, oldAction,
                                                        newAction) -> {
            if (newAction == null || newAction.indices().length < 2) {
                return;
            }

            int[] indices = newAction.indices();
            ObservableList<Node> bars = barContainer.getChildren();

            Region bar1 = (Region) bars.get(indices[0]);
            Region bar2 = (Region) bars.get(indices[1]);

            switch (newAction.actionType()) {
                case SWAP:
                    compareBars(bar1, bar2);
                    if (indices[0] != indices[1]) {
                        swapBars(bars, indices[0], indices[1]);
                        System.out.println(Arrays.toString(currentArray));
                    }

                    swapMediaPlayer.seek(Duration.ZERO);
                    swapMediaPlayer.play();
                    break;

                case COMPARE:
                    compareBars(bar1, bar2);

                    compareMediaPlayer.seek(Duration.ZERO);
                    compareMediaPlayer.play();
                    break;
            }
        };

        // Attach animation listener to both tasks.
        shuffleTask.valueProperty().addListener(animationListener);
        sortTask.valueProperty().addListener(animationListener);

        // Chain the tasks (When shuffle task succeeds, start the sort task).
        shuffleTask.setOnSucceeded(workerStateEvent -> {
            new Thread(sortTask).start();
        });

        // Start the visualisation by first running the shuffle task on a new thread.
        new Thread(shuffleTask).start();
    }
}
