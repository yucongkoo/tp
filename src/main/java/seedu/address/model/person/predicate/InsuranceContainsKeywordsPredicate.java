package seedu.address.model.person.predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.Set;
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
                .allMatch(keyword -> isInsuranceContainsPrefix(person.getInsurances(), keyword));
    }

    private static boolean isInsuranceContainsPrefix(Set<Insurance> insurances, String prefix) {
        return insurances.stream()
                .anyMatch(insurance -> Insurance.isContainsPrefix(prefix, insurance));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InsuranceContainsKeywordsPredicate)) {
            return false;
        }

        InsuranceContainsKeywordsPredicate otherInsuranceContainsKeywordsPredicate =
                (InsuranceContainsKeywordsPredicate) other;
        return keywords.equals(otherInsuranceContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
