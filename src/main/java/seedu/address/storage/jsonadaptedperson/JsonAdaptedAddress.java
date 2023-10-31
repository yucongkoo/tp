package seedu.address.storage.jsonadaptedperson;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;

/**
 * Jackson-friendly version of {@link Address}.
 */
class JsonAdaptedAddress {

    private final String value;

    /**
     * Constructs a {@code JsonAdaptedAddress} with the given {@code value} and {@code isEmptyAddress}.
     */
    @JsonCreator
    public JsonAdaptedAddress(@JsonProperty("value") String value) {
        this.value = value;
    }

    /**
     * Converts a given {@code Address} into this class for Jackson use.
     */
    public JsonAdaptedAddress(Address address) {
        requireNonNull(address);

        value = address.getValue();
    }

    /**
     * Converts this Jackson-friendly adapted address object into the model's {@code Address} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted address.
     */
    public Address toModelType() throws IllegalValueException {
        if (!Address.isValidAddress(value)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        return Address.createAddress(value);
    }

}
