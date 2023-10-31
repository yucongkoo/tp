package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remark should  not be empty nor longer than 150 characters.";

    private final String value;

    /**
     * Constructs a Remark object.
     *
     * @param remark String that represent the remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        value = remark;
    }

    public static boolean isValidRemark(String remark) {
        return remark.length() <= 150;
    }

    public boolean isEmptyRemark() {
        return Objects.equals(this.value, "");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Checks if the full remark contains a word that starts with the given prefix, ignoring case.
     *
     * @param prefix The prefix to search for.
     * @return True if any word in the remark starts with the specified prefix, false otherwise.
     */
    public static boolean isRemarkContainsPrefix(Remark remark, String prefix) {
        if (remark.isEmptyRemark()) {
            return false;
        }
        String lowerRemark = remark.value.toLowerCase();
        String lowerPrefix = prefix.toLowerCase();
        for (String lowerRemarkPart: lowerRemark.split("\\s+")) {
            if (lowerRemarkPart.startsWith(lowerPrefix)) {
                return true;
            }
        }
        return false;
    }
}
