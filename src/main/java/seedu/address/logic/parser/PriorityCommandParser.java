package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.priority.Priority;

/**
 * Parses input arguments and creates a new PriorityCommand object.
 */
public class PriorityCommandParser implements Parser<PriorityCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ParserCommand
     * and returns a ParserCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public PriorityCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        String[] separatedArgs = trimmedArgs.split(" ");

        Index index;

        try {
            index = ParserUtil.parseIndex(separatedArgs[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE), pe);
        }

        if (separatedArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE));
        }

        Priority priority = ParserUtil.parsePriority(separatedArgs[1]);

        return new PriorityCommand(index, priority);
    }
}
