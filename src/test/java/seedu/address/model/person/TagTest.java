package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    // EP: null case
    @Test
    public void isValidTagName_nullTagName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    // EP : valid tag name
    @Test
    public void isValidTagName_validTagName_returnsTrue() {
        assertTrue(Tag.isValidTagName("1")); // boundary value
        assertTrue(Tag.isValidTagName("valid"));
        assertTrue(Tag.isValidTagName("valid1"));
        assertTrue(Tag.isValidTagName("valid1 tag2 name"));
        assertTrue(Tag.isValidTagName("12345678901234567890")); // boundary value
        // boundary value with spaces
        assertTrue(Tag.isValidTagName(" 1 2 3 4 5 6 7   8  9 0 1 2 3 4 5 6 7 8 9 0 "));
    }

    // EP: invalid tag name
    @Test
    public void isValidTagName_invalidTagName_returnsFalse() {
        assertFalse(Tag.isValidTagName("#")); // non-alphanumeric
        assertFalse(Tag.isValidTagName("")); // boundary value: empty string
        assertFalse(Tag.isValidTagName(" "));
        assertFalse(Tag.isValidTagName("123456789012345678901")); // boundary value : too long
    }

    @Test
    public void equals() {
        Tag tag = new Tag("valid tag");

        // same object -> return true
        assertTrue(tag.equals(tag));

        // different type -> false
        assertFalse(tag.equals(new Object()));
        assertFalse(tag.equals(null));

        // different value -> false
        assertFalse(tag.equals(new Tag("other valid tag")));

        // same value -> true
        assertTrue(tag.equals(new Tag("valid tag")));

        // same value with contiguous spaces -> true
        assertTrue(tag.equals(new Tag(" valid    tag ")));
    }
}
