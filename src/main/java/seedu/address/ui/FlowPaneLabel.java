package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.Insurance;
import seedu.address.model.person.Tag;
import seedu.address.model.priority.Level;
import seedu.address.model.priority.Priority;

/**
 * A UI component that displays the label within the FlowPane.
 */
public class FlowPaneLabel extends UiPart<Region> {

    /**
     * To differentiate the label for {@code Tag}, {@code Insurance}, {@code Priority}
     */
    public enum Type { TAG, INSURANCE, PRIORITY }

    private static final String FXML = "FlowPaneLabel.fxml";

    private static final CornerRadii RADIUS = new CornerRadii(3);
    private static final Insets PADDING = new Insets(1);

    // background colors
    private static final Color COLOR_BACKGROUND_TAG = Color.LIGHTBLUE;
    private static final Color COLOR_BACKGROUND_INSURANCE = Color.rgb(233, 190, 255);
    private static final Color COLOR_BACKGROUND_PRIORITY_HIGH = Color.GREEN;
    private static final Color COLOR_BACKGROUND_PRIORITY_MEDIUM = Color.ORANGE;
    private static final Color COLOR_BACKGROUND_PRIORITY_LOW = Color.RED;

    // text colors
    private static final String COLOR_TEXT_TAG = "#000000";
    private static final String COLOR_TEXT_INSURANCE = "#000000";
    private static final String COLOR_TEXT_PRIORITY = "#FFFFFF";

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

    private static Background getBackgroundWithColor(Color color) {
        requireNonNull(color);

        return new Background(new BackgroundFill(color, RADIUS, PADDING));
    }

    private static String getTextStylingWithColor(String colorInHexadecimal) {
        requireNonNull(colorInHexadecimal);

        return String.format("-fx-text-fill: %s; -fx-background-color: transparent", colorInHexadecimal);
    }

    /**
     * Creates and returns a {@code FlowPaneLabel} styled accordingly for {@code Tag tag}.
     */
    public static FlowPaneLabel createFlowPaneLabel(Tag tag) {
        requireNonNull(tag);

        return new FlowPaneLabel(getTextForDisplayForTag(tag), COLOR_TEXT_TAG, COLOR_BACKGROUND_TAG);
    }

    /**
     * Creates and returns a {@code FlowPaneLabel} styled accordingly for {@code Insurance insurance}.
     */
    public static FlowPaneLabel createFlowPaneLabel(Insurance insurance) {
        requireNonNull(insurance);

        return new FlowPaneLabel(getTextForDisplayForInsurance(insurance),
                COLOR_TEXT_INSURANCE, COLOR_BACKGROUND_INSURANCE);
    }

    /**
     * Creates and returns a {@code FlowPaneLabel} styled accordingly for {@code Priority priority}.
     */
    public static FlowPaneLabel createFlowPaneLabel(Priority priority) {
        requireNonNull(priority);

        return new FlowPaneLabel(getTextForDisplayForPriority(priority),
                COLOR_TEXT_PRIORITY, getPriorityBackgroundColor(priority));
    }

    private static String getTextForDisplayForTag(Tag tag) {
        requireNonNull(tag);

        String text = tag.getTagName();
        return "[ t ] " + text;
    }

    private static String getTextForDisplayForInsurance(Insurance insurance) {
        requireNonNull(insurance);

        String text = insurance.getInsuranceName();
        return "[ i ] " + text;
    }

    private static String getTextForDisplayForPriority(Priority priority) {
        requireNonNull(priority);

        String text = priority.toString();
        return "Priority." + text;
    }

    private static Color getPriorityBackgroundColor(Priority priority) {
        requireNonNull(priority);

        Level priorityLevel = priority.getPriorityLevel();

        switch (priorityLevel) {
        case HIGH:
            return COLOR_BACKGROUND_PRIORITY_HIGH;
        case MEDIUM:
            return COLOR_BACKGROUND_PRIORITY_MEDIUM;
        case LOW:
            return COLOR_BACKGROUND_PRIORITY_LOW;
        case NONE:
            // should not reach here
            throw new IllegalArgumentException("Should not have a FlowPaneLabel for PrioritiyLevel.NONE.");
        default:
            // should not reach here
            throw new IllegalArgumentException("Switch case for enum Level reached default branch.");
        }
    }
}
