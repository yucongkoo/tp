package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_VENUE;
import static seedu.address.logic.parser.ParserUtil.parseDateString;
import static seedu.address.logic.parser.ParserUtil.parseTimeString;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;

/**
 * Parses input arguments and creates a new AppointmentCommand object
 */
public class AppointmentCommandParser implements Parser<AppointmentCommand> {
    private static final Logger logger = LogsCenter.getLogger(AppointmentCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AppointmentCommand
     * and returns an AppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.fine("AppointmentCommandParser parsing: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_APPOINTMENT, PREFIX_APPOINTMENT_TIME, PREFIX_APPOINTMENT_VENUE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.finer("AppointmentCommandParser parse failed due to invalid index: " + argMultimap.getPreamble());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE), pe);
        }
        String parsedDate = getParsedDate(argMultimap);
        String parsedTime = getParsedTime(argMultimap);
        String parsedVenue = getParsedVenue(argMultimap);
        return new AppointmentCommand(index, new Appointment(parsedDate, parsedTime, parsedVenue));
    }

    private String checkInputDate(String retrievedDate) throws ParseException {
        if (retrievedDate.equalsIgnoreCase(Appointment.NO_APPOINTMENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AppointmentCommand.MESSAGE_USAGE));
        }

        return retrievedDate;
    }
    private String getParsedDate(ArgumentMultimap argMultimap) throws ParseException {
        String retrievedDate = checkInputDate(argMultimap.getValue(PREFIX_APPOINTMENT)
                .orElse(Appointment.NO_APPOINTMENT));
        String parsedDate = parseDateString(retrievedDate);
        return parsedDate;
    }

    private String getParsedTime(ArgumentMultimap argMultimap) throws ParseException {
        String retrievedTime = argMultimap.getValue(PREFIX_APPOINTMENT_TIME).orElse(Appointment.NO_TIME);
        String parsedTime = parseTimeString(retrievedTime);
        logger.info(parsedTime + "get to apptcmd parser");
        return parsedTime;
    }

    private String checkVenueLength(String venue) throws ParseException {
        if (!Appointment.isValidVenueFormat(venue)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Appointment.INVALID_VENUE_INPUT));
        }
        return venue;
    }

    private String getParsedVenue(ArgumentMultimap argMultimap) throws ParseException {
        String retrievedVenue = argMultimap.getValue(PREFIX_APPOINTMENT_VENUE).orElse(Appointment.NO_VENUE);
        String parsedVenue = checkVenueLength(retrievedVenue);
        return parsedVenue;
    }
}
