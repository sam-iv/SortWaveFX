package io.github.samiv.sortwavefx.ui;

import io.github.samiv.sortwavefx.algorithms.FisherYatesShuffle;
import io.github.samiv.sortwavefx.model.Algorithm;
import io.github.samiv.sortwavefx.model.SortAction;
import io.github.samiv.sortwavefx.model.SortingAlgorithm;
import io.github.samiv.sortwavefx.task.SortAndVisualTask;
import io.github.samiv.sortwavefx.util.ArrayUtil;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Optional;

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
    @FXML
    private HBox barContainer;
    @FXML
    private ComboBox<Algorithm> sortComboBox;
    @FXML
    private Text sortText;
    @FXML
    private Text statusText;
    @FXML
    private Text accessText;
    @FXML
    private Text comparisonText;
    @FXML
    private Text swapText;
    @FXML
    private Slider delaySlider;
    @FXML
    private TextField amountTextField;
    @FXML
    private Button dynamicButton;

    /** An array that is the true state for the visualisation/sort, modified in-place by {@link SortingAlgorithm}'s */
    private int[] currentArray;

    private SortAndVisualTask activeTask = null;

    private MediaPlayer swapMediaPlayer;
    private MediaPlayer compareMediaPlayer;

    private final String BAR_STYLE_ORIGINAL = "-fx-background-color: #0099ff;";
    @SuppressWarnings("FieldCanBeLocal")
    private final String BAR_STYLE_COMPARE = "-fx-background-color: #66ccff;";

    /**
     * A FXML method that is automatically invoked after the FXML elements are loaded
     * but before UI is displayed.
     * <p>
     * Responsible for:
     * <ul>
     *     <li>{@link ComboBox} ({@link #sortComboBox}) Initialisation</li>
     *     <li>{@link Text} ({@link #sortText}) Initialisation</li>
     *     <li>{@link Media} &amp {@link MediaPlayer} ({@link #swapMediaPlayer}, {@link #compareMediaPlayer})
     *     Initialisation</li>
     * </ul>
     */
    @FXML
    public void initialize() {
        // ComboBox setup
        sortComboBox.getItems().addAll(Algorithm.values());
        sortComboBox.setValue(Algorithm.BUBBLE_SORT);

        // SortText setup
        sortText.setText(Algorithm.BUBBLE_SORT.getDisplayName());

        // StatusText setup
        statusText.setText("Status: " + Status.IDLE.getStatusName());

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
     * A simple FXML method that is invoked onAction of the {@code sortComboBox} to update
     * the title of the sort.
     */
    @FXML
    public void updateSortText() {
        sortText.setText(sortComboBox.getValue().getDisplayName());
    }

    /**
     * A method to update the status displayed in {@link #statusText} using predefined statuses created in
     * {@link Status}.
     *
     * @param status The status to display.
     *
     * @see Status
     */
    private void updateStatus(Status status) {
        statusText.setText("Status: " + status.getStatusName());
    }

    /**
     * A method to reset UI elements. To do this the method re-enables previously locked elements, sets the
     * {@link #activeTask} to null, and updates the status.
     * @param cancelled If True, set status to {@link Status#STOPPED}, else, {@link Status#IDLE}.
     */
    private void resetUI(boolean cancelled) {
        Platform.runLater(() -> {
            dynamicButton.setText("Visualise");
            delaySlider.setDisable(false);
            amountTextField.setDisable(false);
            sortComboBox.setDisable(false);
        });
        activeTask = null;

        if (cancelled) {
            updateStatus(Status.STOPPED);
        } else {
            updateStatus(Status.IDLE);
        }
    }

    /**
     * A method to lock UI elements, to prevent the user from changing values, (although changing said values wouldn't
     * change the visualisation).
     * <p>
     * Elements locked:
     * <ul>
     *     <li>{@link #dynamicButton}</li>
     *     <li>{@link #delaySlider}</li>
     *     <li>{@link #amountTextField}</li>
     *     <li>{@link #sortComboBox}</li>
     * </ul>
     */
    private void lockUI() {
        Platform.runLater(() -> {
            dynamicButton.setText("Stop");
            delaySlider.setDisable(true);
            amountTextField.setDisable(true);
            sortComboBox.setDisable(true);
        });
    }

    private void updateCounters(int accesses, int comparisons, int swaps) {
        Platform.runLater(() -> {
            accessText.setText("Accesses: " + accesses);
            comparisonText.setText("Comparisons: " + comparisons);
            swapText.setText("Swaps: " + swaps);
        });
    }

    private void resetCounters() {
        Platform.runLater(() -> {
            accessText.setText("Accesses: " + 0);
            comparisonText.setText("Comparisons: " + 0);
            swapText.setText("Swaps: " + 0);
        });
    }

    /**
     * A method to display Error Alert Box with custom content and title.
     *
     * @param typeOfError The type of error, to be used as the title for the box.
     * @param message The custom error, will be used as the content for the box.
     */
    @SuppressWarnings("SameParameterValue")
    private void showError(String typeOfError, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(typeOfError);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * A method to get and validate the entry from the {@link #amountTextField}. It uses
     * {@link #showError(String, String)} to display alert to the user if the entry fails validation.
     *
     * @return An optional container of Integer of the string if it can be converted to an int between 3 - 100,
     * otherwise empty.
     * 
     * @see #showError(String, String) 
     */
    private Optional<Integer> getAmountTextFieldValue() {
        try {
            int output = Integer.parseInt(amountTextField.getText());
            if (output >= 3 && output <= 100) {
                return Optional.of(output);
            } else {
                showError("Input Error", "Please enter a valid number (3 - 100)");
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            showError("Input Error", "Please enter a valid number (3 - 100)");
            return Optional.empty();
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
        barContainer.setSpacing(1);

        for (int value : this.currentArray) {
            Region bar = new Region();

            // Allow barContainer to control each bar's width
            HBox.setHgrow(bar, Priority.ALWAYS);
            bar.setMinWidth(0);
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
        PauseTransition pause = new PauseTransition(Duration.millis(delaySlider.getValue() * 0.75));

        pause.setOnFinished(actionEvent -> {
            bar1.setStyle(BAR_STYLE_ORIGINAL);
            bar2.setStyle(BAR_STYLE_ORIGINAL);
        });

        pause.play();
    }

    /**
     * The method orchestrates the entire visualisation sequence:
     * <ul>
     *     <li>It generates a new set of bars by invoking {@link #generateAndDisplayBars(int)} passing
     *     {@code size} as the size, created from {@link #getAmountTextFieldValue()}</li>
     *     <li>It creates and runs {@link SortAndVisualTask}s and uses {@link #swapBars(ObservableList, int, int)} and
     *      {@link #compareBars(Region, Region)} to animate both the visualisation of the shuffle and
     *      sorting, where the shuffle task is chained to the animate task; hence, after the shuffle is complete, the
     *      sort begins.</li>
     *      <li>It controls UI elements through helper methods.</li>
     * </ul>
     *
     * @see SortAndVisualTask
     * @see #generateAndDisplayBars(int)
     * @see #getAmountTextFieldValue()
     * @see #swapBars(ObservableList, int, int)
     * @see #compareBars(Region, Region)
     * @see #updateStatus(Status)
     * @see #lockUI()
     * @see #resetUI(boolean)
     */
    private void startSort() {
        Optional<Integer> size = getAmountTextFieldValue();
        if (size.isEmpty()) {
            return;
        }

        generateAndDisplayBars(size.get());

        SortingAlgorithm shuffle = new FisherYatesShuffle();
        shuffle.setup(this.currentArray);
        SortAndVisualTask shuffleTask = new SortAndVisualTask(shuffle, (long) delaySlider.getValue());

        SortingAlgorithm sort = sortComboBox.getValue().createInstance();
        sort.setup(this.currentArray);
        SortAndVisualTask sortTask = new SortAndVisualTask(sort, (long) delaySlider.getValue());

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

            updateCounters(newAction.accessCount(), newAction.comparisonCount(), newAction.swapCount());
        };

        // Attach animation listener to both tasks.
        shuffleTask.valueProperty().addListener(animationListener);
        sortTask.valueProperty().addListener(animationListener);

        // Chain the tasks (When shuffle task succeeds, start the sort task).
        shuffleTask.setOnSucceeded(workerStateEvent -> {
            PauseTransition sortPause = new PauseTransition(Duration.millis(700));

            sortPause.setOnFinished(actionEvent -> {
                resetCounters();
                new Thread(sortTask).start();
            });

            Platform.runLater(() -> updateStatus(Status.SORTING)); // runLater to not update UI on background threads
            activeTask = sortTask;
            sortPause.play();
        });

        sortTask.setOnSucceeded(workerStateEvent -> resetUI(false));

        shuffleTask.setOnCancelled(workerStateEvent -> resetUI(true));

        sortTask.setOnCancelled(workerStateEvent -> resetUI(true));

        // Start the visualisation by first running the shuffle task on a new thread.
        PauseTransition shufflePause = new PauseTransition(Duration.millis(500));
        shufflePause.setOnFinished(actionEvent -> new Thread(shuffleTask).start());

        updateStatus(Status.SHUFFLING);
        activeTask = shuffleTask;
        dynamicButton.setText("Stop");
        lockUI();
        resetCounters();
        shufflePause.play();
    }

    /**
     * An FXML method to handle the on-click event for the {@link #dynamicButton} in the view ({@code MainView.fxml}).
     * <p>
     * The method decides whether to stop the {@link #activeTask}, if there is one running, or to start the sort.
     *
     * @see #startSort()
     */
    @FXML
    private void onDynamicButtonClick() {
        if (activeTask != null) {
            activeTask.cancel();
        } else {
            startSort();
        }
    }
}
