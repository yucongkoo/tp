package seedu.address.storage.jsonadaptedperson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insurance.Insurance;

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
