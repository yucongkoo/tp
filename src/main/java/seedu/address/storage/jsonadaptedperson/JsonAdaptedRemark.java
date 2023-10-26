package seedu.address.storage.jsonadaptedperson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Remark;
import seedu.address.model.priority.Priority;

/**
 * Jackson-friendly version of {@link Priority}.
 */
public class JsonAdaptedRemark {
    private final String value;

    /**
     * Constructs a {@code JsonAdaptedRemark} with the given {@code value}.
     */
    @JsonCreator
    public JsonAdaptedRemark(@JsonProperty("value") String value) {
        this.value = value;
    }

    /**
     * Converts a given {@code Remark} into this class for Jackson use.
     */
    public JsonAdaptedRemark(Remark remark) {
        value = remark.toString();
    }

    /**
     * Converts this Jackson-friendly adapted priority object into the model's {@code Remark} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted remark.
     */
    public Remark toModelType() throws IllegalValueException {
        if (!Remark.isValidRemark(value)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }

        return new Remark(value);
    }
}
