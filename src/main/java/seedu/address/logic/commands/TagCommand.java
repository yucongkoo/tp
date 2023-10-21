package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_TAG_COUNT_EXCEED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.Person.createPersonWithUpdatedTags;
import static seedu.address.model.tag.Tag.MAXIMUM_TAGS_PER_PERSON;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

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
    public static final String MESSAGE_COMMON_TAG_FAILURE = "Should not add and delete the same tag.";

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

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (updatePersonTagsDescriptor.containsCommonTagToAddAndDelete()) {
            throw new CommandException(MESSAGE_COMMON_TAG_FAILURE);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        Person updatedPerson = createPersonWithUpdatedTags(personToUpdate,
                updatePersonTagsDescriptor.getTagsToAdd(),
                updatePersonTagsDescriptor.getTagsToDelete());

        requireAllNonNull(personToUpdate, updatedPerson);

        if (updatedPerson.getTagsCount() > MAXIMUM_TAGS_PER_PERSON) {
            throw new CommandException(MESSAGE_TAG_COUNT_EXCEED);
        }

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedPerson)));
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
