package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.model.person.Remark.isValidRemark;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class RemarkTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsParseException() {
        assertThrows(IllegalArgumentException.class, () -> new Remark(INVALID_REMARK_DESC));
    }

    @Test
    public void isValidRemark_invalidRemark_returnsFalse() {
        assertFalse(isValidRemark(INVALID_REMARK_DESC));
    }

    @Test
    public void isValidRemark_validRemark_returnsTrue() {
        assertTrue(isValidRemark("Some Remark"));
        assertTrue(isValidRemark("!@#$%^&*()_+"));
        assertTrue(isValidRemark("AAAbbbCCC\t\n\tDDD"));
        assertTrue(isValidRemark(""));
    }

    @Test
    public void equalsMethod() {
        Remark remark = new Remark(VALID_REMARK_AMY);

        // same object -> return true
        assertTrue(remark.equals(remark));

        // different type -> false
        assertFalse(remark.equals(new Object()));
        assertFalse(remark.equals(null));
        assertFalse(remark.equals(new Phone("12345678")));

        // different value -> false
        assertFalse(remark.equals(new Remark(VALID_REMARK_BOB)));

        // same value -> true
        assertTrue(remark.equals(new Remark(VALID_REMARK_AMY)));
    }
}
