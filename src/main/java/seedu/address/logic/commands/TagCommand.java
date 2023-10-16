package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Adds or deletes tags of a person identified using it's displayed index from the address book.
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add/Delete tags of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Usage: "
            + COMMAND_WORD + " <INDEX> "
            + "[" + PREFIX_ADD_TAG + "<TAG_TO_ADD>]... "
            + "[" + PREFIX_DELETE_TAG + "<TAG_TO_DELETE>]...\n";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Updated tag of person: %s";
    public static final String MESSAGE_NOT_UPDATED = "At least one tag to add or delete must be provided.";


    private Index index;
    private Set<Tag> tagsToAdd;
    private Set<Tag> tagsToDelete;

    /**
     * Constructs a Tag Command.
     *
     * @param index of the person in the filtered person list to tag.
     * @param tagsToAdd to the person specified by index.
     * @param tagsToDelete from the person specified by index.
     */
    public TagCommand(Index index, Set<Tag> tagsToAdd, Set<Tag> tagsToDelete) {
        requireNonNull(index);
        requireNonNull(tagsToAdd);
        requireNonNull(tagsToDelete);

        this.index = index;
        this.tagsToAdd = tagsToAdd;
        this.tagsToDelete = tagsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        Person updatedPerson = personToUpdate.createPersonWithUpdatedTags(tagsToAdd, tagsToDelete);

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedPerson)));
    }
}
