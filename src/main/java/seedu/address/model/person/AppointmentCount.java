package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
/**
 * Keeps track of the number of appointment the user have with a client.
 */
public class AppointmentCount {
    public static final String INVALID_COUNT_INPUT = "Appointment count cannot be negative";
    public static final String INITIAL_COUNT = "0";

    private int count;

    /**
     * Constructor for {@Code AppointmentCount}
     *
     * @param count A String representing the number of appointment with a contact.
     */
    public AppointmentCount(String count) {
        requireNonNull(count);
        checkArgument(isValidCount(count), INVALID_COUNT_INPUT);
        this.count = Integer.parseInt(count);
    }

    /**
     * Increases the appointment count by 1.
     *
     * @return The updated AppointmentCount
     */
    public AppointmentCount incrementAppointmentCount() {
        count += 1;
        return this;
    }

    /**
     * Decreases the appointment count by 1.
     *
     * @return The updated AppointmentCount
     */
    public AppointmentCount decrementAppointmentCount() {
        count--;
        return this;
    }

    public int getCount() {
        return this.count;
    }

    /**
     * Checks if the input count is a non-negative integer.
     *
     * @param test The input count string.
     * @return A boolean indicating if the count is valid.
     */
    public static boolean isValidCount(String test) {
        try {
            int n = Integer.parseInt(test);
            return n >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the Appointment count is can be decremented.
     *
     * @param test The current Appointment Count.
     * @return A boolean indicating if the current Appointment Count can be decremented.
     */
    public static boolean isValidDecrementCount(AppointmentCount test) {
        return (test.count >= 1);
    }

    /**
     * Generates the default {@code AppointmentCount}.
     *
     * @return An {@code AppointmentCount} with zero count.
     */
    public static AppointmentCount getDefaultAppointmentCount() {
        return new AppointmentCount(INITIAL_COUNT);
    }

    @Override
    public String toString() {
        return String.valueOf(count);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentCount // instanceof handles nulls
                && count == ((AppointmentCount) other).count); // state check
    }

    @Override
    public int hashCode() {
        return count;
    }
}
