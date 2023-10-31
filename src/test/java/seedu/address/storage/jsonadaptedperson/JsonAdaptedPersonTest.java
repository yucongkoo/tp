package seedu.address.storage.jsonadaptedperson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.jsonadaptedperson.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CALMEN;
import static seedu.address.testutil.TypicalPersons.DERRICK;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.AppointmentCount;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NonEmptyAddress;
import seedu.address.model.person.PersonTestUtil;
import seedu.address.model.person.Phone;
import seedu.address.model.priority.Priority;

public class JsonAdaptedPersonTest {

    private static final JsonAdaptedName INVALID_NAME = new JsonAdaptedName("R@chel");
    private static final JsonAdaptedPhone INVALID_PHONE = new JsonAdaptedPhone("+651234");
    private static final JsonAdaptedAddress INVALID_ADDRESS =
            new JsonAdaptedAddress(PersonTestUtil.generateStringOfLength(NonEmptyAddress.MAX_LENGTH + 1));
    private static final JsonAdaptedEmail INVALID_EMAIL = new JsonAdaptedEmail("example.com");
    private static final String INVALID_TAG = "#friend";
    private static final JsonAdaptedPriority INVALID_PRIORITY = new JsonAdaptedPriority("top");
    private static final String INVALID_INSURANCE = "/*weird insurance";

    private static final JsonAdaptedAppointment INVALID_APPOINTMENT = new JsonAdaptedAppointment(
            new Appointment("10 Oct 2025", "10:00", "Suntec"));
    private static final String INVALID_APPOINTMENT_TIME = "10:00";
    private static final String INVALID_APPOINTMENT_VENUE = "test1test2test3test4test5test6test7";
    private static final String INVALID_APPOINTMENT_COUNT = "-1000";

    private static final JsonAdaptedName VALID_NAME = new JsonAdaptedName(BENSON.getName().toString());
    private static final JsonAdaptedPhone VALID_PHONE = new JsonAdaptedPhone(BENSON.getPhone().toString());
    private static final JsonAdaptedEmail VALID_EMAIL = new JsonAdaptedEmail(BENSON.getEmail().toString());
    private static final JsonAdaptedAddress VALID_ADDRESS = new JsonAdaptedAddress(BENSON.getAddress());
    private static final JsonAdaptedRemark VALID_REMARK = new JsonAdaptedRemark(BENSON.getRemark());
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final List<JsonAdaptedInsurance> VALID_INSURANCES = BENSON.getInsurances().stream()
            .map(JsonAdaptedInsurance::new)
            .collect(Collectors.toList());
    private static final JsonAdaptedAppointment VALID_APPOINTMENT = new JsonAdaptedAppointment(BENSON.getAppointment());
    private static final JsonAdaptedAppointmentCount VALID_APPOINTMENT_COUNT =
            new JsonAdaptedAppointmentCount(BENSON.getAppointmentCount());

    private static final JsonAdaptedPriority VALID_PRIORITY = new JsonAdaptedPriority(DERRICK.getPriority());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_validPersonDetailsWithoutAddress_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(CALMEN);
        assertEquals(CALMEN, person.toModelType());
    }

    @Test
    public void toModelType_validPersonDetailsWithPriority_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(DERRICK);
        assertEquals(DERRICK, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                        VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                        VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                       VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                        VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = NonEmptyAddress.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_REMARK, VALID_TAGS, VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, invalidTags,
                        VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidInsurances_throwsIllegalValueException() {
        List<JsonAdaptedInsurance> invalidInsurances = new ArrayList<>(VALID_INSURANCES);
        invalidInsurances.add(new JsonAdaptedInsurance(INVALID_INSURANCE));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                        invalidInsurances, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = Insurance.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person =

                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                        VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, INVALID_PRIORITY);

        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_REMARK, VALID_TAGS, VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, null);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAppointment_throwsIllegalValueException() {
        JsonAdaptedPerson person =

                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_REMARK, VALID_TAGS,
                        VALID_INSURANCES, VALID_APPOINTMENT, VALID_APPOINTMENT_COUNT, VALID_PRIORITY);

        String expectedMessage = Appointment.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
