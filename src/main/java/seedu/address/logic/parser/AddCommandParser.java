package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.AppointmentCount;
import seedu.address.model.person.Email;
import seedu.address.model.person.Insurance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Tag;
import seedu.address.model.priority.Priority;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Prefix[] validPrefixes = new Prefix[] { PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_INSURANCE, PREFIX_REMARK, PREFIX_PRIORITY};
    private static final Prefix[] compulsoryPrefixes =
            new Prefix[] { PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL };
    private static final Prefix[] nonRepeatingPrefixes =
            new Prefix[] { PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_PRIORITY };

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, validPrefixes);
        verifyIsValidArgumentMultimap(argMultimap);

        Person personToAdd = createPersonToAdd(argMultimap);

        return new AddCommand(personToAdd);
    }

    /**
     * Throws a {@code ParseException} if the {@code map} is not a valid {@code ArgumentMultimap}
     * for {@code AddCommand}.
     */
    private static void verifyIsValidArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);

        if (!argMultimap.areAllPrefixesPresent(compulsoryPrefixes)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.isPreambleEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(nonRepeatingPrefixes);
    }

    /**
     * Creates a {@code Person} using the information in {@code argMultimap}.
     * @param argMultimap contains the information of the attributes of the person.
     * @throws ParseException if there are certain attributes that could not be parsed.
     */
    private static Person createPersonToAdd(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).orElse(""));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(""));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<Insurance> insurances = ParserUtil.parseInsurances(argMultimap.getAllValues(PREFIX_INSURANCE));
        Appointment appointment = Appointment.getDefaultEmptyAppointment();
        AppointmentCount count = AppointmentCount.getDefaultAppointmentCount();

        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)
                .orElse(Priority.NONE_PRIORITY_KEYWORD));

        return new Person(name, phone, email, address, remark, tagList, insurances,
                appointment, count, priority);
    }
}
