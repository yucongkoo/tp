package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.Person.createPersonWithUpdatePriority;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;

public class PriorityCommand extends Command {
    public static final String COMMAND_WORD = "pr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign priority to a person "
            + "by the index number used in the displayed person list.\n"
            + "Usage: "
            + COMMAND_WORD + " <INDEX> "
            + "<PRIORITY_LEVEL>\n"
            + Priority.MESSAGE_CONSTRAINTS;

    public static final String MESSAGE_NOT_ASSIGNED = "Priority given is the same as previous one.";
    public static final String MESSAGE_ASSIGN_PERSON_SUCCESS = "Updated priority of person: %1$s";

    private final Index index;
    private final Priority priority;

    /**
     * Constructs a PriorityCommand
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
        Person updatedPerson = createPersonWithUpdatePriority(personToUpdate, priority);

        if (personToUpdate.getPriority().equals(priority)) {
            throw new CommandException(MESSAGE_NOT_ASSIGNED);
        }

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ASSIGN_PERSON_SUCCESS, Messages.format(updatedPerson)));
    }
}
