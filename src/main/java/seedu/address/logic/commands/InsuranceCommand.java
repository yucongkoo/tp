package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INSURANCE_COUNT_EXCEED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_INSURANCE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.insurance.Insurance.MAX_INSURANCE_COUNT;
import static seedu.address.model.person.Person.createPersonWithUpdatedInsurances;

public class InsuranceCommand extends Command{

    public static final String COMMAND_WORD = "ins";

    public static String MESSAGE_USAGE = COMMAND_WORD + ": Assign/Remove insurance policy to/from customer identified "
            + "by index shown in the displayed customer list.\n"
            + "Usage: " + COMMAND_WORD + " <INDEX> "
            + "[" + PREFIX_ADD_INSURANCE + "<INSURANCE_TO_ADD>]... "
            + "[" + PREFIX_DELETE_INSURANCE + "<INSURANCE_TO_DELETE>]...\n";

    public static String MESSAGE_INSURANCE_PERSON_SUCCESS = "Update insurance of person: %s";

    public static String MESSAGE_INSURANCE_NO_UPDATE = "There has to be at least one insurance updated/deleted.";

    public static String MESSAGE_INSURANCE_CONFLICT = "Should not assign and remove the same insurance.";

    private static final Logger logger = LogsCenter.getLogger(InsuranceCommand.class);

    private Index index;

    private UpdatePersonInsuranceDescriptor updatePersonInsuranceDescriptor;

    public InsuranceCommand(Index i, UpdatePersonInsuranceDescriptor u) {
        requireAllNonNull(i,u);
        this.index = i;
        this.updatePersonInsuranceDescriptor = u;
    }

    @Override
    public CommandResult execute(Model m) throws CommandException {
        requireAllNonNull(m);
        List<Person> personList = m.getFilteredPersonList();

        if (index.getZeroBased() >= personList.size()) {
            logger.finer(String.format("InsuranceCommand execution failed due to index %d out of bound",
                    index.getOneBased()));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (updatePersonInsuranceDescriptor.hasCommonInsurance()) {
            throw new CommandException("");
        }

        Person personToUpdate = personList.get(index.getZeroBased());
        Person updatedPerson = createPersonWithUpdatedInsurances(personToUpdate,
                updatePersonInsuranceDescriptor.insurancesToAdd,
                updatePersonInsuranceDescriptor.insurancesToDelete);

        if (updatedPerson.getInsurancesCount() >= MAX_INSURANCE_COUNT) {
            logger.finer("InsuranceCommand execution failed due to exceeding maximum insurance counts allowed");
            throw new CommandException(MESSAGE_INSURANCE_COUNT_EXCEED);
        }

        requireAllNonNull(personToUpdate, updatedPerson);

        m.setPerson(personToUpdate, updatedPerson);
        m.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

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

    public static class UpdatePersonInsuranceDescriptor {

        private Set<Insurance> insurancesToAdd;
        private Set<Insurance> insurancesToDelete;

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

        public void setInsurancesToAdd(Set<Insurance> insurancesToAdd) {
            this.insurancesToAdd = insurancesToAdd;
        }

        public void setInsurancesToDelete(Set<Insurance> insurancesToDelete) {
            this.insurancesToDelete = insurancesToDelete;
        }

        public boolean hasCommonInsurance() {
            Set<Insurance> intersection = new HashSet<>(insurancesToAdd);
            intersection.retainAll(insurancesToDelete);

            return !intersection.isEmpty();
        }

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
