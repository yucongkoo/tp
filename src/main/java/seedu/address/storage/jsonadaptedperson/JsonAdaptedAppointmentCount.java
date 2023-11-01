package seedu.address.storage.jsonadaptedperson;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.AppointmentCount;


/**
 * Jackson-friendly version of {@link AppointmentCount}.
 */
class JsonAdaptedAppointmentCount {
    private final String value;

    /**
     * Constructs a given {@code JsonAdaptedAppointmentCount} with the given {@code count}.
     */
    @JsonCreator
    public JsonAdaptedAppointmentCount(@JsonProperty("value") String value) {
        this.value = value;
    }

    /**
     * Constructs a given {@code JsonAdaptedAppointmentCount} into this class for Jackson use.
     */
    public JsonAdaptedAppointmentCount(AppointmentCount count) {
        requireNonNull(count);
        value = String.valueOf(count.getCount());
    }

    /**
     * Converts this Jackson-friendly adapted phone object into the model's {@code AppointmentCount} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment count.
     */
    public AppointmentCount toModelType() throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(AppointmentCount.INVALID_COUNT_INPUT);
        }
        if (!AppointmentCount.isValidCount(value)) {
            throw new IllegalValueException(AppointmentCount.INVALID_COUNT_INPUT);
        }

        return new AppointmentCount(value);
    }
}
