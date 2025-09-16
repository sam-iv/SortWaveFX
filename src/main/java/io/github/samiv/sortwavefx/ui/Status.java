package io.github.samiv.sortwavefx.ui;

/**
 * An enumeration to define the status the visualisation can display to the user. Used by {@link MainController} to
 * update the text elements in the UI.
 */
public enum Status {
    IDLE("Idle"),
    SHUFFLING("Shuffling"),
    SORTING("Sorting");

    private final String statusName;

    Status(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() { return this.statusName; }
}
