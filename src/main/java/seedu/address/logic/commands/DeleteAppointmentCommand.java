package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;

public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "deleteappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a customer's appointment. \n"
            + "Usage: "
            + "INDEX (must be a positive integer)";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted appointment with %1$s";
    public static final String MESSAGE_DELETE_APPOINTMENT_FAILED_EMPTY_APPT = "No appointment with %1$s yet!";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Index index;
    private final Appointment appointment;

    /**
     * Construct for an {@code DeleteAppointmentCommand}
     *
     * @param index index of the person in the filtered person list to delete the appointment
     * @param appointment appointment scheduled with the person
     */
    public DeleteAppointmentCommand(Index index, Appointment appointment) {
        this.index = index;
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(index.getZeroBased());
        Person updatedPerson = new Person(
                personToDelete.getName(), personToDelete.getPhone(), personToDelete.getEmail(),
                personToDelete.getAddress(), personToDelete.getRemark(),
                personToDelete.getTags(), appointment, personToDelete.getCount());

        String name = personToDelete.getName().fullName;

        if (Appointment.isAppointmentEmpty(personToDelete.getAppointment())) {
            logger.warning("-----Invalid Delete Appointment Command: Appointment does not exist-----");
            throw new CommandException(String.format(MESSAGE_DELETE_APPOINTMENT_FAILED_EMPTY_APPT, name));
        }

        model.setPerson(personToDelete, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        logger.info("-----Delete Appointment Command: Appointment deleted successfully-----");

        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, name));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        // state check
        DeleteAppointmentCommand e = (DeleteAppointmentCommand) other;
        return index.equals(e.index)
                && appointment.equals(e.appointment);
    }

}