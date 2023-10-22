package seedu.address.model.priority;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/** Represents a Person's priority level. **/
public class Priority {
    public enum LEVEL {HIGH, MEDIUM, LOW, NONE};

    public static final String HIGH_PRIORITY_KEYWORD = "high";
    public static final String MEDIUM_PRIORITY_KEYWORD = "medium";
    public static final String LOW_PRIORITY_KEYWORD = "low";

    public static final String MESSAGE_CONSTRAINTS = "Priority levels should only be one of: {" +
        HIGH_PRIORITY_KEYWORD + " " + MEDIUM_PRIORITY_KEYWORD + " " + LOW_PRIORITY_KEYWORD + " }";

    private LEVEL level;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        assignPriorityLevel(priority);
    }

    private void assignPriorityLevel(String priority) {
        switch (priority) {
        case HIGH_PRIORITY_KEYWORD:
            this.level = LEVEL.HIGH;
            break;
        case MEDIUM_PRIORITY_KEYWORD:
            this.level = LEVEL.MEDIUM;
            break;
        case LOW_PRIORITY_KEYWORD:
            this.level = LEVEL.LOW;
            break;
        default:
            this.level = LEVEL.NONE;
        }
    }

    /**
     * Checks if the priority given is valid.
     *
     * @param priority to check.
     */
    public static boolean isValidPriority(String priority) {
        return priority.equals(HIGH_PRIORITY_KEYWORD) || priority.equals(MEDIUM_PRIORITY_KEYWORD) ||
                priority.equals(LOW_PRIORITY_KEYWORD) || priority.equals("-");
    }

    /**
     * Returns priority level.
     */
    public LEVEL getPriorityLevel() {
        return this.level;
    }

    @Override
    public String toString() {
        if (this.level == LEVEL.NONE) {
            return "-";
        }
        return this.level.toString().toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Priority)) {
            return false;
        }

        Priority otherPriority = (Priority) other;
        return level.equals(otherPriority.level);
    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }
}
