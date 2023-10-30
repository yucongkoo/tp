package seedu.address.ui;

import static seedu.address.model.priority.Priority.HIGH_PRIORITY_KEYWORD;
import static seedu.address.model.priority.Priority.LOW_PRIORITY_KEYWORD;
import static seedu.address.model.priority.Priority.MEDIUM_PRIORITY_KEYWORD;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.priority.Priority.Level;

/**
 * A UI component that displays the label within the FlowPane.
 */
public class FlowPaneLabel extends UiPart<Region> {

    /**
     * To differentiate the label for {@code Tag}, {@code Insurance}, {@code Priority}
     */
    public enum Type { TAG, INSURANCE, PRIORITY }
    private static final String FXML = "FlowPaneLabel.fxml";
    private static final CornerRadii radius = new CornerRadii(3);
    private static final Insets padding = new Insets(1);

    @javafx.fxml.FXML
    private HBox flowPaneLabel;
    @FXML
    private Label value;
    private Level priorityLevel;



    /**
     * Creates a FlowPaneLabel with text {@code value} and default styling.
     */
    public FlowPaneLabel(String value, Type type) {
        super(FXML);
        this.value.setText(value);

        switch (type) {
        case TAG:
            styleTag();
            break;

        case INSURANCE:
            styleInsurance();
            break;

        case PRIORITY:
            this.value.setText("Priority." + value);
            assign_level(value);
            stylePriority();
            break;
        default:
        }
    }

    private void styleTag() {
        styleTagValue();
        styleTagLabel();
    }
    private void styleTagLabel() {
        this.flowPaneLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, radius, padding)));
    }

    private void styleTagValue() {
        this.value.setStyle("-fx-text-fill: #000000; -fx-background-color: transparent");
    }

    private void styleInsurance() {
        this.value.setStyle("-fx-text-fill: #000000; -fx-background-color: transparent");
        this.flowPaneLabel.setBackground(new Background(new BackgroundFill(Color.PALETURQUOISE, radius, padding)));
    }

    private void stylePriority() {
        stylePriorityLabel();
        stylePriorityValue();
    }

    private void stylePriorityLabel() {
        if (this.priorityLevel == Level.HIGH) {
            this.flowPaneLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, radius, padding)));
        } else if (this.priorityLevel == Level.MEDIUM) {
            this.flowPaneLabel.setBackground(new Background(new BackgroundFill(Color.ORANGE, radius, padding)));
        } else if (this.priorityLevel == Level.LOW) {
            this.flowPaneLabel.setBackground(new Background(new BackgroundFill(Color.RED, radius, padding)));
        }
    }

    private void stylePriorityValue() {
        this.value.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: transparent");
    }

    private void assign_level(String value) {
        if (value.equals(HIGH_PRIORITY_KEYWORD)) {
            this.priorityLevel = Level.HIGH;
        } else if (value.equals(MEDIUM_PRIORITY_KEYWORD)) {
            this.priorityLevel = Level.MEDIUM;
        } else if (value.equals(LOW_PRIORITY_KEYWORD)) {
            this.priorityLevel = Level.LOW;
        } else {
            this.priorityLevel = null;
        }
    }
}
