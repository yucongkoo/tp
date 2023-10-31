package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandUtil.verifyPersonInsuranceCountIsValid;
import static seedu.address.logic.commands.CommandUtil.verifyPersonNotInModel;
import static seedu.address.logic.commands.CommandUtil.verifyPersonTagCountIsValid;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = "Usage: \n" + COMMAND_WORD + " "
            + PREFIX_NAME + "<name> "
            + PREFIX_PHONE + "<phone number> "
            + PREFIX_EMAIL + "<email address> "
            + "[" + PREFIX_ADDRESS + "<home/office address>] "
            + "[" + PREFIX_PRIORITY + "<priority>] "
            + "[" + PREFIX_TAG + "<tag>]... "
            + "[" + PREFIX_INSURANCE + "<insurance>]... "
            + "[" + PREFIX_REMARK + "<remarks>]\n";


    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}.
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        verifyCommandIsExecutable(model);
        model.addPerson(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Throws a {@code CommandException} if {@code toAdd} is not a valid person to add to {@code model}.
     */
    private void verifyCommandIsExecutable(Model model) throws CommandException {
        requireNonNull(model);

        verifyPersonTagCountIsValid(toAdd);
        verifyPersonInsuranceCountIsValid(toAdd);
        verifyPersonNotInModel(model, toAdd);
    }



    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
