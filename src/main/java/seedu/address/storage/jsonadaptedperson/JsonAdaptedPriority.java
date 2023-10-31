package seedu.address.storage.jsonadaptedperson;

import static seedu.address.storage.jsonadaptedperson.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.priority.Priority;

/**
 * Jackson-friendly version of {@link Priority}.
 */
public class JsonAdaptedPriority {
    private final String value;

    /**
     * Constructs a {@code JsonAdaptedPriority} with the given {@code value}.
     */
    @JsonCreator
    public JsonAdaptedPriority(@JsonProperty("value") String value) {
        this.value = value;
    }

    /**
     * Converts a given {@code Priority} into this class for Jackson use.
     */
    public JsonAdaptedPriority(Priority priority) {
        value = priority.toString();
    }

    /**
     * Converts this Jackson-friendly adapted priority object into the model's {@code Priority} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted priority.
     */
    public Priority toModelType() throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }

        if (!Priority.isValidPriority(value)) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }

        return new Priority(value);
    }
}
