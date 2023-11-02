package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandUtil.getPersonAtIndex;
import static seedu.address.logic.commands.CommandUtil.verifyPersonChanged;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Person;

/**
 * Marks the appointment with an existing person in the address book.
 */
public class MarkAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "markappt";

    public static final String MESSAGE_USAGE = "Usage: \n" + COMMAND_WORD
            + " <index>";

    public static final String MESSAGE_MARK_APPOINTMENT_SUCCESS = "Marked an appointment with "
            + "%1$s %2$s %3$s %4$s";
    public static final String MESSAGE_MARK_APPOINTMENT_FAILURE_EMPTY_APPT = "No appointment exists!";

    private static final Logger logger = LogsCenter.getLogger(MarkAppointmentCommand.class);

    private final Index index;
    private final Appointment appointment;

    /**
     * Construct for an {@code MarkAppointmentCommand}
     *
     * @param index index of the person in the filtered person list to mark the appointment as completed
     * @param appointment the existing appointment that has been completed
     */
    public MarkAppointmentCommand(Index index, Appointment appointment) {
        requireAllNonNull(index, appointment);

        this.index = index;
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person personToEdit = getPersonAtIndex(model, index);

        if (Appointment.isAppointmentEmpty(personToEdit.getAppointment())) {
            logger.warning("-----Invalid Mark Appointment Command: Appointment does not exist-----");
            throw new CommandException(MESSAGE_MARK_APPOINTMENT_FAILURE_EMPTY_APPT);
        }

        Person editedPerson = Person.createPersonWithIncreasedCount(personToEdit,
                appointment, personToEdit.getAppointmentCount());
        verifyPersonChanged(personToEdit, editedPerson);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        logger.info("-----Mark Appointment Command: Appointment marked successfully-----");

        return new CommandResult(generateSuccessMessage(personToEdit));
    }

    /**
     * Generates a command execution success message when the appointment is marked as completed successfully.
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {

        return String.format(MESSAGE_MARK_APPOINTMENT_SUCCESS, personToEdit.getName().fullName,
                personToEdit.getAppointment().getDate(),
                personToEdit.getAppointment().getTimeFormatted(),
                personToEdit.getAppointment().getVenue());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAppointmentCommand)) {
            return false;
        }

        // state check
        MarkAppointmentCommand e = (MarkAppointmentCommand) other;
        return index.equals(e.index)
                && appointment.equals(e.appointment);
    }
}
