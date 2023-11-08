package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmptyAddressTest {
    @Test
    public void equals() {
        Address emptyAddress = EmptyAddress.getEmptyAddress();

        // empty address -> returns true
        assertTrue(emptyAddress.equals(EmptyAddress.getEmptyAddress()));

        // same object -> returns true
        assertTrue(emptyAddress.equals(emptyAddress));

        // null -> returns false
        assertFalse(emptyAddress.equals(null));

        // different types -> returns false
        assertFalse(emptyAddress.equals(5.0f));

        // non-empty address with different value -> returns false
        assertFalse(emptyAddress.equals(new NonEmptyAddress("Other Valid Address")));

        // non-empty address with same value -> returns false
        assertTrue(emptyAddress.equals(Address.createAddress(EmptyAddress.DUMMY_VALUE_FOR_EMPTY_ADDRESS)));
    }
}
