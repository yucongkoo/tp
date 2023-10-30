package seedu.address.model.person;

import seedu.address.model.insurance.Insurance;

import java.util.List;
import java.util.function.Predicate;

public class InsuranceContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public InsuranceContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        }
        return keywords.stream()
                .allMatch(keyword -> Name.isFullNameContainsPrefix(person.getName(), keyword));
    }

}
