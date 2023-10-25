package seedu.address.storage.jsonadaptedperson;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Phone;

/**
 * Jackson-friendly version of {@link Phone}.
 */
class JsonAdaptedPhone {

    private final String value;

    /**
     * Constructs a {@code JsonAdaptedPhone} with the given {@code value}.
     */
    @JsonCreator
    public JsonAdaptedPhone(@JsonProperty("value") String value) {
        this.value = value;
    }

    /**
     * Converts a given {@code Phone} into this class for Jackson use.
     */
    public JsonAdaptedPhone(Phone phone) {
        requireNonNull(phone);
        value = phone.value;
    }

    /**
     * Converts this Jackson-friendly adapted phone object into the model's {@code Phone} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted phone.
     */
    public Phone toModelType() throws IllegalValueException {
        if (!Phone.isValidPhone(value)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }

        return new Phone(value);
    }
}
