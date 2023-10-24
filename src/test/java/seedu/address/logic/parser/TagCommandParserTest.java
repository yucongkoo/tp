package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADD_TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.DELETE_TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADD_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELETE_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TagCommand.UpdatePersonTagsDescriptor;
import seedu.address.model.tag.Tag;

public class TagCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    private TagCommandParser parser = new TagCommandParser();


    private Index targetIndex = INDEX_FIRST_PERSON;
    private Tag friendTag = new Tag(VALID_TAG_FRIEND);
    private Tag husbandTag = new Tag(VALID_TAG_HUSBAND);

    private UpdatePersonTagsDescriptor updatePersonTagsDescriptor =
            new UpdatePersonTagsDescriptor(new HashSet<>(), new HashSet<>());

    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_onlyAddTag_success() {
        // only add one tag
        updatePersonTagsDescriptor.setTagsToAdd(Set.of(friendTag));
        updatePersonTagsDescriptor.setTagsToDelete(new HashSet<>());
        Command expectedCommand = new TagCommand(targetIndex, updatePersonTagsDescriptor);
        String userInput = String.format("%d %s",
                targetIndex.getOneBased(),
                PREFIX_ADD_TAG + VALID_TAG_FRIEND);
        assertParseSuccess(parser, userInput, expectedCommand);

        // add multiple tags
        updatePersonTagsDescriptor.setTagsToAdd(Set.of(friendTag, husbandTag));
        updatePersonTagsDescriptor.setTagsToDelete(new HashSet<>());
        expectedCommand = new TagCommand(targetIndex, updatePersonTagsDescriptor);
        userInput = String.format("%d %s %s",
                targetIndex.getOneBased(),
                PREFIX_ADD_TAG + VALID_TAG_FRIEND,
                PREFIX_ADD_TAG + VALID_TAG_HUSBAND);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_onlyDeleteTag_success() {
        // only add one tag
        updatePersonTagsDescriptor.setTagsToAdd(new HashSet<>());
        updatePersonTagsDescriptor.setTagsToDelete(Set.of(friendTag));
        Command expectedCommand = new TagCommand(targetIndex, updatePersonTagsDescriptor);
        String userInput = String.format("%d %s",
                targetIndex.getOneBased(),
                PREFIX_DELETE_TAG + VALID_TAG_FRIEND);
        assertParseSuccess(parser, userInput, expectedCommand);

        // add multiple tags
        updatePersonTagsDescriptor.setTagsToAdd(new HashSet<>());
        updatePersonTagsDescriptor.setTagsToDelete(Set.of(friendTag, husbandTag));
        expectedCommand = new TagCommand(targetIndex, updatePersonTagsDescriptor);
        userInput = String.format("%d %s %s",
                targetIndex.getOneBased(),
                PREFIX_DELETE_TAG + VALID_TAG_FRIEND,
                PREFIX_DELETE_TAG + VALID_TAG_HUSBAND);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_bothAddAndDeleteTags_success() {
        updatePersonTagsDescriptor.setTagsToAdd(Set.of(friendTag));
        updatePersonTagsDescriptor.setTagsToDelete(Set.of(husbandTag));
        Command expectedCommand = new TagCommand(targetIndex, updatePersonTagsDescriptor);

        // add tag first before delete tag
        String userInput = String.format("%d %s %s",
                targetIndex.getOneBased(),
                PREFIX_ADD_TAG + VALID_TAG_FRIEND,
                PREFIX_DELETE_TAG + VALID_TAG_HUSBAND);
        assertParseSuccess(parser, userInput, expectedCommand);

        // delete tag first before add tag
        userInput = String.format("%d %s %s",
                targetIndex.getOneBased(),
                PREFIX_DELETE_TAG + VALID_TAG_HUSBAND,
                PREFIX_ADD_TAG + VALID_TAG_FRIEND);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ADD_TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", TagCommand.MESSAGE_NOT_UPDATED);

        // no index and field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ADD_TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ADD_TAG_DESC_FRIEND, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "random string 1", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValues_failure() {
        // invalid tag in add tag
        assertParseFailure(parser, "1" + INVALID_ADD_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + INVALID_ADD_TAG_DESC + DELETE_TAG_DESC_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // invalid tag in delete tag
        assertParseFailure(parser, "1" + INVALID_DELETE_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + ADD_TAG_DESC_FRIEND + INVALID_DELETE_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // both invalid values
        assertParseFailure(parser,
                "1" + INVALID_ADD_TAG_DESC + INVALID_DELETE_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);
    }
}
