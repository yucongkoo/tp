package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_WITH_PREFIX_TAG;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC3;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC2;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DERRICK;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_NONE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_WITH_PREFIX_TAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_PRIORITY_ERROR;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_TAG_ERROR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NonEmptyAddress;
import seedu.address.model.person.Phone;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, NonEmptyAddress.MESSAGE_CONSTRAINTS); // invalid address

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_WITH_PREFIX_TAG;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_WITH_PREFIX_TAG).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_editTag_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String exceptionMessage = MESSAGE_EDIT_TAG_ERROR + "\n";

        // all fields specified with tag
        String userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + TAG_DESC_FRIEND;
        assertParseFailure(parser, userInput, exceptionMessage);

        // all fields specified with invalid tag
        targetIndex = INDEX_SECOND_PERSON;
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + INVALID_TAG_DESC;
        assertParseFailure(parser, userInput, exceptionMessage);

        // all fields specified with empty tag
        targetIndex = INDEX_SECOND_PERSON;
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + INVALID_TAG_DESC2;
        assertParseFailure(parser, userInput, exceptionMessage);
    }

    @Test
    public void parse_editPriority_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String exceptionMessage = MESSAGE_EDIT_PRIORITY_ERROR + "\n";

        // all fields specified with priority high
        String userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + PRIORITY_DESC_HIGH;
        assertParseFailure(parser, userInput, exceptionMessage);

        // all fields specified with priority none
        targetIndex = INDEX_SECOND_PERSON;
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + PRIORITY_DESC_NONE;
        assertParseFailure(parser, userInput, exceptionMessage);

        // invalid priority
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + INVALID_PRIORITY_DESC;
        assertParseFailure(parser, userInput, exceptionMessage);

        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + INVALID_PRIORITY_DESC2;
        assertParseFailure(parser, userInput, exceptionMessage);

        // empty priority
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + INVALID_PRIORITY_DESC3;
        assertParseFailure(parser, userInput, exceptionMessage);
    }

    @Test
    public void parse_bothTagAndPriority_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String exceptionMessage = MESSAGE_EDIT_TAG_ERROR + "\n" + MESSAGE_EDIT_PRIORITY_ERROR + "\n";

        // both tags and priority are valid
        String userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + PRIORITY_DESC_HIGH;
        assertParseFailure(parser, userInput, exceptionMessage);

        // empty tag
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + INVALID_TAG_DESC2 + PRIORITY_DESC_HIGH;
        assertParseFailure(parser, userInput, exceptionMessage);

        // empty priority
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + INVALID_PRIORITY_DESC3;
        assertParseFailure(parser, userInput, exceptionMessage);

        // both tag and priority are empty
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + INVALID_TAG_DESC2 + INVALID_PRIORITY_DESC3;
        assertParseFailure(parser, userInput, exceptionMessage);

        // invalid tag
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + INVALID_TAG_DESC + PRIORITY_DESC_HIGH;
        assertParseFailure(parser, userInput, exceptionMessage);

        // invalid priority
        userInput = targetIndex.getOneBased() + NAME_DESC_DERRICK + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + TAG_DESC_FRIEND + INVALID_PRIORITY_DESC;
        assertParseFailure(parser, userInput, exceptionMessage);
    }
}
