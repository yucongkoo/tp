package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MALE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // alice is first person
    private Index aliceIndex = INDEX_FIRST_PERSON;
    private Person alice = model.getFilteredPersonList().get(aliceIndex.getZeroBased());
    private String aliceTag = "friends";

    // benson is second person
    private Index bensonIndex = Index.fromOneBased(2);
    private Person benson = model.getFilteredPersonList().get(bensonIndex.getZeroBased());
    private String bensonFirstTag = "owesMoney";
    private String bensonSecondTag = "friends";

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCommand(null, new HashSet<>(), new HashSet<>()));
        assertThrows(NullPointerException.class, () ->
                new TagCommand(INDEX_FIRST_PERSON, null, new HashSet<>()));
        assertThrows(NullPointerException.class, () ->
                new TagCommand(INDEX_FIRST_PERSON, new HashSet<>(), null));
    }

    @Test
    public void execute_onlyAddSingleTag_success() {
        Set<Tag> husbandTagSet = Set.of(new Tag(VALID_TAG_HUSBAND));
        Command command = new TagCommand(aliceIndex, husbandTagSet, new HashSet<>());

        Person updatedAlice = new PersonBuilder(alice).withTags(aliceTag, VALID_TAG_HUSBAND).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_onlyAddMultipleTags_success() {
        Set<Tag> husbandAndMaleTagSet = Set.of(new Tag(VALID_TAG_HUSBAND), new Tag(VALID_TAG_MALE));
        Command command = new TagCommand(aliceIndex, husbandAndMaleTagSet, new HashSet<>());

        Person updatedAlice = new PersonBuilder(alice).withTags(aliceTag, VALID_TAG_HUSBAND, VALID_TAG_MALE).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_addDuplicateTag_success() {
        Set<Tag> tagsToBeAdded = Set.of(new Tag(aliceTag));
        Command command = new TagCommand(aliceIndex, tagsToBeAdded, new HashSet<>());

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(alice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_onlyDeleteSingleTag_success() {
        Set<Tag> friendTagSet = Set.of(new Tag(aliceTag));
        Command command = new TagCommand(aliceIndex, new HashSet<>(), friendTagSet);

        Person updatedAlice = new PersonBuilder(alice).withTags().build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyDeleteMultipleTag_success() {
        Set<Tag> tagsToBeRemovedSet = Set.of(new Tag(bensonFirstTag), new Tag(bensonSecondTag));
        Command command = new TagCommand(bensonIndex, new HashSet<>(), tagsToBeRemovedSet);

        Person updatedBenson = new PersonBuilder(benson).withTags().build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedBenson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(benson, updatedBenson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNonExistingTag_success() {
        Set<Tag> tagsToBeRemovedSet = Set.of(new Tag("nonExistingTag"));
        Command command = new TagCommand(aliceIndex, new HashSet<>(), tagsToBeRemovedSet);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(alice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addAndDeleteTags_success() {
        Set<Tag> tagsToBeAddedSet = Set.of(
                new Tag(VALID_TAG_HUSBAND), new Tag(VALID_TAG_MALE), new Tag(bensonFirstTag));
        Set<Tag> tagsToBeDeletedSet = Set.of(new Tag(bensonSecondTag), new Tag("nonExistingTag"));
        Command command = new TagCommand(bensonIndex, tagsToBeAddedSet, tagsToBeDeletedSet);

        Person updatedBenson = new PersonBuilder(benson)
                .withTags(bensonFirstTag, VALID_TAG_HUSBAND, VALID_TAG_MALE).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedBenson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(benson, updatedBenson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexOutOfBound_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonListSize() + 1);
        Command command = new TagCommand(outOfBoundIndex, new HashSet<>(), new HashSet<>());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Index testIndex = INDEX_FIRST_PERSON;
        Set<Tag> testSetToAdd = Set.of(new Tag("tagToAdd"));
        Set<Tag> testSetToDelete = Set.of(new Tag("tagToDelete"));

        TagCommand tagCommand = new TagCommand(testIndex, testSetToAdd, testSetToDelete);

        // same object -> true
        assertTrue(tagCommand.equals(tagCommand));

        // different class -> false
        assertFalse(tagCommand.equals(new Object()));

        // null -> false
        assertFalse(tagCommand.equals(null));

        // same value -> true
        assertTrue(tagCommand.equals(new TagCommand(testIndex, testSetToAdd, testSetToDelete)));

        // different index -> false
        assertFalse(tagCommand.equals(new TagCommand(INDEX_SECOND_PERSON, testSetToAdd, testSetToDelete)));

        // different setToAdd -> false
        assertFalse(tagCommand.equals(new TagCommand(testIndex, new HashSet<>(), testSetToDelete)));

        // different setToDelete -> false
        assertFalse(tagCommand.equals(new TagCommand(testIndex, testSetToAdd, new HashSet<>())));
    }
}
