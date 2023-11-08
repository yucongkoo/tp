package seedu.address.model.person;

/**
 * Represents a Person's empty address in the address book as a singleton.
 */
public class EmptyAddress extends Address {

    public static final String DUMMY_VALUE_FOR_EMPTY_ADDRESS = " ";
    private static final String VALUE_FOR_DISPLAY = "-";

    private static EmptyAddress emptyAddress;

    // make constructor private
    private EmptyAddress() {

    }

    /**
     * Returns the singleton instance of {@code EmptyAddress}.
     */
    public static EmptyAddress getEmptyAddress() {
        if (emptyAddress == null) {
            emptyAddress = new EmptyAddress();
        }

        return emptyAddress;
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
        return other == this;
    }

    @Override
    public String toString() {
        return VALUE_FOR_DISPLAY;
    }

}
