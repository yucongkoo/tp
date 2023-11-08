package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    private static final NameContainsKeywordsPredicate NAME_PREDICATE =
            new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
    private static final AddressContainsKeywordsPredicate ADDRESS_PREDICATE =
            new AddressContainsKeywordsPredicate(Arrays.asList("Main", "Street"));
    private static final EmailContainsKeywordsPredicate EMAIL_PREDICATE =
            new EmailContainsKeywordsPredicate(Arrays.asList("alice@gmail.com"));
    private static final InsuranceContainsKeywordsPredicate INSURANCE_PREDICATE =
            new InsuranceContainsKeywordsPredicate(Arrays.asList("Great", "Eastern"));
    private static final PhoneContainsKeywordsPredicate PHONE_PREDICATE =
            new PhoneContainsKeywordsPredicate(Arrays.asList("12345678"));
    private static final PriorityContainsKeywordsPredicate PRIORITY_PREDICATE =
            new PriorityContainsKeywordsPredicate(Arrays.asList("high"));
    private static final RemarkContainsKeywordsPredicate REMARK_PREDICATE =
            new RemarkContainsKeywordsPredicate(Arrays.asList("Some", "remarks"));
    private static final TagContainsKeywordsPredicate TAG_PREDICATE =
            new TagContainsKeywordsPredicate(Arrays.asList("Friend"));
    private static final List<String> WRONG_KEYWORD_LIST = Arrays.asList("Wrong", "Keywords");

    private static final Person PERSON_STUB = new PersonBuilder()
            .withAddress("Main Street")
            .withName("Alice Bob")
            .withInsurances("Great Eastern")
            .withEmail("alice@gmail.com")
            .withPriority("high")
            .withPhone("12345678")
            .withRemark("Some Remarks")
            .withTags("Friend")
            .build();
    @Test
    public void equals() {
        List<Predicate<Person>> firstPredicateList =
                Collections.singletonList(new NameContainsKeywordsPredicate(Collections.singletonList("first")));
        List<Predicate<Person>> secondPredicateList = Arrays.asList(NAME_PREDICATE, REMARK_PREDICATE);

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(firstPredicateList);
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(secondPredicateList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(firstPredicateList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_allPredicateReturnTrue_returnsTrue() {
        // One Predicate
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList(NAME_PREDICATE));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(ADDRESS_PREDICATE));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(EMAIL_PREDICATE));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@gmail.com").build()));

        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(ADDRESS_PREDICATE));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(INSURANCE_PREDICATE));
        assertTrue(predicate.test(new PersonBuilder().withInsurances("Great Eastern").build()));

        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PHONE_PREDICATE));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(PRIORITY_PREDICATE));
        assertTrue(predicate.test(new PersonBuilder().withPriority("high").build()));

        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(REMARK_PREDICATE));
        assertTrue(predicate.test(new PersonBuilder().withRemark("Some Remarks").build()));

        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(TAG_PREDICATE));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend").build()));

        // Multiple predicates
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList(
                ADDRESS_PREDICATE, EMAIL_PREDICATE, INSURANCE_PREDICATE, NAME_PREDICATE,
                PHONE_PREDICATE, PRIORITY_PREDICATE, REMARK_PREDICATE, TAG_PREDICATE
        ));
        assertTrue(predicate.test(PERSON_STUB));
    }

    @Test
    public void test_somePredicatesRetuenFalse_returnsFalse() {
        // Zero Predicate
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));


        // Wrong Address Predicate
        predicate = predicate = new PersonContainsKeywordsPredicate(Arrays.asList(
                new AddressContainsKeywordsPredicate(WRONG_KEYWORD_LIST),
                EMAIL_PREDICATE, INSURANCE_PREDICATE, NAME_PREDICATE,
                PHONE_PREDICATE, PRIORITY_PREDICATE, REMARK_PREDICATE, TAG_PREDICATE
        ));
        assertFalse(predicate.test(PERSON_STUB));

        // Wrong Email Predicate
        predicate = predicate = new PersonContainsKeywordsPredicate(Arrays.asList(
                new EmailContainsKeywordsPredicate(WRONG_KEYWORD_LIST),
                ADDRESS_PREDICATE, INSURANCE_PREDICATE, NAME_PREDICATE,
                PHONE_PREDICATE, PRIORITY_PREDICATE, REMARK_PREDICATE, TAG_PREDICATE
        ));
        assertFalse(predicate.test(PERSON_STUB));

        // Wrong Insurance Predicate
        predicate = predicate = new PersonContainsKeywordsPredicate(Arrays.asList(
                new InsuranceContainsKeywordsPredicate(WRONG_KEYWORD_LIST),
                ADDRESS_PREDICATE, EMAIL_PREDICATE, NAME_PREDICATE,
                PHONE_PREDICATE, PRIORITY_PREDICATE, REMARK_PREDICATE, TAG_PREDICATE
        ));
        assertFalse(predicate.test(PERSON_STUB));

        // Wrong Name Predicate
        predicate = predicate = new PersonContainsKeywordsPredicate(Arrays.asList(
                new NameContainsKeywordsPredicate(WRONG_KEYWORD_LIST),
                ADDRESS_PREDICATE, INSURANCE_PREDICATE, EMAIL_PREDICATE,
                PHONE_PREDICATE, PRIORITY_PREDICATE, REMARK_PREDICATE, TAG_PREDICATE
        ));
        assertFalse(predicate.test(PERSON_STUB));

        // Wrong Email Predicate
        predicate = predicate = new PersonContainsKeywordsPredicate(Arrays.asList(
                new PhoneContainsKeywordsPredicate(WRONG_KEYWORD_LIST),
                ADDRESS_PREDICATE, INSURANCE_PREDICATE, NAME_PREDICATE,
                EMAIL_PREDICATE, PRIORITY_PREDICATE, REMARK_PREDICATE, TAG_PREDICATE
        ));
        assertFalse(predicate.test(PERSON_STUB));

        // Wrong Email Predicate
        predicate = predicate = new PersonContainsKeywordsPredicate(Arrays.asList(
                new PriorityContainsKeywordsPredicate(WRONG_KEYWORD_LIST),
                ADDRESS_PREDICATE, INSURANCE_PREDICATE, NAME_PREDICATE,
                PHONE_PREDICATE, EMAIL_PREDICATE, REMARK_PREDICATE, TAG_PREDICATE
        ));
        assertFalse(predicate.test(PERSON_STUB));

        // Wrong Email Predicate
        predicate = predicate = new PersonContainsKeywordsPredicate(Arrays.asList(
                new RemarkContainsKeywordsPredicate(WRONG_KEYWORD_LIST),
                ADDRESS_PREDICATE, INSURANCE_PREDICATE, NAME_PREDICATE,
                PHONE_PREDICATE, PRIORITY_PREDICATE, EMAIL_PREDICATE, TAG_PREDICATE
        ));
        assertFalse(predicate.test(PERSON_STUB));

        // Wrong Email Predicate
        predicate = predicate = new PersonContainsKeywordsPredicate(Arrays.asList(
                new TagContainsKeywordsPredicate(WRONG_KEYWORD_LIST),
                ADDRESS_PREDICATE, INSURANCE_PREDICATE, NAME_PREDICATE,
                PHONE_PREDICATE, PRIORITY_PREDICATE, REMARK_PREDICATE, EMAIL_PREDICATE
        ));
        assertFalse(predicate.test(PERSON_STUB));
    }
}
