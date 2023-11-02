package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.priority.Priority;

/**
 * Parses input arguments and creates a new PriorityCommand object.
 */
public class PriorityCommandParser implements Parser<PriorityCommand> {
    private static final Logger logger = LogsCenter.getLogger(PriorityCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ParserCommand
     * and returns a ParserCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public PriorityCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.fine("PriorityCommandParser parsing...");

        String trimmedArgs = args.trim();
        String[] separatedArgs = trimmedArgs.split("\\s+", 2);

        assert separatedArgs.length >= 1 : "separatedArgs length is smaller than 1";
        Index index;

        try {
            index = ParserUtil.parseIndex(separatedArgs[0]);
        } catch (ParseException pe) {
            logger.finer("Parsing failed due to invalid command format");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE), pe);
        }

        if (separatedArgs.length != 2) {
            logger.finer("Parsing failed due to invalid command format");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
        }

        Priority priority = ParserUtil.parsePriority(separatedArgs[1]);

        return new PriorityCommand(index, priority);
    }
}
