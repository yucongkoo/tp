package seedu.address.storage.jsonadaptedperson;

import static seedu.address.storage.jsonadaptedperson.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Insurance;

/**
 * Jackson-friendly version of {@code Insurance}
 *
 */
public class JsonAdaptedInsurance {

    private final String insuranceName;

    @JsonCreator
    public JsonAdaptedInsurance(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public JsonAdaptedInsurance(Insurance source) {
        this.insuranceName = source.getInsuranceName();
    }

    /**
     * Convert Json version to model version of {@code Insurance}
     *
     */
    public Insurance toModelType() throws IllegalValueException {
        if (insuranceName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }

        if (!Insurance.isValidInsuranceName(insuranceName)) {
            throw new IllegalValueException(Insurance.MESSAGE_CONSTRAINTS);
        }
        return new Insurance(insuranceName);
    }
    @JsonValue
    public String getInsuranceName() {
        return insuranceName;
    }
}
