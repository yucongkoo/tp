package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmptyAddressTest {
    @Test
    public void isEmptyAddress() {
        assertTrue(new EmptyAddress().isEmptyAddress());
    }

    @Test
    public void equals() {
        Address emptyAddress = Address.EMPTY_ADDRESS;

        // empty address -> returns true
        assertTrue(emptyAddress.equals(Address.EMPTY_ADDRESS));

        // same object -> returns true
        assertTrue(emptyAddress.equals(emptyAddress));

        // null -> returns false
        assertFalse(emptyAddress.equals(null));

        // different types -> returns false
        assertFalse(emptyAddress.equals(5.0f));

        // different values -> returns false
        assertFalse(emptyAddress.equals(new Address("Other Valid Address")));

        // address with value "-" -> returns false
        assertFalse(emptyAddress.equals(new Address("-")));
    }
}
