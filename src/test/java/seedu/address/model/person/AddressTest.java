package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AddressTest {
    private String validAddress = "some valid address";
    private String invalidAddress = PersonTestUtil.generateStringOfLength(Address.MAX_LENGTH + 1);

    @Test
    public void createAddress_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Address.createAddress(null));
    }

    @Test
    public void createAddress_validAddress_returnsNonEmptyAddress() {
        assertEquals(new NonEmptyAddress(validAddress), Address.createAddress(validAddress));
    }

    @Test
    public void createAddress_invalidAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Address.createAddress(invalidAddress));
    }

    @Test
    public void createAddress_emptyString_returnsEmptyAddress() {
        assertEquals(EmptyAddress.getEmptyAddress(), Address.createAddress(""));
    }

    @Test
    public void isValidAddress_emptyString_returnsTrue() {
        assertTrue(Address.isValidAddress(""));
        assertTrue(Address.isValidAddress("  "));
    }

    @Test
    public void isValidAddress_nonEmptyValidString_returnsTrue() {
        assertTrue(Address.isValidAddress(validAddress));
    }

    @Test
    public void isValidAddress_invalidString_returnsFalse() {
        assertFalse(Address.isValidAddress(invalidAddress));
    }

    @Test
    public void isEmptyAddress() {
        // empty address
        assertTrue(EmptyAddress.getEmptyAddress().isEmptyAddress());

        // non-empty address
        assertFalse(Address.createAddress("Some address").isEmptyAddress());

        // address with empty value
        assertTrue(Address.createAddress(EmptyAddress.DUMMY_VALUE_FOR_EMPTY_ADDRESS).isEmptyAddress());
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
