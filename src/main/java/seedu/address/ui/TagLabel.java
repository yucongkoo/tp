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

public class TagLabel extends UiPart<Region> {
    private static final String FXML = "TagLabel.fxml";
    private static final CornerRadii radius = new CornerRadii(3);
    private static final Insets padding = new Insets(1);

    @FXML
    private HBox tagLabel;
    @FXML
    private Label value;

    // temporary i used to determine priority, to be updated
    private int i = -1;

    public TagLabel(String value) {
        super(FXML);
        this.value.setText(value);

        styleTagValue();
        styleTagLabel();
    }

    // temporary, to be updated
    public TagLabel(String value, int i) {
        super(FXML);
        this.value.setText(value);
        this.i = i;
        stylePriorityLabel();
        stylePriorityValue();
    }

    private void styleTagLabel() {
        this.tagLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, radius, padding)));
    }

    private void styleTagValue() {
        this.value.setStyle("-fx-text-fill: #000000; -fx-background-color: transparent");
    }

    // temporary, to be updated
    private void stylePriorityLabel() {
        this.tagLabel.setBackground(new Background(new BackgroundFill(Color.RED, radius, padding)));
    }

    // temporary, to be updated
    private void stylePriorityValue() {
        this.value.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: transparent");
    }
}
