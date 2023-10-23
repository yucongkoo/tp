package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_NONE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;
import seedu.address.testutil.PersonBuilder;

public class PriorityCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Priority lowPriority = new Priority(VALID_PRIORITY_LOW);
    private Priority highPriority = new Priority(VALID_PRIORITY_HIGH);
    private Priority nonePriority = new Priority(VALID_PRIORITY_NONE);

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        Index index = INDEX_FIRST_PERSON;

        // null index
        assertThrows(NullPointerException.class, () -> new PriorityCommand(null, lowPriority));

        // null priority
        assertThrows(NullPointerException.class, () -> new PriorityCommand(index, null));

        // null index and priority
        assertThrows(NullPointerException.class, () -> new PriorityCommand(null, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonListSize() + 1);
        PriorityCommand pc = new PriorityCommand(outOfBoundIndex, lowPriority);

        assertCommandFailure(pc, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_samePriority_throwsCommandException() {
        Index indexOfTargetPerson = INDEX_FIRST_PERSON;
        PriorityCommand pc = new PriorityCommand(indexOfTargetPerson, highPriority); // default priority is high

        assertCommandFailure(pc, model, PriorityCommand.MESSAGE_NOT_ASSIGNED);
    }

    @Test
    public void execute_validPriority_success() {
        // change from high priority to low
        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person expectedPerson = new PersonBuilder(personInFilteredList).withPriority(VALID_PRIORITY_LOW).build();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), expectedPerson);
        String expectedMessage = String.format(PriorityCommand.MESSAGE_ASSIGN_PERSON_SUCCESS,
                Messages.format(expectedPerson));

        Index indexOfTargetPerson = INDEX_FIRST_PERSON;
        PriorityCommand pc = new PriorityCommand(indexOfTargetPerson, lowPriority);
        assertCommandSuccess(pc, model, expectedMessage, expectedModel);

        // change from high priority to none
        personInFilteredList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        expectedPerson = new PersonBuilder(personInFilteredList).withPriority(VALID_PRIORITY_NONE).build();
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()), expectedPerson);
        expectedMessage = String.format(PriorityCommand.MESSAGE_ASSIGN_PERSON_SUCCESS, Messages.format(expectedPerson));

        indexOfTargetPerson = INDEX_SECOND_PERSON;
        pc = new PriorityCommand(indexOfTargetPerson, nonePriority);
        assertCommandSuccess(pc, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final PriorityCommand standardCommand = new PriorityCommand(INDEX_FIRST_PERSON, highPriority);

        // same values -> returns true
        PriorityCommand commandWithSameValues = new PriorityCommand(INDEX_FIRST_PERSON, highPriority);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new PriorityCommand(INDEX_SECOND_PERSON, highPriority)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new PriorityCommand(INDEX_FIRST_PERSON, lowPriority)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        PriorityCommand pc = new PriorityCommand(index, lowPriority);
        String expected = PriorityCommand.class.getCanonicalName() + "{priority="
                + Priority.LOW_PRIORITY_KEYWORD + "}";
        assertEquals(expected, pc.toString());
    }
}
