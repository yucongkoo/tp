package seedu.address.storage.jsonadaptedperson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;

/**
 * Jackson-friendly version of {@link Email}.
 */
class JsonAdaptedEmail {

    private final String value;

    /**
     * Constructs a {@code JsonAdaptedEmail} with the given {@code value}.
     */
    @JsonCreator
    public JsonAdaptedEmail(@JsonProperty("value") String value) {
        this.value = value;
    }

    /**
     * Converts a given {@code Email} into this class for Jackson use.
     */
    public JsonAdaptedEmail(Email email) {
        value = email.value;
    }

    /**
     * Converts this Jackson-friendly adapted email object into the model's {@code Email} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted email.
     */
    public Email toModelType() throws IllegalValueException {
        if (!Email.isValidEmail(value)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        return new Email(value);
    }
}
