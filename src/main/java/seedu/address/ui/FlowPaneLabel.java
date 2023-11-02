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
import seedu.address.model.priority.Priority;
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

    // background colors
    private static final Color tagBackgroundColor = Color.LIGHTBLUE;
    private static final Color insuranceBackgroundColor = Color.rgb(233, 190, 255);
    private static final Color highPriorityBackgroundColor = Color.GREEN;
    private static final Color mediumPriorityBackgroundColor = Color.ORANGE;
    private static final Color lowPriorityBackgroundColor = Color.RED;

    private static final String tagTextColorInHexadecimal = "#000000";
    private static final String insuranceTextColorInHexadecimal = "#000000";
    private static final String priorityTextColorInHexadecimal = "#FFFFFF";

    @FXML
    private HBox flowPaneLabel;
    @FXML
    private Label value;

    private FlowPaneLabel(String text, String textColorInHexadecimal, Color backgroundColor) {
        super(FXML);

        value.setText(text);
        value.setStyle(getTextStylingWithColor(textColorInHexadecimal));

        flowPaneLabel.setBackground(getBackgroundWithColor(backgroundColor));
    }

    /**
     * Creates and returns a {@code FlowPaneLabel} styled according to the {@code Type} and {@code text}.
     */
    public static FlowPaneLabel createFlowPaneLabel(Type type, String text) {
        switch (type) {
        case TAG:
            return new FlowPaneLabel(getTextToDisplayForType(text, type),
                    tagTextColorInHexadecimal, tagBackgroundColor);
        case INSURANCE:
            return new FlowPaneLabel(getTextToDisplayForType(text, type),
                    insuranceTextColorInHexadecimal, insuranceBackgroundColor);
        case PRIORITY:
            return new FlowPaneLabel(getTextToDisplayForType(text, type),
                    priorityTextColorInHexadecimal, getPriorityBackgroundColor(text));
        default:
            // should not reach here
            throw new IllegalArgumentException("Switch case for enum Type reached default branch.");
        }
    }

    private static Background getBackgroundWithColor(Color color) {
        return new Background(new BackgroundFill(color, radius, padding));
    }

    private static String getTextStylingWithColor(String colorInHexadecimal) {
        return String.format("-fx-text-fill: %s; -fx-background-color: transparent", colorInHexadecimal);
    }

    private static String getTextToDisplayForType(String text, Type type) {
        switch (type) {
        case TAG:
            return "[ t ] " + text;
        case INSURANCE:
            return "[ i ] " + text;
        case PRIORITY:
            return "Priority." + text;
        default:
            // should not reach here
            throw new IllegalArgumentException("Switch case for enum Type reached default branch.");
        }
    }

    private static Color getPriorityBackgroundColor(String priority) {
        Level priorityLevel = Priority.parsePriorityLevel(priority);

        switch (priorityLevel) {
        case HIGH:
            return highPriorityBackgroundColor;
        case MEDIUM:
            return mediumPriorityBackgroundColor;
        case LOW:
            return lowPriorityBackgroundColor;
        case NONE:
            // should not reach here
            throw new IllegalArgumentException("Should not have a FlowPaneLabel for PrioritiyLevel.NONE.");
        default:
            // should not reach here
            throw new IllegalArgumentException("Switch case for enum Level reached default branch.");
        }
    }
}
