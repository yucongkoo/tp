package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the insurance plan the customer currently holds or plans to purchase
 * Guarantees: immutable; is valid as declared in {@link #isValidInsuranceName(String)}
 *
 */
public class Insurance {

    public static final String VALIDATION_REGEX = "\\p{Alnum}+(\\s+\\p{Alnum}+)*";
    public static final int MAX_INSURANCE_COUNT = 8;
    private static final int MAX_LENGTH = 32;

    public static final String MESSAGE_CONSTRAINTS =
            String.format("Insurance name should be alphanumeric,"
                    + "non-empty and not longer than %d characters", MAX_LENGTH);
    private final String insuranceName;

    /**
     * Constructs an {@code Insurance}.
     *
     * @param name A valid insurance plan.
     */
    public Insurance(String name) {
        requireNonNull(name);
        String trimmedName = name.trim();
        checkArgument(isValidInsuranceName(trimmedName), MESSAGE_CONSTRAINTS);
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
        return test.matches(VALIDATION_REGEX) && test.replaceAll("\\s", "").length() <= MAX_LENGTH;
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

    /**
     * Checks if the insurance name contains a word that starts with the given prefix, ignoring case.
     *
     * @param prefix The prefix to search for.
     * @return True if any word in the insurance name starts with the specified prefix, false otherwise.
     */
    public static boolean isInsuranceContainsPrefix(Insurance insurance, String prefix) {
        String lowerInsuranceName = insurance.insuranceName.toLowerCase();
        String lowerPrefix = prefix.toLowerCase();
        for (String lowerNamePart: lowerInsuranceName.split("\\s+")) {
            if (lowerNamePart.startsWith(lowerPrefix)) {
                return true;
            }
        }
        return false;
    }
}
