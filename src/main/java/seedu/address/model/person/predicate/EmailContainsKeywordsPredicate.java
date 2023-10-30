package seedu.address.model.person.predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class EmailContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        }
        return keywords.stream()
                .allMatch(keyword -> Email.isEmailContainsPrefix(person.getEmail(), keyword));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsKeywordsPredicate)) {
            return false;
        }

        EmailContainsKeywordsPredicate otherEmailContainsKeywordsPredicate =
                (EmailContainsKeywordsPredicate) other;
        return keywords.equals(otherEmailContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
