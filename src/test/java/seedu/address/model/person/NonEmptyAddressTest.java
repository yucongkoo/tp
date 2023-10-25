package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NonEmptyAddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NonEmptyAddress(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new NonEmptyAddress(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> NonEmptyAddress.isValidAddress(null));

        // invalid addresses
        assertFalse(NonEmptyAddress.isValidAddress("")); // empty string
        assertFalse(NonEmptyAddress.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(NonEmptyAddress.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(NonEmptyAddress.isValidAddress("-")); // one character
        // long address
        assertTrue(NonEmptyAddress.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
    }

    @Test
    public void equals() {
        Address address = new NonEmptyAddress("Valid Address");
        Address addressWithValueOfEmptyAddress = new NonEmptyAddress(EmptyAddress.DUMMY_VALUE_FOR_EMPTY_ADDRESS);

        // same values -> returns true
        assertTrue(address.equals(new NonEmptyAddress("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new NonEmptyAddress("Other Valid Address")));

        // empty address -> returns false
        assertFalse(address.equals(EmptyAddress.getEmptyAddress()));

        // valid address with same value as empty address should not be equal to empty address
        assertFalse(addressWithValueOfEmptyAddress.equals(EmptyAddress.getEmptyAddress()));
    }
}
