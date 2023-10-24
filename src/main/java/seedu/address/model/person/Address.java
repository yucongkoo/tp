package seedu.address.model.person;

/**
 * Represents a Person's address in the address book.
 */
public abstract class Address {

    /**
     * Returns true if this instance is an empty address.
     */
    public abstract boolean isEmptyAddress();

    /**
     * Returns the address value.
     */
    public abstract String getValue();

}
