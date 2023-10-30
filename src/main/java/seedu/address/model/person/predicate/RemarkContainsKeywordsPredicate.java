package seedu.address.model.person.predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

import java.util.List;
import java.util.function.Predicate;

public class RemarkContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public RemarkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        }
        return keywords.stream()
                .allMatch(keyword -> Remark.isRemarkContainsPrefix(person.getRemark(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkContainsKeywordsPredicate)) {
            return false;
        }

        RemarkContainsKeywordsPredicate otherRemarkContainsKeywordsPredicate =
                (RemarkContainsKeywordsPredicate) other;
        return keywords.equals(otherRemarkContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
