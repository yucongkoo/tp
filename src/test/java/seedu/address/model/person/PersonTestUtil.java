package seedu.address.model.person;

/**
 * Contains helper method for testing persons.
 */
public class PersonTestUtil {

    /**
     * Generates a String of length {@code len}.
     */
    public static String generateStringOfLength(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            sb.append("a");
        }
        return sb.toString();
    }
}
