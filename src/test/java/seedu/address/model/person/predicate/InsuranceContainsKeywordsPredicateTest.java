package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class InsuranceContainsKeywordsPredicateTest {

    private static final String[] INSURANCE_STUB = new String[]{
        "Great Eastern", "Best Insurance", "IQInsurance"
    };
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        InsuranceContainsKeywordsPredicate firstPredicate =
                new InsuranceContainsKeywordsPredicate(firstPredicateKeywordList);
        InsuranceContainsKeywordsPredicate secondPredicate =
                new InsuranceContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InsuranceContainsKeywordsPredicate firstPredicateCopy =
                new InsuranceContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different  -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_insuranceContainsKeywordsAsPrefix_returnsTrue() {
        // One Keyword
        InsuranceContainsKeywordsPredicate predicate =
                new InsuranceContainsKeywordsPredicate(Collections.singletonList("IQInsurance"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("IQ"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        // Multiple keywords
        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("Great", "Eastern"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("IQ", "East"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        // Only one matching keyword
        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("Great"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("Eas"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        // Mixed-case keywords
        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("grEAt", "eAsTeRN"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("beS", "eAS"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        // Keywords match but not in order
        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("Insurance", "Best"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("Eas", "Grea"));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        // Empty Keyword
        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList(""));
        assertTrue(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));
    }

    @Test
    public void test_insuranceDoesNotContainKeywordsAsPrefix_returnsFalse() {
        // Zero keywords
        InsuranceContainsKeywordsPredicate predicate =
                new InsuranceContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        // Non-matching keyword
        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("Life"));
        assertFalse(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("Li"));
        assertFalse(predicate.test(new PersonBuilder().withInsurances().build()));


        // Multiple keywords
        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("Great", "Car"));
        assertFalse(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("IQ", "Din", "Gr"));
        assertFalse(predicate.test(new PersonBuilder().withInsurances(INSURANCE_STUB).build()));

        // Keywords match phone, name, email and address, but does not match insurance
        predicate = new InsuranceContainsKeywordsPredicate(Arrays.asList("12345678", "alice@email.com",
                "Alice", "Main Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").withInsurances(INSURANCE_STUB).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        InsuranceContainsKeywordsPredicate predicate = new InsuranceContainsKeywordsPredicate(keywords);

        String expected = InsuranceContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
