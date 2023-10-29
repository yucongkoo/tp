package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/** Contains utility methods for commands. **/
public class CommandUtil {
    private static final Logger logger = LogsCenter.getLogger(CommandUtil.class);
    /**
     * Returns the person from the model at a specific index.
     */
    public static Person getPersonToUpdate(Model model, Index index) throws CommandException {
        requireAllNonNull(model, index);
        List<Person> lastShownList = model.getFilteredPersonList();

        assert lastShownList.size() >= 0 : "Size of list should not be a negative number";
        assert index.getZeroBased() >= 0 : "index.getZeroBased() should not be a negative number";

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.finer(String.format("Execution failed due to index %d out of bound", index.getOneBased()));
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        assert personToUpdate != null : "null instance in filtered person list";

        return personToUpdate;
    }
}
