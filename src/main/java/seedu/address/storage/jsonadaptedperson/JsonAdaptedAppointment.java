package seedu.address.storage.jsonadaptedperson;

import static java.util.Objects.requireNonNull;
import static seedu.address.storage.jsonadaptedperson.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Appointment;


/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedAppointment.class);

    private final String date;
    private final String time;
    private final String venue;


    /**
     * Constructs a given {@code Appointment} with the given {@code appointment}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("value")String date,
                    @JsonProperty("time") String time, @JsonProperty("venue") String venue) {
        this.date = date;
        this.time = time;
        this.venue = venue;
        logger.info(date + time + venue);
    }

    /**
     * Constructs a {@code JsonAdaptedAppointment}.
     */
    public JsonAdaptedAppointment(Appointment appointment) {
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
        if (date == null || time == null || venue == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Appointment.class.getSimpleName()));
        }

        if (!Appointment.isValidDateFormat(date)) {
            throw new IllegalValueException(Appointment.MESSAGE_CONSTRAINTS);
        }

        if (!Appointment.isValidTimeFormat(time)) {
            throw new IllegalValueException(Appointment.MESSAGE_CONSTRAINTS);
        }

        if (!Appointment.isValidVenueFormat(venue)) {
            throw new IllegalValueException(Appointment.MESSAGE_CONSTRAINTS);
        }

        return new Appointment(date, time, venue);
    }
}
