package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Person's non-empty address in the address book.
 */
public class NonEmptyAddress extends Address {

    private final String value;

    /**
     * Constructs an {@code NonEmptyAddress}.
     *
     * @param address A valid address.
     */
    public NonEmptyAddress(String address) {
        requireNonNull(address);
        checkArgument(isValidNonEmptyAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    @Override
    public boolean isEmptyAddress() {
        return false;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NonEmptyAddress)) {
            return false;
        }

        NonEmptyAddress otherAddress = (NonEmptyAddress) other;
        return value.equals(otherAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    private static boolean isValidNonEmptyAddress(String address) {
        requireNonNull(address);

        return isValidAddress(address) && !address.isEmpty();
    }
}
