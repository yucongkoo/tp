package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandUtil.getPersonAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CommandUtilTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void getPersonAtIndex_nullInput_throwsNullPointerException() {
        // model and index are null
        assertThrows(NullPointerException.class, () -> getPersonAtIndex(null, null));

        // model is null
        assertThrows(NullPointerException.class, () -> getPersonAtIndex(null, INDEX_FIRST_PERSON));

        // index is null
        assertThrows(NullPointerException.class, () -> getPersonAtIndex(model, null));
    }

    @Test
    public void getPersonAtIndex_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonListSize() + 1);
        assertThrows(CommandException.class, () -> getPersonAtIndex(model, outOfBoundIndex));
    }

    @Test
    public void getPersonAtIndex_validInput_success() {
        Person expectedPerson = new PersonBuilder(model.getFilteredPersonList().get(0)).build();

        try {
            assertEquals(expectedPerson, getPersonAtIndex(model, INDEX_FIRST_PERSON));
        } catch (CommandException e) {
            fail();
        }
    }
}
