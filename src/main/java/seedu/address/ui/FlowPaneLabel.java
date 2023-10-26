package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * A UI component that displays the label within the FlowPane.
 */
public class FlowPaneLabel extends UiPart<Region> {

    public enum Type { TAG, INSURANCE, PRIORITY}
    private static final String FXML = "FlowPaneLabel.fxml";
    private static final CornerRadii radius = new CornerRadii(3);
    private static final Insets padding = new Insets(1);

    @javafx.fxml.FXML
    private HBox flowPaneLabel;
    @FXML
    private Label value;



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

    // TODO: provide styling of label, specifically saying the value of Color
    private void stylePriorityLabel() {
        this.flowPaneLabel.setBackground(new Background(new BackgroundFill(Color.RED, radius, padding)));
    }

    // TODO: provide styling of text, specifically saying the value to -fx-text-fill
    private void stylePriorityValue() {
        this.value.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: transparent");
    }
}
