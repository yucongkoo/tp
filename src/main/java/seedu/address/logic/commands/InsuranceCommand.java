package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandUtil.getPersonAtIndex;
import static seedu.address.logic.commands.CommandUtil.verifyPersonChanged;
import static seedu.address.logic.commands.CommandUtil.verifyPersonInsuranceCountIsValid;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_INSURANCE;
import static seedu.address.model.person.Person.createPersonWithUpdatedInsurances;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Insurance;
import seedu.address.model.person.Person;

/**
 * Parse input argument and create InsuranceCommand Object
 *
 */
public class InsuranceCommand extends Command {

    public static final String COMMAND_WORD = "insurance";

    public static final String MESSAGE_USAGE = "Usage: \n" + COMMAND_WORD
            + " <index> "
            + "[" + PREFIX_ADD_INSURANCE + "<insurance_to_add>]... "
            + "[" + PREFIX_DELETE_INSURANCE + "<insurance_to_delete>]...\n";

    public static final String MESSAGE_INSURANCE_PERSON_SUCCESS = "Update insurance of customer: %s";

    public static final String MESSAGE_INSURANCE_NO_UPDATE = "There has to be at least one insurance updated/deleted.";

    public static final String MESSAGE_INSURANCE_CONFLICT = "Should not assign and remove the same insurance.";

    public static final String MESSAGE_INSURANCE_UNCHANGED_REASONS =
            "(Possible reason: only adding insurances that already exist and/or deleting insurances that do not exist)";

    private static final Logger logger = LogsCenter.getLogger(InsuranceCommand.class);

    private final Index index;

    private final UpdatePersonInsuranceDescriptor updatePersonInsuranceDescriptor;

    /**
     * Instantiate {@code InsuranceCommand}
     */
    public InsuranceCommand(Index i, UpdatePersonInsuranceDescriptor u) {
        requireAllNonNull(i, u);
        assert u.hasInsuranceToUpdate() : "It should have insurance to update";

        this.index = i;
        this.updatePersonInsuranceDescriptor = u;
    }

    /**
     * Assigns an insurance to a customer identified by index
     *
     * @param m {@code Model} which the command should operate on.
     * @return {@code CommandResult} for Ui component to display
     * @throws CommandException when index is out of bound or maximum amount of insurance is exceeded
     */
    @Override
    public CommandResult execute(Model m) throws CommandException {
        requireAllNonNull(m);
        verifyNoConflictingInsurance();

        return updatePerson(m);
    }

    /**
     * Check for conflicting {@code Insurance} for the given command
     *
     * @throws CommandException when command add and delete the same {@code Insurance} at the same time
     */
    private void verifyNoConflictingInsurance() throws CommandException {
        if (updatePersonInsuranceDescriptor.hasCommonInsurance()) {
            throw new CommandException(MESSAGE_INSURANCE_CONFLICT);
        }
    }

    /**
     * Update the customer details in the contact book
     *
     * @param m {@code Model}
     * @return {@code CommandResult} result message of the command execution
     * @throws CommandException when the execution of command is not allowed
     */
    private CommandResult updatePerson(Model m) throws CommandException {
        Person personToUpdate = getPersonAtIndex(m, index);
        Person updatedPerson = createPersonWithUpdatedInsurances(personToUpdate,
                updatePersonInsuranceDescriptor.insurancesToAdd,
                updatePersonInsuranceDescriptor.insurancesToDelete);

        verifyPersonInsuranceCountIsValid(updatedPerson);
        verifyPersonChanged(personToUpdate, updatedPerson, MESSAGE_INSURANCE_UNCHANGED_REASONS);

        m.setPerson(personToUpdate, updatedPerson);

        return new CommandResult(String.format(MESSAGE_INSURANCE_PERSON_SUCCESS, Messages.format(updatedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof InsuranceCommand)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        InsuranceCommand temp = (InsuranceCommand) other;

        return this.index.equals(temp.index)
                && this.updatePersonInsuranceDescriptor.equals(temp.updatePersonInsuranceDescriptor);
    }

    /**
     * Wrapper class to hold the changes to insurance of a customer
     */
    public static class UpdatePersonInsuranceDescriptor {

        private Set<Insurance> insurancesToAdd;
        private Set<Insurance> insurancesToDelete;

        /**
         * Instantiate {@code UpdatePersonInsuranceDescriptor}
         */
        public UpdatePersonInsuranceDescriptor(Set<Insurance> toAdd, Set<Insurance> toDelete) {
            this.insurancesToAdd = toAdd;
            this.insurancesToDelete = toDelete;
        }

        public Set<Insurance> getInsurancesToAdd() {
            return insurancesToAdd;
        }

        public Set<Insurance> getInsurancesToDelete() {
            return insurancesToDelete;
        }

        public void setInsurancesToAdd(Insurance i) {
            this.insurancesToAdd.add(i);
        }
        public void setInsurancesToDelete(Insurance i) {
            this.insurancesToDelete.add(i);
        }

        /**
         * Checks for common insurance between add and delete sets
         *
         * @return false if there is no common insurance
         */
        public boolean hasCommonInsurance() {
            Set<Insurance> intersection = new HashSet<>(insurancesToAdd);
            intersection.retainAll(insurancesToDelete);

            return !intersection.isEmpty();
        }

        /**
         * Checks for available insurance to modify
         *
         * @return true if there exist insurance to add or delete
         */
        public boolean hasInsuranceToUpdate() {
            return !(insurancesToAdd.isEmpty() && insurancesToDelete.isEmpty());
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof UpdatePersonInsuranceDescriptor)) {
                return false;
            }

            UpdatePersonInsuranceDescriptor temp = (UpdatePersonInsuranceDescriptor) other;
            return this.insurancesToAdd.equals(temp.insurancesToAdd)
                    && this.insurancesToDelete.equals(temp.insurancesToDelete);
        }
    }
}
