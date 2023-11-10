package seedu.address.model.priority;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.priority.Level.HIGH;
import static seedu.address.model.priority.Level.LOW;
import static seedu.address.model.priority.Level.MEDIUM;
import static seedu.address.model.priority.Level.NONE;

import java.util.HashMap;
import java.util.HashSet;

/** Represents a Person's priority level. **/
public class Priority {

    public static final String HIGH_PRIORITY_KEYWORD = "high";
    public static final String MEDIUM_PRIORITY_KEYWORD = "medium";
    public static final String LOW_PRIORITY_KEYWORD = "low";
    public static final String NONE_PRIORITY_KEYWORD = "-";

    /**
     * Stores the valid priority keywords.
     **/
    public static final HashSet<String> VALID_PRIORITY_KEYWORDS = new HashSet<>() {
        {
            add(HIGH_PRIORITY_KEYWORD);
            add(MEDIUM_PRIORITY_KEYWORD);
            add(LOW_PRIORITY_KEYWORD);
            add(NONE_PRIORITY_KEYWORD);
        }
    };

    /**
     * Stores the priority levels and their respective keywords as key-value pairs.
     **/
    public static final HashMap<Level, String> LEVEL_STRING_MAP = new HashMap<>() {
        {
            put(HIGH, HIGH_PRIORITY_KEYWORD);
            put(MEDIUM, MEDIUM_PRIORITY_KEYWORD);
            put(LOW, LOW_PRIORITY_KEYWORD);
            put(NONE, NONE_PRIORITY_KEYWORD);
        }
    };

    public static final String MESSAGE_CONSTRAINTS = "Priority levels should only be one of: { "
            + HIGH_PRIORITY_KEYWORD + ", " + MEDIUM_PRIORITY_KEYWORD + ", " + LOW_PRIORITY_KEYWORD + ", "
            + NONE_PRIORITY_KEYWORD + " }";

    private Level level;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        this.level = parsePriorityLevel(priority);
    }

    /**
     * Parses the {@code priority} and returns the responding {@code Level}.
     */
    public static Level parsePriorityLevel(String priority) {
        requireNonNull(priority);
        assert isValidPriority(priority);

        switch (priority) {
        case HIGH_PRIORITY_KEYWORD:
            return HIGH;
        case MEDIUM_PRIORITY_KEYWORD:
            return MEDIUM;
        case LOW_PRIORITY_KEYWORD:
            return LOW;
        default:
            return NONE;
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

    /**
     * Checks if the priority level starts with the given prefix, ignoring case.
     *
     * @param prefix The prefix to search for.
     * @return True if the priority level starts with the specified prefix, false otherwise.
     */
    public static boolean isPriorityContainsPrefix(Priority priority, String prefix) {
        if (priority.level.equals(NONE)) {
            return false;
        }
        String lowerCasePriority = priority.toString().toLowerCase();
        return lowerCasePriority.startsWith(prefix.toLowerCase());
    }
}
