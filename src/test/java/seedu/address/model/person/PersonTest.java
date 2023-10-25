package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DERRICK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_NONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.model.person.Person.createPersonWithUpdatedPriority;
import static seedu.address.model.person.Person.createPersonWithUpdatedTags;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DERRICK;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

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
    public void constructor_oneNullField_throwsNullPointerException() {
        Name validName = new Name(VALID_NAME_BOB);
        Phone validPhone = new Phone(VALID_PHONE_BOB);
        Email validEmail = new Email(VALID_EMAIL_BOB);
        Address validAddress = new NonEmptyAddress(VALID_ADDRESS_BOB);
        Set<Tag> validTags = new HashSet<>() {{
                add(new Tag(VALID_TAG_FRIEND));
            }};
        Priority validPriority = new Priority(VALID_PRIORITY_HIGH);

        // with priority field
        assertThrows(NullPointerException.class, () -> new Person(null, validPhone, validEmail, validAddress,
                validTags, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, null, validEmail, validAddress,
                validTags, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, null, validAddress,
                validTags, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, null,
                validTags, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                null, validPriority));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                validTags, null));

        // without priority field
        assertThrows(NullPointerException.class, () -> new Person(null, validPhone, validEmail, validAddress,
                validTags));
        assertThrows(NullPointerException.class, () -> new Person(validName, null, validEmail, validAddress,
                validTags));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, null, validAddress,
                validTags));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, null,
                validTags));
        assertThrows(NullPointerException.class, () -> new Person(validName, validPhone, validEmail, validAddress,
                null));
    }

    @Test
    public void constructor_allFieldsValid_success() {
        Person expectedPerson = new PersonBuilder(BOB).withPriority("-").build();
        Name validName = new Name(VALID_NAME_BOB);
        Phone validPhone = new Phone(VALID_PHONE_BOB);
        Email validEmail = new Email(VALID_EMAIL_BOB);
        Address validAddress = new NonEmptyAddress(VALID_ADDRESS_BOB);
        Set<Tag> validTags = new HashSet<>() {{
                add(new Tag(VALID_TAG_FRIEND));
                add(new Tag(VALID_TAG_HUSBAND));
            }};
        Priority validPriority = new Priority(VALID_PRIORITY_NONE);

        // with priority field
        Person testPerson = new Person(validName, validPhone, validEmail, validAddress, validTags, validPriority);
        assertEquals(expectedPerson, testPerson);

        // without priority field
        testPerson = new Person(validName, validPhone, validEmail, validAddress, validTags);
        assertEquals(expectedPerson, testPerson);

    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).withPriority(VALID_PRIORITY_HIGH)
                .build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        Person editedDerrick = new PersonBuilder(DERRICK).withName(VALID_NAME_DERRICK.toLowerCase()).build();
        assertFalse(DERRICK.isSamePerson(editedDerrick));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));

        nameWithTrailingSpaces = VALID_NAME_DERRICK + " ";
        editedDerrick = new PersonBuilder(DERRICK).withName(nameWithTrailingSpaces).build();
        assertFalse(DERRICK.isSamePerson(editedDerrick));
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
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", priority=" + ALICE.getPriority() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
