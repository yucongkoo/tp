package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

import java.util.logging.Logger;


/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    private static final Logger logger = LogsCenter.getLogger(RemarkCommandParser.class);

    /**
     * Parses input and return the RemarkCommand object.
     *
     * @param args Input string.
     * @return Corresponding RemarkCommand Object.
     * @throws ParseException When there is an exception.
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.fine("RemarkCommandParser parsing...");

        String trimmedArgs = args.trim();
        String[] separatedArgs = trimmedArgs.split("\\s+", 2);

        assert separatedArgs.length >= 1 : "separatedArgs length is smaller than 1";
        Index index;

        try {
            index = ParserUtil.parseIndex(separatedArgs[0]);
        } catch (IllegalValueException ive) {
            logger.finer("Parsing failed due to invalid command format");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemarkCommand.MESSAGE_USAGE), ive);
        }

        Remark remark;

        if (separatedArgs.length == 1) {
            remark = ParserUtil.parseRemark("");
        } else {
            remark = ParserUtil.parseRemark(separatedArgs[1]);
        }

        return new RemarkCommand(index, remark);
    }

}
