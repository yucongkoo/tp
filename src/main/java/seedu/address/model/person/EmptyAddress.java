package seedu.address.model.person;

/**
 * Represents a Person's empty address in the address book as a singleton.
 */
public class EmptyAddress extends Address {

    public static final String DUMMY_VALUE_FOR_EMPTY_ADDRESS = "-";

    public static final EmptyAddress EMPTY_ADDRESS = new EmptyAddress();

    // make constructor private
    private EmptyAddress() {

    }

    @Override
    public boolean isEmptyAddress() {
        return true;
    }

    @Override
    public String getValue() {
        return DUMMY_VALUE_FOR_EMPTY_ADDRESS;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof EmptyAddress;
    }

    @Override
    public String toString() {
        return DUMMY_VALUE_FOR_EMPTY_ADDRESS;
    }

}
