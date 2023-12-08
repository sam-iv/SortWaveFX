package com.sortwavefx;

import com.sortwavefx.Models.IModelObserver;
import com.sortwavefx.Models.SortingModel;
import com.sortwavefx.Models.Sorts.Sorts;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * A controller class to handle interactions with the model and displaying the appropriate view.
 *
 * @author Samuel Ivuerah
 */
public class SortingController implements IModelObserver {

    /** The default colour of the bar. */
    private final Color defaultColour = Color.web("#0099ff");

    /** The colour of bars when they are being compared. */
    private final Color compareColour = Color.web("#66ccff");

    @FXML
    public HBox barBox; // The HBox that will contain the bars.
    @FXML
    private Text sortText; // A title for the name of the sort.
    @FXML
    private Button startButton;
    @FXML
    private Text statusText; // Current status of the algorithm e.g. shuffling, sorting, completed
    @FXML
    private Text accessText; // The number of array access made during the sort.
    @FXML
    private Text swapText; // The number of swaps carried out during the sort.
    @FXML
    private TextField amountTextField; // The number of bars to be used for the visualisation.
    @FXML
    private Slider delaySlider; // A slider to control the time delay between each swap.
    @FXML
    private ComboBox<Sorts> sortComboBox; // A combo box to select the type of sort.

    /** The sorting model. */
    private SortingModel sortingModel;

    /** The last two compared indexes. */
    private int lastComparedIndex1 = -1;
    private int lastComparedIndex2 = -1;

    /** The amount of items to be sorted. */
    private int amountToSort;

    /** The delay between each step. */
    private double delay;

    /**
     * An initialisation method, called after @FXML member have been loaded,
     * used to initialise the controller.
     */
    @FXML
    public void initialize() {

        // Populate the ComboBox with all sorting algorithms.
        sortComboBox.getItems().addAll(Sorts.values());

        // Selecting a default sorting algorithm, (bubble sort).
        sortComboBox.setValue(Sorts.BUBBLE_SORT);

        // Updating the sorting algorithm title.
        updateSortText();

        // Add a ChangeListener to amountTextField
        amountTextField.textProperty().addListener((observable, oldValue, newValue) -> { //TODO: Validation
            try {
                amountToSort = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                // Handle exception when newValue is not a valid integer
            }
        });

        // Add a ChangeListener to delaySlider
        delaySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            delay = newValue.doubleValue();
        });
    }

    /**
     * A method to handle the start button logic.
     */
    @FXML
    private void startButtonClicked() {
        // Create the model and assign an observer of the model, (which is the controller).
        sortingModel = new SortingModel(amountToSort);
        sortingModel.setObserver(this);
        sortingModel.setStepDelay(delay);

        // Generate the bars for visualisation.
        generateBars();

        // Start and visualise sorting.
        PauseTransition delayTransition = new PauseTransition(Duration.millis(500));
        delayTransition.setOnFinished(event -> sortingModel.shuffle());
        delayTransition.play();
    }

    /**
     * A method to update the sort title.
     */
    @FXML
    public void updateSortText() {
        sortText.setText(sortComboBox.getValue().toString());
    }

    /**
     * A method to generate bars based on the numbers held in the model.
     */
    private void generateBars() {

        // Resetting the HBox.
        barBox.getChildren().clear();

        // Getting the numbers from the model.
        double[] array = sortingModel.getArrayToSort();

        // Calculating the maximum-width and maximum-height of all the bars
        // based on how many bars to display.
        double width = barBox.getWidth() / sortingModel.getSize();
        double maxHeight = barBox.getHeight();
        double maxMagnitude = sortingModel.getMaxValue();

        // Generating bars.
        for (int i = 0; i < array.length; i++) {
            // Calculate bar dimension with the restrictions of the view.
            double barHeight = maxHeight * (array[i] / maxMagnitude);
            double x = i * width;
            double y = barBox.getHeight() - barHeight;

            // Generate bar, giving it a colour, and adding to the HBox.
            Rectangle rect = new Rectangle(x, y, width, barHeight);
            rect.setFill(defaultColour);
            barBox.getChildren().add(rect);
        }
    }

    /**
     * A method to handle the onSwap event in {@link SortingModel} through {@link IModelObserver}.
     * It will visualise the swapping of two bars based on the indexes provided.
     *
     * @param index1 The index of one of the parties in the swap.
     * @param index2 The index of one of the parties in the swap.
     */
    @Override
    public void onSwap(int index1, int index2) {
        Platform.runLater(() -> {
            // Validation of the indexes.
            if (index1 < 0 || index2 < 0 || index1 >= barBox.getChildren().size() ||
                    index2 >= barBox.getChildren().size() || index1 == index2) {
                return; // Invalid indices or same indices, do nothing
            }

            // Getting the bars to be swapped based of the indexes.
            Rectangle bar1 = (Rectangle) barBox.getChildren().get(index1);
            Rectangle bar2 = (Rectangle) barBox.getChildren().get(index2);

            // Getting the x position for each bar.
            double layoutX1 = bar1.getLayoutX();
            double layoutX2 = bar2.getLayoutX();

            // Swap the layoutX positions of the rectangles
            bar1.setLayoutX(layoutX2);
            bar2.setLayoutX(layoutX1);

            // Reset color of all bars to default
            for (Node node : barBox.getChildren()) {
                Rectangle rect = (Rectangle) node;
                rect.setFill(defaultColour);
            }

            // Update the barsContainer's children list to reflect the swap
            List<Node> children = new ArrayList<>(barBox.getChildren());
            children.set(index1, bar2);
            children.set(index2, bar1);
            barBox.getChildren().setAll(children);
        });
    }

    /**
     * A method to handle the onCompare event in {@link SortingModel} through {@link IModelObserver}.
     * It will visualise the comparison of two bars based on the indexes provided.
     * @param index1 The index of one of the parties in the comparison.
     * @param index2 The index of one of the parties in the comparison.
     */
    @Override
    public void onCompare(int index1, int index2) {
        Platform.runLater(() -> {
            if (index1 < 0 || index2 < 0 || index1 >= barBox.getChildren().size() ||
                index2 >= barBox.getChildren().size() || index1 == index2) {
                return; // Invalid indices or same indices, do nothing
            }

            // Remove compare color from the last compared bars (if any)
            if (lastComparedIndex1 != -1 && lastComparedIndex2 != -1) {
                Rectangle lastBar1 = (Rectangle) barBox.getChildren().get(lastComparedIndex1);
                Rectangle lastBar2 = (Rectangle) barBox.getChildren().get(lastComparedIndex2);

                lastBar1.setFill(defaultColour);
                lastBar2.setFill(defaultColour);
            }

            // Set compare color to the bars at index1 and index2
            Rectangle bar1 = (Rectangle) barBox.getChildren().get(index1);
            Rectangle bar2 = (Rectangle) barBox.getChildren().get(index2);

            bar1.setFill(compareColour);
            bar2.setFill(compareColour);

            // Update the last compared indices
            lastComparedIndex1 = index1;
            lastComparedIndex2 = index2;
        });
    }

    /**
     * A method to handle the {@link IModelObserver} onShuffleCompletion event.
     * It will introduce a small delay, then start the appropriate sort.
     */
    @Override
    public void onShuffleComplete() {
        System.out.println("Shuffle Complete!"); // Console print out.

        // Delay and start sort.
        PauseTransition delayTransition = new PauseTransition(Duration.millis(500));
        delayTransition.setOnFinished(event -> sortingModel.sort(sortComboBox.getValue()));
        delayTransition.play();
    }
}