package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CALMEN = "Calmen";
    public static final String VALID_NAME_DERRICK = "Derrick";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_CALMEN = "33333333";
    public static final String VALID_PHONE_DERRICK = "44444444";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_CALMEN = "calmen@example.com";
    public static final String VALID_EMAIL_DERRICK = "derrick@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_REMARK_AMY = "nothing to remark";
    public static final String VALID_REMARK_BOB = "Owned me 30k";
    public static final String VALID_REMARK_CALMEN = "He like to eat apple";
    public static final String VALID_REMARK_DERRICK = "He don't like to shake hand with people";
    public static final String VALID_ADDRESS_DERRICK = "Block 432, Derrick Road";
    public static final String VALID_ADDRESS_WITH_PREFIX_TAG = "51, Kent Ridge/t";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_MALE = "male";

    public static final String VALID_INSURANCE_CAR = "car insurance";
    public static final String VALID_INSURANCE_LIFE = "life insurance";
    public static final String VALID_INSURANCE_HEALTH = "health insurance";
    public static final String VALID_PRIORITY_HIGH = "high";
    public static final String VALID_PRIORITY_LOW = "low";
    public static final String VALID_PRIORITY_NONE = "-";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_CALMEN = " " + PREFIX_NAME + VALID_NAME_CALMEN;
    public static final String NAME_DESC_DERRICK = " " + PREFIX_NAME + VALID_NAME_DERRICK;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String PHONE_DESC_CALMEN = " " + PREFIX_PHONE + VALID_PHONE_CALMEN;
    public static final String PHONE_DESC_DERRICK = " " + PREFIX_PHONE + VALID_PHONE_DERRICK;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EMAIL_DESC_CALMEN = " " + PREFIX_EMAIL + VALID_EMAIL_CALMEN;
    public static final String EMAIL_DESC_DERRICK = " " + PREFIX_EMAIL + VALID_EMAIL_DERRICK;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ADDRESS_DESC_DERRICK = " " + PREFIX_ADDRESS + VALID_ADDRESS_DERRICK;
    public static final String ADDRESS_DESC_WITH_PREFIX_TAG = " " + PREFIX_ADDRESS + VALID_ADDRESS_WITH_PREFIX_TAG;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String REMARK_DESC_CALMEN = " " + PREFIX_REMARK + VALID_REMARK_CALMEN;
    public static final String REMARK_DESC_DERRICK = " " + PREFIX_REMARK + VALID_REMARK_DERRICK;

    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String ADD_TAG_DESC_FRIEND = " " + PREFIX_ADD_TAG + VALID_TAG_FRIEND;
    public static final String DELETE_TAG_DESC_FRIEND = " " + PREFIX_DELETE_TAG + VALID_TAG_FRIEND;
    public static final String INSURANCE_DESC_CAR = " " + PREFIX_INSURANCE + VALID_INSURANCE_CAR;
    public static final String INSURANCE_DESC_LIFE = " " + PREFIX_INSURANCE + VALID_INSURANCE_LIFE;
    public static final String INSURANCE_DESC_HEALTH = " " + PREFIX_INSURANCE + VALID_INSURANCE_HEALTH;
    public static final String ADD_INSURANCE_DESC_CAR = " " + PREFIX_ADD_INSURANCE + VALID_INSURANCE_CAR;
    public static final String ADD_INSURANCE_DESC_LIFE = " " + PREFIX_ADD_INSURANCE + VALID_INSURANCE_LIFE;
    public static final String ADD_INSURANCE_DESC_HEALTH = " " + PREFIX_ADD_INSURANCE + VALID_INSURANCE_HEALTH;
    public static final String DELETE_INSURANCE_DESC_CAR = " " + PREFIX_DELETE_INSURANCE + VALID_INSURANCE_CAR;
    public static final String DELETE_INSURANCE_DESC_LIFE = " " + PREFIX_DELETE_INSURANCE + VALID_INSURANCE_LIFE;
    public static final String DELETE_INSURANCE_DESC_HEALTH = " " + PREFIX_DELETE_INSURANCE + VALID_INSURANCE_HEALTH;

    public static final String PRIORITY_DESC_HIGH = " " + PREFIX_PRIORITY + VALID_PRIORITY_HIGH;
    public static final String PRIORITY_DESC_NONE = " " + PREFIX_PRIORITY + VALID_PRIORITY_NONE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK + "this is a invalid remark because "
            + "this remark is too long! too long until more than 100 character which is not allowed";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_TAG_DESC2 = " " + PREFIX_TAG; // empty string not allowed for tags
    public static final String INVALID_ADD_TAG_DESC = " " + PREFIX_ADD_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_DELETE_TAG_DESC = " " + PREFIX_DELETE_TAG + "hubby*";
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "high low"; // multiple arguments
    public static final String INVALID_PRIORITY_DESC2 = " " + PREFIX_PRIORITY + "1";
    public static final String INVALID_PRIORITY_DESC3 = " " + PREFIX_PRIORITY;
    public static final String INVALID_INSURANCE_DESC1 = " " + PREFIX_INSURANCE + "/*-+"; // non-alphanumeric characters
    public static final String INVALID_INSURANCE_DESC2 = " " + PREFIX_INSURANCE
            + "12345678901234567890123456789012345"; // longer than 32 characters

    public static final String INVALID_ADD_INSURANCE_DESC1 =
            " " + PREFIX_ADD_INSURANCE + "/*-+"; // non-alphanumeric characters
    public static final String INVALID_ADD_INSURANCE_DESC2 = " " + PREFIX_ADD_INSURANCE
            + "12345678901234567890123456789012345"; // longer than 32 characters

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String INVALID_PRIORITY = "high low";
    public static final String INVALID_PRIORITY2 = "1";
    public static final String INVALID_PRIORITY3 = " ";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
