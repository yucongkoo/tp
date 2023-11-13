package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.Messages.MESSAGE_INSURANCE_COUNT_EXCEED;
import static seedu.address.logic.Messages.MESSAGE_PERSON_NOT_CHANGED;
import static seedu.address.logic.Messages.MESSAGE_TAG_COUNT_EXCEED;
import static seedu.address.model.person.Insurance.MAX_INSURANCE_COUNT;
import static seedu.address.model.person.Tag.MAXIMUM_TAGS_PER_PERSON;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/** Contains utility methods for commands. **/
public class CommandUtil {
    private static final Logger logger = LogsCenter.getLogger(CommandUtil.class);

    /**
     * Returns the person from the model at a specific index.
     */
    public static Person getPersonAtIndex(Model model, Index index) throws CommandException {
        requireAllNonNull(model, index);
        List<Person> lastShownList = model.getFilteredPersonList();

        assert lastShownList.size() >= 0 : "Size of list should not be a negative number";
        assert index.getZeroBased() >= 0 : "index.getZeroBased() should not be a negative number";

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.finer(String.format("Execution failed due to index %d out of bound", index.getOneBased()));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        assert personToUpdate != null : "null instance in filtered person list";

        return personToUpdate;
    }

    /**
     * Throws a {@code CommandException} if {@code updatedPerson} is equal to {@code personToUpdate}.
     */
    public static void verifyPersonChanged(Person personToUpdate, Person updatedPerson,
                                           String reasons) throws CommandException {
        requireAllNonNull(personToUpdate, updatedPerson, reasons);
        if (personToUpdate.equals(updatedPerson)) {
            logger.finer("Command execution failed due to no changes in person." + reasons);
            throw new CommandException(MESSAGE_PERSON_NOT_CHANGED + "\n" + reasons);
        }
    }

    /**
     * Throws a {@code CommandException} if {@code updatedPerson} is equal to {@code personToUpdate}.
     */
    public static void verifyPersonChanged(Person personToUpdate, Person updatedPerson) throws CommandException {
        requireAllNonNull(personToUpdate, updatedPerson);

        if (personToUpdate.equals(updatedPerson)) {
            logger.finer("Command execution failed due to no changes in person.");
            throw new CommandException(MESSAGE_PERSON_NOT_CHANGED);
        }
    }

    /**
     * Throws a {@code CommandException} if the {@code person}'s tag count exceeds the allowed count.
     */
    public static void verifyPersonTagCountIsValid(Person person) throws CommandException {
        requireNonNull(person);
        if (person.getTagsCount() > MAXIMUM_TAGS_PER_PERSON) {
            logger.finer("Command execution failed due to exceeding maximum tag counts allowed.");
            throw new CommandException(MESSAGE_TAG_COUNT_EXCEED);
        }
    }

    /**
     * Throws a {@code CommandException} if the {@code person}'s insurance count exceeds the allowed count.
     */
    public static void verifyPersonInsuranceCountIsValid(Person person) throws CommandException {
        requireNonNull(person);
        if (person.getInsurancesCount() > MAX_INSURANCE_COUNT) {
            logger.finer("Command execution failed due to exceeding maximum insurance counts allowed.");
            throw new CommandException(MESSAGE_INSURANCE_COUNT_EXCEED);
        }
    }

    /**
     * Throws a {@code CommandException} if the {@code model} has the {@code person}.
     */
    public static void verifyPersonNotInModel(Model model, Person person) throws CommandException {
        requireAllNonNull(model, person);
        if (model.hasPerson(person)) {
            logger.finer("Command execution failed due to person already exist in model.");
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }
}
