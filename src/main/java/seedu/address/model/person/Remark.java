package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {
    private final String value;

    public static final String MESSAGE_CONSTRAINTS = "Remark should not be longer than 150 characters.";

    public static final String REMARK_TITLE = "Remark: ";

    public static final String REMARK_TITLE_NO_REMARK = "No remark";

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
}
