package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_VENUE;
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

/**
 * Adds/Edits the appointment with an existing person in the FAST.
 */
public class AppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addappt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add appointment with the customer identified"
            + " by the index number used in the displayed person list.\n"
            + "Usage: "
            + COMMAND_WORD + " <INDEX> "
            + "[" + PREFIX_APPOINTMENT + "<DATE>]..."
            + "[" + PREFIX_APPOINTMENT_TIME + "<TIME>] "
            + "[" + PREFIX_APPOINTMENT_VENUE + "VENUE]...\n";

    public static final String MESSAGE_ADD_APPOINTMENT_SUCCESS = "Added appointment with customer: %1$s %2$s %3$s %4$s";
    public static final String MESSAGE_ADD_APPOINTMENT_FAILURE_APPT_EXIST = "Appointment already exists!";
    private final Index index;
    private final Appointment appointment;
    private static final Logger logger = LogsCenter.getLogger(AppointmentCommand.class);


    /**
     * Constructor for an {@code AppointmentCommand}
     *
     * @param index index of the customer list to add appointment for
     * @param appointment appointment scheduled with the target
     */
    public AppointmentCommand(Index index, Appointment appointment)  {
        requireAllNonNull(index, appointment);

        this.index = index;
        this.appointment = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.fine("AppointmentCommand executing...");

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.finer(String.format("AppointmentCommand execution failed due to index %d out of bound",
                    index.getOneBased()));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        Person updatedPerson = Person.createPersonWithAddedAppointment(personToUpdate, appointment);

        if (!Appointment.isAppointmentEmpty(personToUpdate.getAppointment())) {
            logger.warning("-----Invalid Add Appointment Command: Appointment Already Exist-----");
            throw new CommandException(MESSAGE_ADD_APPOINTMENT_FAILURE_APPT_EXIST);
        }

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        logger.info("-----Add Appointment Command: Appointment added successfully-----");

        return new CommandResult(generateSuccessMessage(updatedPerson));
    }

    /**
     * Generates a command execution success message when appointment has been added successfully.
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person editedPerson) {

        return String.format(MESSAGE_ADD_APPOINTMENT_SUCCESS, editedPerson.getName().fullName,
                editedPerson.getAppointment().getDate(),
                editedPerson.getAppointment().getTimeFormatted(),
                editedPerson.getAppointment().getVenue());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentCommand)) {
            return false;
        }

        AppointmentCommand a = (AppointmentCommand) other;
        return index.equals(a.index)
                && appointment.equals(a.appointment);
    }
}
