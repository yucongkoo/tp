package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    private static final String[] Tag_STUB = new String[]{
        "Male", "Best Friend", "Old People"
    };
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different  -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywordsAsPrefix_returnsTrue() {
        // One Keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("Male"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Be"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Old", "Friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Fri", "Ma"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Ol"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("fRienD", "peOPlE"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        predicate = new TagContainsKeywordsPredicate(Arrays.asList("oL", "maL"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        // Keywords match but not in order
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("People", "Old"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Peo", "Ol"));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        // Empty Keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList(""));
        assertTrue(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));
    }

    @Test
    public void test_tagDoesNotContainKeywordsAsPrefix_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Girl"));
        assertFalse(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Wrong"));
        assertFalse(predicate.test(new PersonBuilder().withTags().build()));


        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Old", "What"));
        assertFalse(predicate.test(new PersonBuilder().withTags(Tag_STUB).build()));

        // Keywords match phone, name, email and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345678", "alice@email.com",
                "Alice", "Main Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").withTags(Tag_STUB).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
