package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        }
        return keywords.stream()
                .allMatch(keyword -> isFullNameContainsPrefix(person.getName().fullName, keyword));
    }

    /**
     * Checks if the full name contains a word that starts with the given prefix, ignoring case.
     *
     * @param fullName The full name to check.
     * @param prefix The prefix to search for.
     * @return True if any word in the full name starts with the specified prefix, false otherwise.
     */
    public boolean isFullNameContainsPrefix(String fullName, String prefix) {
        String lowerFullName = fullName.toLowerCase();
        String lowerPrefix = prefix.toLowerCase();
        for (String name: lowerFullName.split("\\s+")) {
            if (name.startsWith(lowerPrefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
