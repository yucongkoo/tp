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

    /**
     * Checks if the full name contains a word that starts with the given prefix, ignoring case.
     *
     * @param prefix The prefix to search for.
     * @return True if any word in the full name starts with the specified prefix, false otherwise.
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
