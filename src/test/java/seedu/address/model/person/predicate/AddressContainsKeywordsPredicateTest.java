package seedu.address.model.person.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different  -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_addressContainsKeywordsAsPrefix_returnsTrue() {
        // One Keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Singapore"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Singapore").build()));

        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Sing"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Singapore").build()));

        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Jurong", "West"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West").build()));

        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("J", "W", "Cen"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West Center").build()));

        // Only one matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West Recreational Park").build()));

        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Recrea"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West Recreational Park").build()));

        // Mixed-case keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("juRoNg", "weST"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West Recreational Park").build()));

        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("juR", "wES"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West Recreational Park").build()));

        // Keywords match but not in order
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Park", "Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West Recreational Park").build()));

        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Jur", "Rec"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Jurong West Recreational Park").build()));

        // Empty Keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList(""));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Singapore").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywordsAsPrefix_returnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Singapore").build()));

        // Empty Address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList(""));
        assertFalse(predicate.test(new PersonBuilder().withoutAddress().build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Jurong"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Chang Yi").build()));

        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("jur"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Chang Yi").build()));


        // Multiple keywords
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Jurong", "East"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Jurong West").build()));

        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Juro", "Park", "Ea"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Jurong West Park").build()));

        // Keywords match phone, email and name, but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("12345678", "alice@email.com", "Alice"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
