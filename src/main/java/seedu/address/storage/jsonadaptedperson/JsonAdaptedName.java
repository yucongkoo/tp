package seedu.address.storage.jsonadaptedperson;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;

/**
 * Jackson-friendly version of {@link Name}.
 */
class JsonAdaptedName {

    private final String value;

    /**
     * Constructs a {@code JsonAdaptedName} with the given {@code value}.
     */
    @JsonCreator
    public JsonAdaptedName(@JsonProperty("value") String value) {
        this.value = value;
    }

    /**
     * Converts a given {@code Name} into this class for Jackson use.
     */
    public JsonAdaptedName(Name name) {
        requireNonNull(name);
        value = name.fullName;
    }

    /**
     * Converts this Jackson-friendly adapted name object into the model's {@code Name} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted name.
     */
    public Name toModelType() throws IllegalValueException {
        if (!Name.isValidName(value)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        return new Name(value);
    }
}
