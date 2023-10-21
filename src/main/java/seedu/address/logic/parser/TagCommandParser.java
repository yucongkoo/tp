package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TagCommand.UpdatePersonTagsDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    private static final Logger logger = LogsCenter.getLogger(TagCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.fine("TagCommandParser parsing: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ADD_TAG, PREFIX_DELETE_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.finer("TagCommandParser parse failed due to invalid index: " + argMultimap.getPreamble());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }

        Set<Tag> tagsToAdd = parseTags(argMultimap.getAllValues(PREFIX_ADD_TAG)).orElse(new HashSet<>());
        Set<Tag> tagsToDelete = parseTags(argMultimap.getAllValues(PREFIX_DELETE_TAG)).orElse(new HashSet<>());
        UpdatePersonTagsDescriptor updatePersonTagsDescriptor = new UpdatePersonTagsDescriptor(tagsToAdd, tagsToDelete);

        if (!updatePersonTagsDescriptor.hasTagToUpdate()) {
            logger.finer("TagCommandParser parse failed due to missing tags to update");
            throw new ParseException(TagCommand.MESSAGE_NOT_UPDATED);
        }

        return new TagCommand(index, updatePersonTagsDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTags(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseTags(tags));
    }

}
