package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Insurance.isValidInsuranceName;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class InsuranceTest {

    private String invalidArgument = "  ";

    @Test
    public void constructor_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()-> new Insurance(null));
    }

    @Test
    public void constructor_invalidArgument_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Insurance(invalidArgument));
    }

    @Test
    public void isValidInsuranceName_validName_success() {
        // alphabets below 32 characters
        assertTrue(isValidInsuranceName("abcdef"));

        // alphabets below 32 characters with space
        assertTrue(isValidInsuranceName("abc def"));

        // alphabets and numbers below 32 characters
        assertTrue(isValidInsuranceName("abcdef123456"));

        // alphabets and numbers with spaces below 32 characters
        assertTrue(isValidInsuranceName("abcdef 123456"));

        // exactly 32 characters
        assertTrue(isValidInsuranceName("1245678901234567890123456789012"));
    }

    @Test
    public void isValidInsuranceName_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()-> isValidInsuranceName(null));
    }

    @Test
    public void isValidInsuranceName_invalidName_fail() {
        // empty string
        assertFalse(isValidInsuranceName(""));

        // empty spaces
        assertFalse(isValidInsuranceName("  "));

        // symbols only below 32 characters
        assertFalse(isValidInsuranceName("/*-+"));

        // symbols and alphabets below 32 characters
        assertFalse(isValidInsuranceName("abcdef/*-+"));

        // symbols and numbers below 32 characters
        assertFalse(isValidInsuranceName("123456/*-+"));

        // symbols, alphabets, numbers under 32 characters
        assertFalse(isValidInsuranceName("abcdef12345/*-+"));

        // numbers exceeding 32 characters
        assertFalse(isValidInsuranceName("123456789012345678901234567890123456789"));

        // alphabets exceeding 32 characters
        assertFalse(isValidInsuranceName("zxcvbnmasdfghjklqwertyuiopxcvbnmasdfghjkl"));

        // alphabets and numbers with spaces exceeding 32 characters
        assertFalse(isValidInsuranceName("xcvbnm asdfghjk qwertyuio 123456789 plmijn"));

        // symbols exceeding 32 characters
        assertFalse(isValidInsuranceName("/*/*-/*- /*-/*/*- /*-/*-/*- -+-+-+-+-+-+-+-+-+-!@#$%^&*!@#$%^&*"));
    }

    @Test
    public void equals() {
        Insurance carInsurance = new Insurance("car insurance");

        Insurance newCarInsurance = new Insurance("car insurance");

        Insurance healthInsurance = new Insurance("health insurance");

        // same object
        assertTrue(carInsurance.equals(carInsurance));

        // same name
        assertTrue(carInsurance.equals(newCarInsurance));

        // null value
        assertFalse(carInsurance.equals(null));

        // different object type
        assertFalse(carInsurance.equals(0.5f));

        // different name
        assertFalse(carInsurance.equals(healthInsurance));

    }
}
