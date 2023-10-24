package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AddressTest {
    @Test
    public void isEmptyAddress() {
        // empty address
        assertTrue(EmptyAddress.getEmptyAddress().isEmptyAddress());

        // non-empty address
        assertFalse(new NonEmptyAddress("Some address").isEmptyAddress());

        // address with default non-empty value
        assertFalse(new NonEmptyAddress(EmptyAddress.DUMMY_VALUE_FOR_EMPTY_ADDRESS).isEmptyAddress());
    }

    @Test
    public void getValue() {
        // empty address
        Address emptyAddress = EmptyAddress.getEmptyAddress();
        assertEquals(EmptyAddress.DUMMY_VALUE_FOR_EMPTY_ADDRESS, emptyAddress.getValue());

        // non-empty address
        Address nonEmptyAddress = new NonEmptyAddress("Some address");
        assertEquals("Some address", nonEmptyAddress.getValue());
    }
}
