package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);
    private static final Prefix[] validPrefixes = new Prefix[] { PREFIX_INSURANCE, PREFIX_NAME, PREFIX_TAG,
            PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_PRIORITY };


    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireAllNonNull(args);

        logger.fine("FindCommandParser parsing...");

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(" " + args, validPrefixes);

        if (!isPrefixPresent(argMultimap, validPrefixes) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(validPrefixes);

        List<Predicate<Person>> predicateList = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicateList.add(ParserUtil.parseNameKeywords(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_INSURANCE).isPresent()) {
            predicateList.add(ParserUtil.parseInsuranceKeywords(argMultimap.getValue(PREFIX_INSURANCE).get()));
        }


        return new FindCommand(new PersonContainsKeywordsPredicate(predicateList));
    }

    public static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        requireAllNonNull(argumentMultimap, prefixes);
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
