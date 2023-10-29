package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.EditCommand.PREFIX_EDIT_ERROR_MESSAGE_MAP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new EditCommand object.
 */
public class EditCommandParser implements Parser<EditCommand> {
    private static final Logger logger = LogsCenter.getLogger(EditCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.fine("EditCommandParser parsing...");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_PRIORITY);
        assert argMultimap != null : "ArgumentTokenizer.tokenize returns null";

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.finer("Parsing failed due to invalid command format");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        checkPrefixPresent(argMultimap, PREFIX_TAG, PREFIX_PRIORITY);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        EditPersonDescriptor editPersonDescriptor = getEditPersonDescriptor(argMultimap);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            logger.finer("Parsing failed due to person's information not edited");
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    private EditPersonDescriptor getEditPersonDescriptor(ArgumentMultimap argMultimap) throws ParseException {
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            editPersonDescriptor.setName(name);
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            editPersonDescriptor.setPhone(phone);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            editPersonDescriptor.setEmail(email);
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            editPersonDescriptor.setAddress(address);
        }

        return editPersonDescriptor;
    }

    /**
     * Check if {@code prefixes} are present in the {@code argMultimap}.
     *
     * @param argMultimap that we are performing search on.
     * @param prefixes to search for.
     * @throws ParseException when prefixes are found.
     */
    private void checkPrefixPresent(ArgumentMultimap argMultimap, Prefix... prefixes) throws ParseException {
        String exceptionMessage = "";
        boolean hasInvalidPrefix = false;

        for (Prefix targetPrefix : prefixes) {
            if (argMultimap.getValue(targetPrefix).isPresent()) {
                exceptionMessage += PREFIX_EDIT_ERROR_MESSAGE_MAP.get(targetPrefix) + "\n";
                hasInvalidPrefix = true;
            }
        }

        if (hasInvalidPrefix) {
            logger.finer("Parsing failed due to invalid prefixes provided.");
            throw new ParseException(exceptionMessage);
        }
    }
}
