package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RemarkContainsKeywordsPredicateTest {

    private static final String REMARK_STUB = "Some Remarks...";
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RemarkContainsKeywordsPredicate firstPredicate =
                new RemarkContainsKeywordsPredicate(firstPredicateKeywordList);
        RemarkContainsKeywordsPredicate secondPredicate =
                new RemarkContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RemarkContainsKeywordsPredicate firstPredicateCopy =
                new RemarkContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_remarkContainsKeywordsAsPrefix_returnsTrue() {
        // One Keyword
        RemarkContainsKeywordsPredicate predicate =
                new RemarkContainsKeywordsPredicate(Collections.singletonList("Some"));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Rema"));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));


        // Multiple keywords
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Some", "Remarks..."));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("So", "Re"));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        // Only one matching keyword
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Some"));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Re"));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        // Mixed-case keywords
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("sOMe", "rEMaRks..."));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("sOM", "rEM"));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        // Keywords match but there are some words between the keywords
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Remarks...", "Some"));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Rem", "S"));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        // Empty Keyword
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList(""));
        assertTrue(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));
    }

    @Test
    public void test_remarkDoesNotContainKeywordsAsPrefix_returnsFalse() {
        // Zero keywords
        RemarkContainsKeywordsPredicate predicate = new RemarkContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        // Non-matching keyword
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Apple"));
        assertFalse(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Ap"));
        assertFalse(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));


        // Multiple keywords
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Some", "Apple", "Eat"));
        assertFalse(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("Som", "Re", "Mark"));
        assertFalse(predicate.test(new PersonBuilder().withRemark(REMARK_STUB).build()));

        // Keywords match phone, email, name and address, but does not match remark
        predicate = new RemarkContainsKeywordsPredicate(Arrays.asList("12345678", "alice@email.com",
                "Alice", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").withRemark(REMARK_STUB).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        RemarkContainsKeywordsPredicate predicate = new RemarkContainsKeywordsPredicate(keywords);

        String expected = RemarkContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
