package seedu.address.model.person;

/**
 * Represents an empty address.
 */
public class EmptyAddress extends Address {
    /**
     * Constructs an empty address object.
     */
    public EmptyAddress() {
        super("-");
    }

    @Override
    public boolean isEmptyAddress() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof EmptyAddress;
    }
}
