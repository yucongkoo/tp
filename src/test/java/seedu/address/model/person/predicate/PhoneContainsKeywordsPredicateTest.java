package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    private static final String PHONE_STUB = "12345678";
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different  -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywordsAsPrefix_returnsTrue() {
        // One Keyword
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("12345678"));
        assertTrue(predicate.test(new PersonBuilder().withPhone(PHONE_STUB).build()));

        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("1234"));
        assertTrue(predicate.test(new PersonBuilder().withPhone(PHONE_STUB).build()));

        // Multiple keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("1234", "12"));
        assertTrue(predicate.test(new PersonBuilder().withPhone(PHONE_STUB).build()));

        // Empty Keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList(""));
        assertTrue(predicate.test(new PersonBuilder().withPhone(PHONE_STUB).build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywordsAsPrefix_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone(PHONE_STUB).build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("87654321"));
        assertFalse(predicate.test(new PersonBuilder().withPhone(PHONE_STUB).build()));

        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("8765"));
        assertFalse(predicate.test(new PersonBuilder().withPhone(PHONE_STUB).build()));


        // Multiple keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("12345678", "87654321"));
        assertFalse(predicate.test(new PersonBuilder().withPhone(PHONE_STUB).build()));

        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("12345", "00012"));
        assertFalse(predicate.test(new PersonBuilder().withPhone(PHONE_STUB).build()));

        // Keywords match name, email and address, but does not match phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("Main Street", "Alice", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(keywords);

        String expected = PhoneContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
