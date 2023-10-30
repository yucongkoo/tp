package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.InsuranceCommand;
import seedu.address.logic.commands.InsuranceCommand.UpdatePersonInsuranceDescriptor;
import seedu.address.logic.commands.PriorityCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.model.person.Person;
import seedu.address.model.priority.Priority;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().getValue() + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.getTagName() + " ")
        );
        person.getInsurances().stream().forEach(
                i -> sb.append(PREFIX_INSURANCE + i.getInsuranceName() + " ")
        );
        sb.append(PREFIX_PRIORITY + person.getPriority().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.getValue()).append(" "));
        return sb.toString();
    }

    public static String getTagCommand(Index index, TagCommand.UpdatePersonTagsDescriptor updatePersonTagsDescriptor) {
        String tagsToAddString = updatePersonTagsDescriptor.getTagsToAdd().stream()
                .map(t -> " " + PREFIX_ADD_TAG + t.getTagName())
                .collect(Collectors.joining());
        String tagsToDeleteString = updatePersonTagsDescriptor.getTagsToDelete().stream()
                .map(t -> " " + PREFIX_DELETE_TAG + t.getTagName())
                .collect(Collectors.joining());

        return TagCommand.COMMAND_WORD + " " + index.getOneBased() + tagsToAddString + tagsToDeleteString;
    }

    public static String getInsuranceCommand(Index index,
                                             UpdatePersonInsuranceDescriptor updatePersonInsuranceDescriptor) {

        String insurancesToAddString = updatePersonInsuranceDescriptor.getInsurancesToAdd().stream()
                .map(i -> " " + PREFIX_ADD_TAG + i.getInsuranceName())
                .collect(Collectors.joining());

        String insurancesToDeleteString = updatePersonInsuranceDescriptor.getInsurancesToDelete().stream()
                .map(i -> " " + PREFIX_DELETE_TAG + i.getInsuranceName())
                .collect(Collectors.joining());

        return InsuranceCommand.COMMAND_WORD + " " + index.getOneBased()
                + insurancesToAddString + insurancesToDeleteString;
    }

    public static String getPriorityCommand(Index index, Priority priority) {
        return PriorityCommand.COMMAND_WORD + " " + index.getOneBased() + " " + priority;
    }
}
