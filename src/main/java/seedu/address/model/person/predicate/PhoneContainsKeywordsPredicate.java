package seedu.address.model.person.predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class PhoneContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        }
        return keywords.stream()
                .allMatch(keyword -> Phone.isPhoneContainsPrefix(person.getPhone(), keyword));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsKeywordsPredicate)) {
            return false;
        }

        PhoneContainsKeywordsPredicate otherPhoneContainsKeywordsPredicate =
                (PhoneContainsKeywordsPredicate) other;
        return keywords.equals(otherPhoneContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
