package seedu.address.model.person.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;


/**
 * Tests that a {@code Person}'s {@code Priority} matches all the keywords given.
 */
public class PriorityContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public PriorityContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        }
        return keywords.stream()
                .allMatch(keyword -> Priority.isPriorityContainsPrefix(person.getPriority(), keyword));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PriorityContainsKeywordsPredicate)) {
            return false;
        }

        PriorityContainsKeywordsPredicate otherPriorityContainsKeywordsPredicate =
                (PriorityContainsKeywordsPredicate) other;
        return keywords.equals(otherPriorityContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
