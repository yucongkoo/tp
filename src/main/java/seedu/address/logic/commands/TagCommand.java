package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;

/**
 * Adds or deletes tags of a person identified using it's displayed index from the address book.
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add/Delete tags of the person identified "
            + "by the index number used in the displayed person list. "
            + "Non-existing tags to be deleted will be ignored.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ADD_TAG + "TAG_TO_ADD]... "
            + "[" + PREFIX_DELETE_TAG + "TAG_TO_DELETE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ADD_TAG + "friend "
            + PREFIX_ADD_TAG + "tall "
            + PREFIX_DELETE_TAG + "short ";


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

        String feedback = String.format("tagging to index: %d, add: %s, del: %s", index.getOneBased(), tagsToAdd.toString(), tagsToDelete.toString());
        return new CommandResult(feedback);
    }
}
