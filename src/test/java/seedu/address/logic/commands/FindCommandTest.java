package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_NO_PERSON_FOUND;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.JOJO;
import static seedu.address.testutil.TypicalPersons.KAKA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PersonContainsKeywordsPredicate;


/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice"))));

        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList(
                        new NameContainsKeywordsPredicate(Arrays.asList("Bob"))));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroNameKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_NO_PERSON_FOUND);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList()));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_emptyNameKeyword_allPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonListSize());
        List<Person> expectedPersonList = expectedModel.getFilteredPersonList();
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedPersonList, model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("Jojo", "Best")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JOJO), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNameKeywords_onePersonWithDifferentNameOrderFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("Kaka", "Many")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(KAKA), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneNameKeywordAsPrefix_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("Ka")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(KAKA), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneNameKeywordAsPrefix_multiplePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("Bes")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE, JOJO), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordAsPrefix_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("Jo", "B")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JOJO), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordAsPrefix_multiplePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("D", "M")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, KAKA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("keywords")));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private PersonContainsKeywordsPredicate preparePredicate(Predicate<Person>... predicates) {
        return new PersonContainsKeywordsPredicate(Arrays.asList(predicates));
    }
}
