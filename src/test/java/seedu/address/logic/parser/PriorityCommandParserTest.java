package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_NONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.priority.Priority.MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PriorityCommand;
import seedu.address.model.priority.Priority;

public class PriorityCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PriorityCommand.MESSAGE_USAGE);
    private final Parser<PriorityCommand> parser = new PriorityCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PRIORITY_HIGH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_PRIORITY_HIGH, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_PRIORITY_HIGH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "one" + VALID_PRIORITY_HIGH, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPriority_failure() {
        assertParseFailure(parser, "1 " + INVALID_PRIORITY, MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 " + INVALID_PRIORITY2, MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 " + INVALID_PRIORITY3, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validFields_success() {
        Index targetIndex = INDEX_FIRST_PERSON;

        String userInput = targetIndex.getOneBased() + " " + VALID_PRIORITY_HIGH;
        Priority highPriority = new Priority(Priority.HIGH_PRIORITY_KEYWORD);
        assertParseSuccess(parser, userInput, new PriorityCommand(targetIndex, highPriority));

        userInput = targetIndex.getOneBased() + " " + VALID_PRIORITY_NONE;
        Priority nonePriority = new Priority(Priority.NONE_PRIORITY_KEYWORD);
        assertParseSuccess(parser, userInput, new PriorityCommand(targetIndex, nonePriority));
    }
}
