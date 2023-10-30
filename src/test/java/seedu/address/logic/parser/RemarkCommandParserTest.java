package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.person.Remark.MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PriorityCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;
import seedu.address.model.priority.Priority;
public class RemarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    private static final String REMARK_STUB = "Some remarks.";
    private final Parser<RemarkCommand> parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, REMARK_STUB, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-1" + REMARK_STUB, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + REMARK_STUB, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "second" + REMARK_STUB, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidRemark_failure() {
        assertParseFailure(parser, "1 " + INVALID_REMARK_DESC, MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validFields_success() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // Add/update remark.
        String userInput = targetIndex.getOneBased() + " " + REMARK_STUB;
        RemarkCommand expectedRemarkCommand = new RemarkCommand(targetIndex, new Remark(REMARK_STUB));
        assertParseSuccess(parser, userInput, expectedRemarkCommand);

        // Delete remark
        userInput = targetIndex.getOneBased() + "";
        expectedRemarkCommand = new RemarkCommand(targetIndex, new Remark(""));
        assertParseSuccess(parser, userInput, expectedRemarkCommand);
    }
}
