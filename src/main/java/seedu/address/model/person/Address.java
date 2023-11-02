package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's address in the address book.
 */
public abstract class Address {

    public static final int MAX_LENGTH = 100;

    public static final String MESSAGE_CONSTRAINTS =
            "Address should not be longer than 100 characters.";

    /**
     * Creates and returns the {@code Address} object with value {@code address}.
     */
    public static Address createAddress(String address) {
        requireNonNull(address);

        String trimmedAddress = address.trim();
        if (trimmedAddress.isEmpty()) {
            return EmptyAddress.getEmptyAddress();
        }

        return new NonEmptyAddress(trimmedAddress);
    }

    /**
     * Returns true if a given string is a valid address.
     */
    public static boolean isValidAddress(String test) {
        requireNonNull(test);

        int addressLen = test.trim().length();
        return addressLen <= MAX_LENGTH;
    }


    /**
     * Returns true if this instance is an empty address.
     */
    public abstract boolean isEmptyAddress();

    /**
     * Returns the address value.
     */
    public abstract String getValue();

    /**
     * Checks if the full address contains a word that starts with the given prefix, ignoring case.
     *
     * @param prefix The prefix to search for.
     * @return False if there is an empty address,
     *     True if any word in the full address starts with the specified prefix,
     *     false otherwise.
     */
    public static boolean isAddressContainsPrefix(Address address, String prefix) {
        if (address.isEmptyAddress()) {
            return false;
        }
        String lowerFullAddress = address.getValue().toLowerCase();
        String lowerPrefix = prefix.toLowerCase();
        for (String lowerAddress: lowerFullAddress.split("\\s+")) {
            if (lowerAddress.startsWith(lowerPrefix)) {
                return true;
            }
        }
        return false;
    }

}
