package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PriorityContainsKeywordsPredicateTest {

    private static final String PRIORITY_STUB = "high";


    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PriorityContainsKeywordsPredicate firstPredicate =
                new PriorityContainsKeywordsPredicate(firstPredicateKeywordList);
        PriorityContainsKeywordsPredicate secondPredicate =
                new PriorityContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PriorityContainsKeywordsPredicate firstPredicateCopy =
                new PriorityContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different  -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_priorityContainsKeywordsAsPrefix_returnsTrue() {
        // One Keyword
        PriorityContainsKeywordsPredicate predicate =
                new PriorityContainsKeywordsPredicate(Collections.singletonList("high"));
        assertTrue(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));

        predicate = new PriorityContainsKeywordsPredicate(Arrays.asList("hi"));
        assertTrue(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));

        // Multiple keywords
        predicate = new PriorityContainsKeywordsPredicate(Arrays.asList("high", "hi"));
        assertTrue(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));

        // Mixed-case keywords
        predicate = new PriorityContainsKeywordsPredicate(Arrays.asList("hIGh"));
        assertTrue(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));

        predicate = new PriorityContainsKeywordsPredicate(Arrays.asList("HI"));
        assertTrue(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));

        // Empty keywords
        predicate = new PriorityContainsKeywordsPredicate(Arrays.asList(""));
        assertTrue(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));
    }

    @Test
    public void test_priorityDoesNotContainKeywordsAsPrefix_returnsFalse() {
        // Zero keywords
        PriorityContainsKeywordsPredicate predicate = new PriorityContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));

        // Non-matching keyword
        predicate = new PriorityContainsKeywordsPredicate(Arrays.asList("low"));
        assertFalse(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));

        predicate = new PriorityContainsKeywordsPredicate(Arrays.asList("median"));
        assertFalse(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));


        // Multiple keywords
        predicate = new PriorityContainsKeywordsPredicate(Arrays.asList("high", "low"));
        assertFalse(predicate.test(new PersonBuilder().withPriority(PRIORITY_STUB).build()));

        // Keywords match name, phone, email and address, but does not match priority
        predicate = new PriorityContainsKeywordsPredicate(Arrays.asList("12345678", "Main Street", "Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").withPriority(PRIORITY_STUB).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PriorityContainsKeywordsPredicate predicate = new PriorityContainsKeywordsPredicate(keywords);

        String expected = PriorityContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
