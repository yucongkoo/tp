package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS =
            "Tags names should be alphanumeric and not be empty nor longer than 20 characters.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+(\\s+\\p{Alnum}+)*";
    public static final int MAXIMUM_TAG_LENGTH = 20;
    public static final int MAXIMUM_TAGS_PER_PERSON = 10;

    private final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        String trimmedTagName = tagName.trim();
        checkArgument(isValidTagName(trimmedTagName), MESSAGE_CONSTRAINTS);
        this.tagName = trimmedTagName;
    }

    public String getTagName() {
        return tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        requireNonNull(test);
        return test.length() <= MAXIMUM_TAG_LENGTH && test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

    /**
     * Checks if the tag name contains a word that starts with the given prefix, ignoring case.
     *
     * @param prefix The prefix to search for.
     * @return True if any word in the tag name starts with the specified prefix, false otherwise.
     */
    public static boolean isTagContainsPrefix(Tag tag, String prefix) {
        String lowerTagName = tag.tagName.toLowerCase();
        String lowerPrefix = prefix.toLowerCase();
        for (String lowerNamePart: lowerTagName.split("\\s+")) {
            if (lowerNamePart.startsWith(lowerPrefix)) {
                return true;
            }
        }
        return false;
    }
}
