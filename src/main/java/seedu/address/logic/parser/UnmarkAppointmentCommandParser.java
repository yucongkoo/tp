package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnmarkAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;

/**
 * Parses input arguments and creates a new UnmarkAppointmentCommand object
 */
public class UnmarkAppointmentCommandParser implements Parser<UnmarkAppointmentCommand> {
    @Override
    public UnmarkAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkAppointmentCommand.MESSAGE_USAGE), ive);
        }

        return new UnmarkAppointmentCommand(index, Appointment.getDefaultEmptyAppointment());
    }
}
