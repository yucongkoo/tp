package seedu.address.model.person;

/**
 * Represents an empty address.
 */
public class EmptyAddress extends Address {

    public static String DUMMY_VALUE_FOR_EMPTY_ADDRESS = "-";

    /**
     * Constructs an empty address object.
     */
    public EmptyAddress() {
        super(DUMMY_VALUE_FOR_EMPTY_ADDRESS);
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
