package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandUtil.getPersonAtIndex;
import static seedu.address.logic.commands.CommandUtil.verifyPersonChanged;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.person.Person.createPersonWithEditedInformation;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = "Usage: \n" + COMMAND_WORD
            + " <index> "
            + "[" + PREFIX_NAME + "<name>] "
            + "[" + PREFIX_PHONE + "<phone>] "
            + "[" + PREFIX_EMAIL + "<email>] "
            + "[" + PREFIX_ADDRESS + "<address>]\n";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited customer: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_EDIT_TAG_ERROR = "Cannot edit tags. "
            + "Please use \"tag\" command to add/delete tags.";
    public static final String MESSAGE_EDIT_PRIORITY_ERROR = "Cannot edit priority. "
            + "Please use \"pr\" command to assign new priority.";
    public static final String MESSAGE_EDIT_REMARK_ERROR = "Cannot edit remark. "
            + "Please use \"remark\" command to modify remark.";
    public static final String MESSAGE_EDIT_INSURANCE_ERROR = "Cannot edit insurance. "
            + "Please use \"insurance\" command to add/delete insurances.";

    /** Stores a prefix and its corresponding edit error message as a key-value pair. **/
    public static final HashMap<Prefix, String> PREFIX_EDIT_ERROR_MESSAGE_MAP = new HashMap<>() {
        {
            put(PREFIX_TAG, MESSAGE_EDIT_TAG_ERROR);
            put(PREFIX_PRIORITY, MESSAGE_EDIT_PRIORITY_ERROR);
            put(PREFIX_REMARK, MESSAGE_EDIT_REMARK_ERROR);
            put(PREFIX_INSURANCE, MESSAGE_EDIT_INSURANCE_ERROR);
        }
    };

    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * Constructs a new EditCommand.
     *
     * @param index of the person in the filtered person list to edit.
     * @param editPersonDescriptor details to edit the person with.
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.fine("EditCommand executing...");

        Person personToEdit = getPersonAtIndex(model, index);
        Person editedPerson = createPersonWithEditedInformation(personToEdit, editPersonDescriptor);

        verifyPersonChanged(personToEdit, editedPerson);
        checkIsDuplicatePerson(model, personToEdit, editedPerson);

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    private void checkIsDuplicatePerson(Model model, Person personToEdit, Person editedPerson) throws CommandException {
        boolean hasDuplicateInModel = !personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson);
        boolean isDuplicateOfOthersInModel = false;

        for (int i = 0; i < model.getFilteredPersonListSize(); i++) {
            Person personAtIndex = getPersonAtIndex(model, Index.fromZeroBased(i));
            if (i != index.getZeroBased() && editedPerson.isSamePerson(personAtIndex)) {
                isDuplicateOfOthersInModel = true;
            }
        }

        if (hasDuplicateInModel || isDuplicateOfOthersInModel) {
            logger.finer("EditCommand execution failed due to duplicated persons in list");
            throw new CommandException(Messages.MESSAGE_DUPLICATE_PERSON);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            requireNonNull(toCopy);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .toString();
        }
    }
}
