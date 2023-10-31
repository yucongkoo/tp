package seedu.address.storage.jsonadaptedperson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Appointment;

import static java.util.Objects.requireNonNull;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {

    private final String date;
    private final String time;
    private final String venue;


    /**
     * Constructs a given {@code Appointment} with the given {@code appointment}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("value")Appointment appointment) {
        requireNonNull(appointment);
        date = appointment.getDate();
        time = appointment.getTime();
        venue = appointment.getVenue();
    }

    /**
     * Converts this Jackson-friendly adapted phone object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(Appointment.INVALID_DATE_INPUT);
        }
        if (!Appointment.isValidDateFormat(date)) {
            throw new IllegalValueException(Appointment.INVALID_DATE_INPUT);
        }

        if (!Appointment.isValidTimeFormat(time)) {
            throw new IllegalValueException(Appointment.INVALID_TIME_INPUT);
        }

        if (!Appointment.isValidVenueFormat(venue)) {
            throw new IllegalValueException(Appointment.INVALID_VENUE_INPUT);
        }

        return new Appointment(date, time, venue);
    }
}
