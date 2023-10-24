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
    private static final String FXML = "TagLabel.fxml";
    private static final CornerRadii radius = new CornerRadii(3);
    private static final Insets padding = new Insets(1);

    @javafx.fxml.FXML
    private HBox tagLabel;
    @FXML
    private Label value;

    /**
     * Creates a FlowPaneLabel with text {@code value} and default styling.
     */
    public FlowPaneLabel(String value) {
        super(FXML);
        this.value.setText(value);

        styleTagValue();
        styleTagLabel();
    }

    /**
     * Constructs a FlowPaneLabel.
     */
    // TODO: use priority enum to construct, including javadoc header
    public FlowPaneLabel(String value, int i) {
        super(FXML);
        this.value.setText(value);
        stylePriorityLabel(i);
        stylePriorityValue(i);
    }

    private void styleTagLabel() {
        this.tagLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, radius, padding)));
    }

    private void styleTagValue() {
        this.value.setStyle("-fx-text-fill: #000000; -fx-background-color: transparent");
    }

    // TODO: provide styling of label, specifically saying the value of Color
    private void stylePriorityLabel(int i) {
        this.tagLabel.setBackground(new Background(new BackgroundFill(Color.RED, radius, padding)));
    }

    // TODO: provide styling of text, specifically saying the value to -fx-text-fill
    private void stylePriorityValue(int i) {
        this.value.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: transparent");
    }
}
