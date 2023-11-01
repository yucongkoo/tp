package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_VENUE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_CAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_NONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.model.person.Person.createPersonWithUpdatedPriority;
import static seedu.address.model.person.Person.createPersonWithUpdatedTags;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DERRICK;
import static seedu.address.testutil.TypicalPersons.INITIAL_COUNT;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.insurance.Insurance;
import seedu.address.model.priority.Priority;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {
    // dummy persons used for testing
    private Person bobWithoutTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).build();
    private Person bobWithFriendTag = new PersonBuilder(bobWithoutTags).withTags(VALID_TAG_FRIEND).build();
    private Person bobWithHusbandTag = new PersonBuilder(bobWithoutTags).withTags(VALID_TAG_HUSBAND).build();
    private Person bobWithHusbandAndFriendTag = new PersonBuilder(bobWithoutTags)
            .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();

    private Set<Tag> friendTagSet = Set.of(new Tag(VALID_TAG_FRIEND));
    private Set<Tag> husbandTagSet = Set.of(new Tag(VALID_TAG_HUSBAND));
    private Set<Tag> husbandAndFriendTagset = Set.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND));
    private Set<Tag> emptyTagSet = new HashSet<>();



    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void constructor_oneNullFieldWithPriority_throwsNullPointerException() {
        Name validName = new Name(VALID_NAME_BOB);
        Phone validPhone = new Phone(VALID_PHONE_BOB);
        Email validEmail = new Email(VALID_EMAIL_BOB);
        Address validAddress = new NonEmptyAddress(VALID_ADDRESS_BOB);
        Remark validRemark = new Remark(VALID_REMARK_AMY);
        Set<Tag> validTags = new HashSet<>() {{
                add(new Tag(VALID_TAG_FRIEND));
            }};
        Set<Insurance> validInsurances = new HashSet<>() {{
                add(new Insurance(VALID_INSURANCE_CAR));
            }};
        Priority validPriority = new Priority(VALID_PRIORITY_HIGH);
        Appointment validAppointment = new Appointment(VALID_APPOINTMENT_BOB,
                VALID_APPOINTMENT_TIME_BOB, VALID_APPOINTMENT_VENUE_BOB);
        AppointmentCount validAppointmentCount = new AppointmentCount(INITIAL_COUNT);

        // with priority field
        assertThrows(NullPointerException.class, () -> new Person(null, validPhone, validEmail, validAddress,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, null, validEmail, validAddress,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, null, validAddress,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, null,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                null, validTags, validInsurances, validAppointment, validAppointmentCount, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                validRemark, null, validInsurances, validAppointment, validAppointmentCount, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                validRemark, validTags, null, validAppointment, validAppointmentCount, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount, null));
    }

    @Test
    public void constructor_oneNullFieldWithoutPriority_throwsNullPointerException() {
        Name validName = new Name(VALID_NAME_BOB);
        Phone validPhone = new Phone(VALID_PHONE_BOB);
        Email validEmail = new Email(VALID_EMAIL_BOB);
        Address validAddress = new NonEmptyAddress(VALID_ADDRESS_BOB);
        Remark validRemark = new Remark(VALID_REMARK_AMY);
        Set<Tag> validTags = new HashSet<>() {
            {
                add(new Tag(VALID_TAG_FRIEND));
            }
        };
        Set<Insurance> validInsurances = new HashSet<>() {
            {
                add(new Insurance(VALID_INSURANCE_CAR));
            }
        };
        Appointment validAppointment = new Appointment(VALID_APPOINTMENT_BOB,
                VALID_APPOINTMENT_TIME_BOB, VALID_APPOINTMENT_VENUE_BOB);
        AppointmentCount validAppointmentCount = new AppointmentCount(INITIAL_COUNT);

        // without priority field
        assertThrows(NullPointerException.class, () -> new Person(null, validPhone, validEmail, validAddress,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount));
        assertThrows(NullPointerException.class, () -> new Person(validName, null, validEmail, validAddress,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, null, validAddress,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, null,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                null, validTags, validInsurances, validAppointment, validAppointmentCount));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                validRemark, null, validInsurances, validAppointment, validAppointmentCount));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                validRemark, validTags, null, validAppointment, validAppointmentCount));
    }

    @Test
    public void constructor_allFieldsValid_success() {
        Name validName = new Name(VALID_NAME_BOB);
        Phone validPhone = new Phone(VALID_PHONE_BOB);
        Email validEmail = new Email(VALID_EMAIL_BOB);
        Address validAddress = new NonEmptyAddress(VALID_ADDRESS_BOB);
        Remark validRemark = new Remark(VALID_REMARK_BOB);
        Set<Tag> validTags = new HashSet<>() {{
                add(new Tag(VALID_TAG_FRIEND));
                add(new Tag(VALID_TAG_HUSBAND));
            }};
        Set<Insurance> validInsurances = new HashSet<>() {{
                add(new Insurance(VALID_INSURANCE_CAR));
            }};
        Priority validPriority = new Priority(VALID_PRIORITY_NONE);
        Appointment validAppointment = new Appointment(VALID_APPOINTMENT_BOB,
                VALID_APPOINTMENT_TIME_BOB, VALID_APPOINTMENT_VENUE_BOB);
        AppointmentCount validAppointmentCount = new AppointmentCount(INITIAL_COUNT);

        Person expectedPerson = new PersonBuilder(BOB).withInsurances(VALID_INSURANCE_CAR)
                .withAppointment(validAppointment).withPriority("-").build();

        // with priority field

        Person testPerson = new Person(validName, validPhone, validEmail, validAddress,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount, validPriority);
        assertEquals(expectedPerson, testPerson);

        // without priority field
        testPerson = new Person(validName, validPhone, validEmail, validAddress,
                validRemark, validTags, validInsurances, validAppointment, validAppointmentCount);

        assertEquals(expectedPerson, testPerson);

    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same phone number, all other attributes different -> returns true
        Person newPhoneAmy = new PersonBuilder(AMY).withPhone(VALID_PHONE_BOB).build();
        assertTrue(BOB.isSamePerson(newPhoneAmy));

        // same email, all other attributes different -> returns true
        Person newEmailAmy = new PersonBuilder(AMY).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(BOB.isSamePerson(newEmailAmy));

        // same phone number and email, all other attributes different -> returns true
        Person editedAmy = new PersonBuilder(AMY).withEmail(VALID_EMAIL_BOB).withPhone(VALID_PHONE_BOB).build();
        assertTrue(BOB.isSamePerson(editedAmy));

        // different phone number and email, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withEmail(VALID_EMAIL_AMY).withPhone(VALID_PHONE_AMY).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // all attributes different -> returns false
        assertFalse(ALICE.isSamePerson(BOB));
    }

    @Test
    public void createPersonWithUpdatedTagsTest_noUpdate_success() {
        // both empty
        assertEquals(bobWithHusbandAndFriendTag,
                createPersonWithUpdatedTags(bobWithHusbandAndFriendTag, emptyTagSet, emptyTagSet));

        // add existing tag
        assertEquals(bobWithHusbandAndFriendTag,
                createPersonWithUpdatedTags(bobWithHusbandAndFriendTag, friendTagSet, emptyTagSet));
        assertEquals(bobWithHusbandAndFriendTag,
                createPersonWithUpdatedTags(bobWithHusbandAndFriendTag, husbandAndFriendTagset, emptyTagSet));

        // delete non-existing tag
        assertEquals(bobWithHusbandAndFriendTag, createPersonWithUpdatedTags(bobWithHusbandAndFriendTag,
                emptyTagSet, Set.of(new Tag("non existing tag"))));
    }

    @Test
    public void createPersonWithUpdatedTagsTest_addTag_success() {
        // adding all non-existing tags
        assertEquals(bobWithHusbandAndFriendTag,
                createPersonWithUpdatedTags(bobWithoutTags, husbandAndFriendTagset, emptyTagSet));

        // adding tags with some already existing
        assertEquals(bobWithHusbandAndFriendTag,
                createPersonWithUpdatedTags(bobWithFriendTag, husbandAndFriendTagset, emptyTagSet));
    }

    @Test
    public void createPersonWithUpdatedTagTest_deleteTag_success() {
        // deleting only existing-tag
        assertEquals(bobWithoutTags,
                createPersonWithUpdatedTags(bobWithHusbandAndFriendTag, emptyTagSet, husbandAndFriendTagset));

        // deleting both existing and non-existing tag
        Set<Tag> tagsToDelete = new HashSet<>(husbandAndFriendTagset);
        tagsToDelete.add(new Tag("non existing tag"));
        assertEquals(bobWithoutTags,
                createPersonWithUpdatedTags(bobWithHusbandAndFriendTag, emptyTagSet, tagsToDelete));
    }

    @Test
    public void createPersonWithUpdatedTagTest_addAndDeleteTag_success() {
        // adding new tag and deleting existing tag of a person
        assertEquals(bobWithHusbandTag, createPersonWithUpdatedTags(bobWithFriendTag, husbandTagSet, friendTagSet));

        // adding existing tag and deleting existing tag of a person
        assertEquals(bobWithFriendTag,
                createPersonWithUpdatedTags(bobWithHusbandAndFriendTag, friendTagSet, husbandTagSet));

        // adding new tag and deleting non-existing tag of a person
        assertEquals(bobWithFriendTag, createPersonWithUpdatedTags(bobWithoutTags, friendTagSet, husbandTagSet));

        // adding existing tag and deleting non-existing tag of a person
        assertEquals(bobWithFriendTag, createPersonWithUpdatedTags(bobWithFriendTag, friendTagSet, husbandTagSet));
    }

    @Test
    public void createPersonWithUpdatedPriorityMethod() {
        Person derrickWithHighPriority = new PersonBuilder(DERRICK).withPriority(VALID_PRIORITY_HIGH).build();
        Person derrickWithNoPriority = new PersonBuilder(DERRICK).withPriority(VALID_PRIORITY_NONE).build();
        Person derrickWithLowPriority = new PersonBuilder(DERRICK).withPriority(VALID_PRIORITY_LOW).build();

        Priority highPriority = new Priority(VALID_PRIORITY_HIGH);
        Priority noPriority = new Priority(VALID_PRIORITY_NONE);
        Priority lowPriority = new Priority(VALID_PRIORITY_LOW);

        // assigning priority from none to high
        assertEquals(derrickWithHighPriority, createPersonWithUpdatedPriority(derrickWithNoPriority, highPriority));

        // assigning priority from low to none
        assertEquals(derrickWithNoPriority, createPersonWithUpdatedPriority(derrickWithLowPriority, noPriority));

        // assigning priority from high to low
        assertEquals(derrickWithLowPriority, createPersonWithUpdatedPriority(derrickWithHighPriority, lowPriority));

        // assigning same level of priority
        assertEquals(derrickWithHighPriority, createPersonWithUpdatedPriority(derrickWithHighPriority, highPriority));
    }

    @Test
    public void equalsMethod() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different priority -> returns false
        editedAlice = new PersonBuilder(ALICE).withPriority(VALID_PRIORITY_LOW).build(); // default priority is high
        assertFalse(ALICE.equals(editedAlice));

        editedAlice = new PersonBuilder(ALICE).withAppointment(VALID_APPOINTMENT_BOB,
                VALID_APPOINTMENT_TIME_BOB, VALID_APPOINTMENT_VENUE_BOB).build(); // default appointment is empty
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", priority=" + ALICE.getPriority() + ", tags=" + ALICE.getTags()
                + ", insurances=" + ALICE.getInsurances() + ", remark=" + ALICE.getRemark()
                + ", appointment=" + ALICE.getAppointment() + "}";

        assertEquals(expected, ALICE.toString());
    }
}
