package seedu.address.logic.parser;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_INSURANCE;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.InsuranceCommand;
import seedu.address.logic.commands.InsuranceCommand.UpdatePersonInsuranceDescriptor;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.insurance.Insurance;


/**
 * Parse the required details and creates InsuranceCommand
 *
 */
public class InsuranceCommandParser implements Parser<InsuranceCommand> {

    /**
     * Parse the given arguments and extract out the useful information for InsuranceCommand
     *
     * @param args input arguments
     * @return InsuranceCommand object to be executed
     * @throws ParseException when there is error in the input
     */
    @Override
    public InsuranceCommand parse(String args) throws ParseException {
        Index index = null;

        requireAllNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ADD_INSURANCE, PREFIX_DELETE_INSURANCE);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), e);
        }

        requireAllNonNull(index);

        Set<Insurance> insurancesToAdd =
                parseInsurances(argMultimap.getAllValues(PREFIX_ADD_INSURANCE)).orElse(new HashSet<>());

        Set<Insurance> insurancesToDelete =
                parseInsurances(argMultimap.getAllValues(PREFIX_DELETE_INSURANCE)).orElse(new HashSet<>());

        requireAllNonNull(insurancesToAdd, insurancesToDelete);

        UpdatePersonInsuranceDescriptor changes =
                new UpdatePersonInsuranceDescriptor(insurancesToAdd, insurancesToDelete);

        if (!changes.hasInsuranceToUpdate()) {
            throw new ParseException(InsuranceCommand.MESSAGE_INSURANCE_NO_UPDATE);
        }

        return new InsuranceCommand(index, changes);
    }

    /**
     * Parse the all insurance name values
     *
     * @param insurances all insurance names
     * @return {@code Optional<Set<Insurance>>}
     * @throws ParseException if
     */
    private Optional<Set<Insurance>> parseInsurances(Collection<String> insurances) throws ParseException {
        requireAllNonNull(insurances);

        if (insurances.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ParserUtil.parseInsurances(insurances));
    }
}
