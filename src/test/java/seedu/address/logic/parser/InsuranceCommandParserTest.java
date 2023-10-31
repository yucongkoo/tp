package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.InsuranceCommand;
import seedu.address.logic.commands.InsuranceCommand.UpdatePersonInsuranceDescriptor;
import seedu.address.model.insurance.Insurance;

public class InsuranceCommandParserTest {

    private InsuranceCommandParser parser = new InsuranceCommandParser();
    private UpdatePersonInsuranceDescriptor descriptor;
    private Index firstIndex = INDEX_FIRST_PERSON;
    private String invalidFormat = String.format(MESSAGE_INVALID_COMMAND_FORMAT, InsuranceCommand.MESSAGE_USAGE);

    @BeforeEach
    public void setUp() {
        descriptor = new UpdatePersonInsuranceDescriptor(new HashSet<>(), new HashSet<>());
    }
    @Test
    public void parse_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_addOneInsurnance_success() {
        descriptor.setInsurancesToAdd(new Insurance("car insurance"));

        String argument = "1 ai/car insurance";

        InsuranceCommand expectedCommand = new InsuranceCommand(firstIndex, descriptor);

        assertParseSuccess(parser, argument, expectedCommand);
    }

    @Test
    public void parse_addMultipleInsurnance_success() {
        descriptor.setInsurancesToAdd(new Insurance("car insurance"));
        descriptor.setInsurancesToAdd(new Insurance("life insurance"));
        descriptor.setInsurancesToAdd(new Insurance("health insurance"));

        String argument = "1 ai/car insurance ai/life insurance ai/health insurance";

        InsuranceCommand expectedCommand = new InsuranceCommand(firstIndex, descriptor);

        assertParseSuccess(parser, argument, expectedCommand);
    }
    @Test
    public void parse_deleteOneInsurnance_success() {
        descriptor.setInsurancesToDelete(new Insurance("car insurance"));

        String argument = "1 di/car insurance";

        InsuranceCommand expectedCommand = new InsuranceCommand(firstIndex, descriptor);

        assertParseSuccess(parser, argument, expectedCommand);
    }
    @Test
    public void parse_deleteMultipleInsurnance_success() {
        descriptor.setInsurancesToDelete(new Insurance("car insurance"));
        descriptor.setInsurancesToDelete(new Insurance("life insurance"));
        descriptor.setInsurancesToDelete(new Insurance("health insurance"));

        String argument = "1 di/car insurance di/life insurance di/health insurance";

        InsuranceCommand expectedCommand = new InsuranceCommand(firstIndex, descriptor);

        assertParseSuccess(parser, argument, expectedCommand);
    }

    @Test
    public void parse_addDeleteInsurance_success() {
        descriptor.setInsurancesToAdd(new Insurance("car insurance"));
        descriptor.setInsurancesToDelete(new Insurance("life insurance"));

        String argument = "1 ai/car insurance di/life insurance";

        InsuranceCommand expectedCommand = new InsuranceCommand(firstIndex, descriptor);

        assertParseSuccess(parser, argument, expectedCommand);

    }

    @Test
    public void parse_addDeleteMultipleInsurance_success() {
        descriptor.setInsurancesToAdd(new Insurance("car insurance"));
        descriptor.setInsurancesToDelete(new Insurance("life insurance"));

        descriptor.setInsurancesToAdd(new Insurance("AIA insurance"));
        descriptor.setInsurancesToDelete(new Insurance("ABC insurance"));

        String argument = "1 ai/car insurance di/life insurance ai/AIA insurance di/ABC insurance";

        InsuranceCommand expectedCommand = new InsuranceCommand(firstIndex, descriptor);

        assertParseSuccess(parser, argument, expectedCommand);

    }

    @Test
    public void parse_missingArguments_failure() {
        // missing field
        assertParseFailure(parser, "1", InsuranceCommand.MESSAGE_INSURANCE_NO_UPDATE);

        // missing index with add field
        assertParseFailure(parser, "ai/", invalidFormat);

        // missing index with delete field
        assertParseFailure(parser, "di/", invalidFormat);

        // missing both index and field
        assertParseFailure(parser, "", invalidFormat);
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        // negative index
        assertParseFailure(parser, "-1 ai/car insurance", invalidFormat);

        // zero index
        assertParseFailure(parser, "0 ai/car insurance", invalidFormat);

        // alphabets
        assertParseFailure(parser, "abvbvh ai/car insurance", invalidFormat);

        // symbols
        assertParseFailure(parser, "*-+ ai/car insurance", invalidFormat);

        // index mixed with redundant argument
        assertParseFailure(parser, "1 abcdef ai/car insurance", invalidFormat);

        // invalid prefix recognized as preamble
        assertParseFailure(parser, "1 i/AIA ai/car insurance", invalidFormat);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid insurance with add field only
        assertParseFailure(parser, "1 ai/hush*-+", Insurance.MESSAGE_CONSTRAINTS);

        // invalid insurance with delete field only
        assertParseFailure(parser, "1 di/hush*-+", Insurance.MESSAGE_CONSTRAINTS);

        // invalid add insurance with add and delete field
        assertParseFailure(parser, "1 ai/hush*-+ di/abc insurance", Insurance.MESSAGE_CONSTRAINTS);

        // invalid delete insurance with add and delete field
        assertParseFailure(parser, "1 ai/hush insurance di/abc@sg insurance", Insurance.MESSAGE_CONSTRAINTS);

        // both insurances invalid with add and delete field
        assertParseFailure(parser, "1 ai/hush*-+ di/abc@-insurance", Insurance.MESSAGE_CONSTRAINTS);
    }
}
