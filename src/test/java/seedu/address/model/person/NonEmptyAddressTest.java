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
    public void equals() {
        Address address = new NonEmptyAddress("Valid Address");

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
    }
}
