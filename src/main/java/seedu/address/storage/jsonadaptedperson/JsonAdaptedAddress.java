package seedu.address.storage.jsonadaptedperson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.EmptyAddress;
import seedu.address.model.person.NonEmptyAddress;

/**
 * Jackson-friendly version of {@link Address}.
 */
class JsonAdaptedAddress {

    private final String value;
    private final boolean isEmptyAddress;

    /**
     * Constructs a {@code JsonAdaptedAddress} with the given {@code value} and {@code isEmptyAddress}.
     */
    @JsonCreator
    public JsonAdaptedAddress(@JsonProperty("value") String value,
            @JsonProperty("isEmptyAddress") boolean isEmptyAddress) {
        this.value = value;
        this.isEmptyAddress = isEmptyAddress;
    }

    /**
     * Converts a given {@code Address} into this class for Jackson use.
     */
    public JsonAdaptedAddress(Address address) {
        value = address.getValue();
        isEmptyAddress = address.isEmptyAddress();
    }

    /**
     * Converts this Jackson-friendly adapted address object into the model's {@code Address} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted address.
     */
    public Address toModelType() throws IllegalValueException {
        if (isEmptyAddress) {
            return EmptyAddress.getEmptyAddress();
        }

        if (!NonEmptyAddress.isValidAddress(value)) {
            throw new IllegalValueException(NonEmptyAddress.MESSAGE_CONSTRAINTS);
        }

        return new NonEmptyAddress(value);
    }

}
