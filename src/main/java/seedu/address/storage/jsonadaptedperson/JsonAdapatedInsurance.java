package seedu.address.storage.jsonadaptedperson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insurance.Insurance;

public class JsonAdapatedInsurance {

    private final String insuranceName;

    @JsonCreator
    public JsonAdapatedInsurance(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public JsonAdapatedInsurance(Insurance source) {
        this.insuranceName = source.getInsuranceName();
    }

    public Insurance toModelType() throws IllegalValueException {
        if (!Insurance.isValidInsuranceName(insuranceName)) {
            throw new IllegalValueException(Insurance.MESSAGE_CONSTRAINT);
        }
        return new Insurance(insuranceName);
    }
    @JsonValue
    public String getInsuranceName() {
        return insuranceName;
    }
}
