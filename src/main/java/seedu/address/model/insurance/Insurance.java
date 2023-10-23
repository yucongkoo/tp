package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the insurance plan the customer currently holds or plans to purchase
 * Guarantees: immutable; is valid as declared in {@link #isValidInsuranceName(String)}
 *
 */
public class Insurance {

    public static final String MESSAGE_CONSTRAINT = "";
    public static int MAX_INSURANCE_PERSON = 5;
    private static String VALIDATION_REGEX = "";
    private static int MAX_LENGTH = 32;


    private String insuranceName;

    /**
     * Constructs an {@code Insurance}.
     *
     * @param name A valid insurance plan.
     */
    public Insurance(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        checkArgument(isValidInsuranceName(trimmedName), MESSAGE_CONSTRAINT);
        this.insuranceName = trimmedName;
    }

    /**
     * Checks validity of insurance name
     *
     * @param test insurance name
     * @return true if insurance name is valid
     */
    public static boolean isValidInsuranceName(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Insurance)) {
            return false;
        }
        Insurance temp = (Insurance) o;

        return insuranceName.equals(temp.getInsuranceName());
    }

    @Override
    public int hashCode() {
        return insuranceName.hashCode();
    }
    @Override
    public String toString() {
        return '[' + insuranceName + ']';
    }

    public String getInsuranceName() {
        return insuranceName;
    }
}