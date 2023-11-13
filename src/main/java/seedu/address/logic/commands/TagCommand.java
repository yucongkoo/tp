package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandUtil.getPersonAtIndex;
import static seedu.address.logic.commands.CommandUtil.verifyPersonChanged;
import static seedu.address.logic.commands.CommandUtil.verifyPersonTagCountIsValid;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.model.person.Person.createPersonWithUpdatedTags;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * Adds or deletes tags of a person identified using it's displayed index from the address book.
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = "Usage: \n" + COMMAND_WORD
            + " <index> "
            + "[" + PREFIX_ADD_TAG + "<tag_to_add>]... "
            + "[" + PREFIX_DELETE_TAG + "<tag_to_delete>]...\n";

    public static final String MESSAGE_TAG_PERSON_SUCCESS = "Updated tag of customer: %s";
    public static final String MESSAGE_NOT_UPDATED = "At least one tag to add or delete must be provided.";
    public static final String MESSAGE_COMMON_TAG_FAILURE = "Should not add and delete the same tag.";
    public static final String MESSAGE_TAG_UNCHANGED_REASONS =
            "(Possible reason: only adding tags that already exist and/or deleting tags that do not exist)";

    private static final Logger logger = LogsCenter.getLogger(TagCommand.class);

    private final Index index;
    private final UpdatePersonTagsDescriptor updatePersonTagsDescriptor;

    /**
     * Constructs a Tag Command.
     *
     * @param index of the person in the filtered person list to tag.
     * @param updatePersonTagsDescriptor containing details of tags to update.
     */
    public TagCommand(Index index, UpdatePersonTagsDescriptor updatePersonTagsDescriptor) {
        requireAllNonNull(index, updatePersonTagsDescriptor);
        assert updatePersonTagsDescriptor.hasTagToUpdate()
                : "TagCommand expects an updatePersonTagsDescriptor that has tags to be updated";

        this.index = index;
        this.updatePersonTagsDescriptor = new UpdatePersonTagsDescriptor(updatePersonTagsDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.fine("TagCommand executing...");

        verifyCommandIsExecutable(model);

        Person personToUpdate = getPersonAtIndex(model, index);
        Person updatedPerson = createPersonWithUpdatedTags(personToUpdate,
                updatePersonTagsDescriptor.getTagsToAdd(),
                updatePersonTagsDescriptor.getTagsToDelete());
        requireAllNonNull(personToUpdate, updatedPerson);

        verifyPersonChanged(personToUpdate, updatedPerson, MESSAGE_TAG_UNCHANGED_REASONS);
        verifyPersonTagCountIsValid(updatedPerson);

        model.setPerson(personToUpdate, updatedPerson);

        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedPerson)));
    }

    /**
     * Throws a {@code CommandException} if the command is not executable.
     */
    private void verifyCommandIsExecutable(Model model) throws CommandException {
        requireNonNull(model);

        if (updatePersonTagsDescriptor.containsCommonTagToAddAndDelete()) {
            logger.finer("TagCommand execution failed due to common tag in add and delete");
            throw new CommandException(MESSAGE_COMMON_TAG_FAILURE);
        }
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return index.equals(otherTagCommand.index)
                && updatePersonTagsDescriptor.equals(otherTagCommand.updatePersonTagsDescriptor);
    }

    /**
     * Stores the information of tags to add to and tags to delete from a person.
     */
    public static class UpdatePersonTagsDescriptor {
        private Set<Tag> tagsToAdd;
        private Set<Tag> tagsToDelete;

        /**
         * Constructs a {@code UpdatePersonTagsDescriptor} with {@code tagsToAdd} and {@code tagsToDelete}.
         * A defensive copy of {@code tagsToAdd} and {@code tagsToDelete} is used internally.
         */
        public UpdatePersonTagsDescriptor(Set<Tag> tagsToAdd, Set<Tag> tagsToDelete) {
            requireAllNonNull(tagsToAdd, tagsToDelete);

            this.tagsToAdd = new HashSet<>(tagsToAdd);
            this.tagsToDelete = new HashSet<>(tagsToDelete);
        }

        /**
         * Constructs a defensive copy of {@code toCopy}.
         */
        public UpdatePersonTagsDescriptor(UpdatePersonTagsDescriptor toCopy) {
            requireNonNull(toCopy);

            this.tagsToAdd = new HashSet<>(toCopy.tagsToAdd);
            this.tagsToDelete = new HashSet<>(toCopy.tagsToDelete);
        }


        public void setTagsToAdd(Set<Tag> tagsToAdd) {
            requireNonNull(tagsToAdd);

            this.tagsToAdd = new HashSet<>(tagsToAdd);
        }

        public void setTagsToDelete(Set<Tag> tagsToDelete) {
            requireNonNull(tagsToDelete);

            this.tagsToDelete = new HashSet<>(tagsToDelete);
        }

        public Set<Tag> getTagsToAdd() {
            return new HashSet<>(tagsToAdd);
        }

        public Set<Tag> getTagsToDelete() {
            return new HashSet<>(tagsToDelete);
        }

        /**
         * Returns true if there is at least one tag to update.
         */
        public boolean hasTagToUpdate() {
            return !(tagsToAdd.isEmpty() && tagsToDelete.isEmpty());
        }

        private boolean containsCommonTagToAddAndDelete() {
            Set<Tag> intersectionSet = new HashSet<>(tagsToAdd);
            intersectionSet.retainAll(tagsToDelete);
            return !intersectionSet.isEmpty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof UpdatePersonTagsDescriptor)) {
                return false;
            }

            UpdatePersonTagsDescriptor otherUpdatePersonTagsDescriptor = (UpdatePersonTagsDescriptor) other;
            return tagsToAdd.equals(otherUpdatePersonTagsDescriptor.tagsToAdd)
                    && tagsToDelete.equals(otherUpdatePersonTagsDescriptor.tagsToDelete);
        }
    }
}
