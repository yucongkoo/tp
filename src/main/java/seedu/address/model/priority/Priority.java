package seedu.address.model.priority;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/** Represents a Person's priority level. **/
public class Priority {
    /** Possible priorities level. **/
    public enum Level { HIGH, MEDIUM, LOW, NONE };

    public static final String HIGH_PRIORITY_KEYWORD = "high";
    public static final String MEDIUM_PRIORITY_KEYWORD = "medium";
    public static final String LOW_PRIORITY_KEYWORD = "low";
    public static final String NONE_PRIORITY_KEYWORD = "-";
    public static final HashSet<String> VALID_PRIORITY_KEYWORDS = new HashSet<>() {
        {
            add(HIGH_PRIORITY_KEYWORD);
            add(MEDIUM_PRIORITY_KEYWORD);
            add(LOW_PRIORITY_KEYWORD);
            add(NONE_PRIORITY_KEYWORD);
        }
    };

    public static final HashMap<Level, String> LEVEL_STRING_MAP = new HashMap<>() {
        {
            put(Level.HIGH, HIGH_PRIORITY_KEYWORD);
            put(Level.MEDIUM, MEDIUM_PRIORITY_KEYWORD);
            put(Level.LOW, LOW_PRIORITY_KEYWORD);
            put(Level.NONE, NONE_PRIORITY_KEYWORD);
        }
    };

    public static final String MESSAGE_CONSTRAINTS = "Priority levels should only be one of: { "
        + HIGH_PRIORITY_KEYWORD + " " + MEDIUM_PRIORITY_KEYWORD + " " + LOW_PRIORITY_KEYWORD + " }";

    private Level level;

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
            this.level = Level.HIGH;
            break;
        case MEDIUM_PRIORITY_KEYWORD:
            this.level = Level.MEDIUM;
            break;
        case LOW_PRIORITY_KEYWORD:
            this.level = Level.LOW;
            break;
        default:
            this.level = Level.NONE;
        }
    }

    /**
     * Checks if the priority given is valid.
     *
     * @param priority to check.
     */
    public static boolean isValidPriority(String priority) {
        return VALID_PRIORITY_KEYWORDS.contains(priority);
    }

    /**
     * Returns priority level.
     */
    public Level getPriorityLevel() {
        return this.level;
    }

    @Override
    public String toString() {
        return LEVEL_STRING_MAP.get(this.level);
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
