package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.Person.createPersonWithUpdatedPriority;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;

/** Assigns priority to a person. **/
public class PriorityCommand extends Command {
    public static final String COMMAND_WORD = "pr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign priority to a person "
            + "by the index number used in the displayed person list.\n"
            + "Usage: "
            + COMMAND_WORD + " <INDEX> (must be a positive integer) "
            + "<PRIORITY_LEVEL>\n"
            + Priority.MESSAGE_CONSTRAINTS;

    public static final String MESSAGE_NOT_ASSIGNED = "Priority given is the same as previous one.";
    public static final String MESSAGE_ASSIGN_PERSON_SUCCESS = "Updated priority of person: %1$s";

    private final Index index;
    private final Priority priority;

    /**
     * Constructs a PriorityCommand.
     *
     * @param index of the person in the filtered list to assign priority to.
     * @param priority assigned to the person.
     */
    public PriorityCommand(Index index, Priority priority) {
        requireNonNull(index);
        requireNonNull(priority);

        this.index = index;
        this.priority = priority;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        Person updatedPerson = createPersonWithUpdatedPriority(personToUpdate, priority);

        if (personToUpdate.hasSamePriority(priority)) {
            throw new CommandException(MESSAGE_NOT_ASSIGNED);
        }

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ASSIGN_PERSON_SUCCESS, Messages.format(updatedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PriorityCommand)) {
            return false;
        }

        PriorityCommand otherPriorityCommand = (PriorityCommand) other;
        return priority.equals(otherPriorityCommand.priority) && index.equals(otherPriorityCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("priority", priority)
                .toString();
    }
}
