package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_NO_PERSON_FOUND;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CALMEN;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
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
import seedu.address.model.person.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicate.InsuranceContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PersonContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PriorityContainsKeywordsPredicate;
import seedu.address.model.person.predicate.RemarkContainsKeywordsPredicate;
import seedu.address.model.person.predicate.TagContainsKeywordsPredicate;


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
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_NO_PERSON_FOUND);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList()));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_emptyKeyword_allPersonsFound() {

        // Name predicate
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonListSize());
        List<Person> expectedPersonList = expectedModel.getFilteredPersonList();
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedPersonList, model.getFilteredPersonList());

        // Email predicate
        predicate = preparePredicate(new EmailContainsKeywordsPredicate(Arrays.asList("")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedPersonList, model.getFilteredPersonList());

        // Phone predicate
        predicate = preparePredicate(new PhoneContainsKeywordsPredicate(Arrays.asList("")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedPersonList, model.getFilteredPersonList());


    }

    @Test
    public void execute_nameKeywords_personFound() {

        // multiple keywords one person found
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("Jo", "Be")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JOJO), model.getFilteredPersonList());

        // multiple keywords but order of name different order
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        predicate = preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("Kaka", "Many")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(KAKA), model.getFilteredPersonList());

        // multiple keywords multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        predicate = preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("D", "M")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, KAKA), model.getFilteredPersonList());
    }

    @Test
    public void execute_emailKeywords_personFound() {

        // one keyword one person found
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new EmailContainsKeywordsPredicate(Arrays.asList("kaka@example.com")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(KAKA), model.getFilteredPersonList());

        // one keywords multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        predicate = preparePredicate(new EmailContainsKeywordsPredicate(Arrays.asList("j")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, JOJO), model.getFilteredPersonList());
    }

    @Test
    public void execute_phoneKeywords_personFound() {

        // multiple keywords one person found
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new PhoneContainsKeywordsPredicate(Arrays.asList("14445656")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JOJO), model.getFilteredPersonList());

        // one keyword one person found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        predicate = preparePredicate(new PhoneContainsKeywordsPredicate(Arrays.asList("8")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL), model.getFilteredPersonList());

    }

    @Test
    public void execute_addressKeywords_personFound() {

        // empty keyword, everyone with address found
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonListSize() - 1);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new AddressContainsKeywordsPredicate(Arrays.asList("")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, GEORGE,
                JOJO, KAKA, FIONA), model.getFilteredPersonList());

        // one keyword one person found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        predicate = preparePredicate(new AddressContainsKeywordsPredicate(Arrays.asList("little")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA), model.getFilteredPersonList());

        // One keyword multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        predicate = preparePredicate(new AddressContainsKeywordsPredicate(Arrays.asList("street")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, GEORGE, JOJO, KAKA), model.getFilteredPersonList());
    }

    @Test
    public void execute_priorityKeywords_personFound() {

        // Empty keyword, everyone with priority found
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonListSize());
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new PriorityContainsKeywordsPredicate(Arrays.asList("")));
        List<Person> expectedPersonList = expectedModel.getFilteredPersonList();
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedPersonList, model.getFilteredPersonList());

        // One person found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        predicate = preparePredicate(new PriorityContainsKeywordsPredicate(Arrays.asList("low")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA), model.getFilteredPersonList());

    }

    @Test
    public void execute_remarkKeywords_personFound() {

        // multiple keywords one person found
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new RemarkContainsKeywordsPredicate(Arrays.asList("")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CALMEN, JOJO), model.getFilteredPersonList());

        // One person found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        predicate = preparePredicate(new RemarkContainsKeywordsPredicate(Arrays.asList("sOMe", "Re")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JOJO), model.getFilteredPersonList());

    }

    @Test
    public void execute_tagKeywords_personFound() {

        // empty keyword, everyone with tags found
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new TagContainsKeywordsPredicate(Arrays.asList("")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());

        // multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        predicate = preparePredicate(new TagContainsKeywordsPredicate(Arrays.asList("Frien")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());

        // one person found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        predicate = preparePredicate(new TagContainsKeywordsPredicate(Arrays.asList("owe")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_insuraceKeywords_personFound() {

        // empty keyword, everyone with insurances found
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new InsuranceContainsKeywordsPredicate(Arrays.asList("")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, ELLE, GEORGE), model.getFilteredPersonList());

        // One person found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        predicate = preparePredicate(new InsuranceContainsKeywordsPredicate(Arrays.asList("Great")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());

        // Multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        predicate = preparePredicate(new InsuranceContainsKeywordsPredicate(Arrays.asList("Ins")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, ELLE, GEORGE), model.getFilteredPersonList());
    }
    @Test
    public void execute_multipleTypeOfKeywords_personFound() {

        // empty name, email, phone
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonListSize());
        List<Person> expectedPersonList = expectedModel.getFilteredPersonList();
        PersonContainsKeywordsPredicate predicate =
                preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("")),
                        new EmailContainsKeywordsPredicate(Arrays.asList("")),
                        new PhoneContainsKeywordsPredicate(Arrays.asList("")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedPersonList, model.getFilteredPersonList());


        // One person found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        predicate = preparePredicate(new NameContainsKeywordsPredicate(Arrays.asList("Best")),
                new PhoneContainsKeywordsPredicate(Arrays.asList("14445656")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(JOJO), model.getFilteredPersonList());

        // Multiple persons found
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        predicate = preparePredicate(new PhoneContainsKeywordsPredicate(Arrays.asList("94")),
                new InsuranceContainsKeywordsPredicate(Arrays.asList("ins")));
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE, GEORGE), model.getFilteredPersonList());
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
