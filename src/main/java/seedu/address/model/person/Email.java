package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {



    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1.The email address should contain at least one character in the local part, "
            + "consisting of letters (both uppercase and lowercase), digits, "
            + "underscores, and certain special characters including +, &, *, and -.\n"
            + "2. The \"@\" symbol is required to separate the local part from the domain part.\n"
            + "3. The domain part of the email address should consist of at "
            + "least one domain label (excluding top-level domain), consisting of alphanumeric characters and -. \n"
            + "4. The top-level domain (TLD) of the email address should consist of letters (uppercase or lowercase) "
            + "and have a length of between 2 and 7 characters.\n";
    private static final String OWASP_EMAIL_VALIDATION = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}";
    public static final String VALIDATION_REGEX = OWASP_EMAIL_VALIDATION;


    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     *
     * The format of email follows the OWASP email validation rule :
     * 1. The email address should contain at least one character in the local part,
     *    consisting of letters (both uppercase and lowercase), digits, underscores,
     *    and certain special characters including +, &, *, and -.
     *
     * 2. The "@" symbol is required to separate the local part from the domain part.
     *
     * 3. The domain part of the email address should consist of at least one domain label,
     *    with each label containing letters, digits, and hyphens, and separated by periods.
     *
     * 4. The top-level domain (TLD) of the email address should consist of letters (uppercase or lowercase)
     *    and have a length of between 2 and 7 characters.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
