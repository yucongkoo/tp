package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MALE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.TagCommand.UpdatePersonTagsDescriptor;
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

    private UpdatePersonTagsDescriptor updatePersonTagsDescriptor =
            new UpdatePersonTagsDescriptor(new HashSet<>(), new HashSet<>());

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCommand(null,
                new UpdatePersonTagsDescriptor(new HashSet<>(), new HashSet<>())));

        assertThrows(NullPointerException.class, () ->
                new TagCommand(INDEX_FIRST_PERSON, null));

        assertThrows(NullPointerException.class, () ->
                new TagCommand(INDEX_FIRST_PERSON, new UpdatePersonTagsDescriptor(null, new HashSet<>())));

        assertThrows(NullPointerException.class, () ->
                new TagCommand(INDEX_FIRST_PERSON, new UpdatePersonTagsDescriptor(new HashSet<>(), null)));
    }

    @Test
    public void execute_onlyAddSingleTag_success() {
        updatePersonTagsDescriptor.setTagsToAdd(getTagSet(VALID_TAG_HUSBAND));
        updatePersonTagsDescriptor.setTagsToDelete(new HashSet<>());
        Command command = new TagCommand(aliceIndex, updatePersonTagsDescriptor);

        Person updatedAlice = new PersonBuilder(alice).withTags(aliceTag, VALID_TAG_HUSBAND).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_onlyAddMultipleTags_success() {
        updatePersonTagsDescriptor.setTagsToAdd(getTagSet(VALID_TAG_HUSBAND, VALID_TAG_MALE));
        updatePersonTagsDescriptor.setTagsToDelete(new HashSet<>());
        Command command = new TagCommand(aliceIndex, updatePersonTagsDescriptor);

        Person updatedAlice = new PersonBuilder(alice).withTags(aliceTag, VALID_TAG_HUSBAND, VALID_TAG_MALE).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_addDuplicateTag_success() {
        updatePersonTagsDescriptor.setTagsToAdd(getTagSet(aliceTag));
        updatePersonTagsDescriptor.setTagsToDelete(new HashSet<>());
        Command command = new TagCommand(aliceIndex, updatePersonTagsDescriptor);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(alice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_onlyDeleteSingleTag_success() {
        updatePersonTagsDescriptor.setTagsToAdd(new HashSet<>());
        updatePersonTagsDescriptor.setTagsToDelete(getTagSet(aliceTag));
        Command command = new TagCommand(aliceIndex, updatePersonTagsDescriptor);

        Person updatedAlice = new PersonBuilder(alice).withTags().build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyDeleteMultipleTag_success() {
        updatePersonTagsDescriptor.setTagsToAdd(new HashSet<>());
        updatePersonTagsDescriptor.setTagsToDelete(getTagSet(bensonFirstTag, bensonSecondTag));
        Command command = new TagCommand(bensonIndex, updatePersonTagsDescriptor);

        Person updatedBenson = new PersonBuilder(benson).withTags().build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedBenson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(benson, updatedBenson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNonExistingTag_success() {
        updatePersonTagsDescriptor.setTagsToAdd(new HashSet<>());
        updatePersonTagsDescriptor.setTagsToDelete(getTagSet("nonExistingTag"));
        Command command = new TagCommand(aliceIndex, updatePersonTagsDescriptor);

        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(alice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addAndDeleteTags_success() {
        Set<Tag> tagsToBeAdded = getTagSet(VALID_TAG_HUSBAND, VALID_TAG_MALE, bensonFirstTag);
        Set<Tag> tagsToBeDeleted = getTagSet(bensonSecondTag, "nonExistingTag");

        updatePersonTagsDescriptor.setTagsToAdd(tagsToBeAdded);
        updatePersonTagsDescriptor.setTagsToDelete(tagsToBeDeleted);
        Command command = new TagCommand(bensonIndex, updatePersonTagsDescriptor);

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
        updatePersonTagsDescriptor.setTagsToAdd(getTagSet("tagToAdd"));
        Command command = new TagCommand(outOfBoundIndex, updatePersonTagsDescriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addAndDeleteCommonTag_failure() {
        updatePersonTagsDescriptor.setTagsToAdd(getTagSet("commonTag"));
        updatePersonTagsDescriptor.setTagsToDelete(getTagSet("commonTag"));
        Command command = new TagCommand(aliceIndex, updatePersonTagsDescriptor);
        assertCommandFailure(command, model, TagCommand.MESSAGE_COMMON_TAG_FAILURE);

        updatePersonTagsDescriptor.setTagsToAdd(getTagSet("commonTag", "validTag"));
        updatePersonTagsDescriptor.setTagsToDelete(getTagSet("commonTag"));
        command = new TagCommand(aliceIndex, updatePersonTagsDescriptor);
        assertCommandFailure(command, model, TagCommand.MESSAGE_COMMON_TAG_FAILURE);

        updatePersonTagsDescriptor.setTagsToAdd(getTagSet("commonTag"));
        updatePersonTagsDescriptor.setTagsToDelete(getTagSet("commonTag", "validTag"));
        command = new TagCommand(aliceIndex, updatePersonTagsDescriptor);
        assertCommandFailure(command, model, TagCommand.MESSAGE_COMMON_TAG_FAILURE);
    }

    @Test
    public void execute_tagCountExceedLimit_failure() {
        Set<Tag> nineTags = getTagSet("1", "2", "3", "4", "5", "6", "7", "8", "9");
        Set<Tag> tenTags = getTagSet("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        updatePersonTagsDescriptor.setTagsToAdd(nineTags);
        Command command = new TagCommand(bensonIndex, updatePersonTagsDescriptor);
        assertCommandFailure(command, model, Messages.MESSAGE_TAG_COUNT_EXCEED);

        updatePersonTagsDescriptor.setTagsToAdd(tenTags);
        command = new TagCommand(bensonIndex, updatePersonTagsDescriptor);
        assertCommandFailure(command, model, Messages.MESSAGE_TAG_COUNT_EXCEED);
    }

    @Test
    public void execute_tagCountAtLimit_success() {
        Set<Tag> eightTags = getTagSet("1", "2", "3", "4", "5", "6", "7", "8");

        updatePersonTagsDescriptor.setTagsToAdd(eightTags);
        Command command = new TagCommand(bensonIndex, updatePersonTagsDescriptor);

        Person updatedBenson = new PersonBuilder(benson)
                .withTags(bensonFirstTag, bensonSecondTag, "1", "2", "3", "4", "5", "6", "7", "8").build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedBenson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(benson, updatedBenson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Index testIndex = INDEX_FIRST_PERSON;
        Set<Tag> testSetToAdd = getTagSet("tagToAdd");
        Set<Tag> testSetToDelete = getTagSet("tagToDelete");
        updatePersonTagsDescriptor.setTagsToAdd(testSetToAdd);
        updatePersonTagsDescriptor.setTagsToDelete(testSetToDelete);

        TagCommand tagCommand = new TagCommand(testIndex, updatePersonTagsDescriptor);

        // same object -> true
        assertTrue(tagCommand.equals(tagCommand));

        // different class -> false
        assertFalse(tagCommand.equals(new Object()));

        // null -> false
        assertFalse(tagCommand.equals(null));

        // same value -> true
        assertTrue(tagCommand.equals(new TagCommand(testIndex,
                new UpdatePersonTagsDescriptor(testSetToAdd, testSetToDelete))));

        // different index -> false
        assertFalse(tagCommand.equals(new TagCommand(INDEX_SECOND_PERSON,
                new UpdatePersonTagsDescriptor(testSetToAdd, testSetToDelete))));

        // different setToAdd -> false
        assertFalse(tagCommand.equals(new TagCommand(testIndex,
                new UpdatePersonTagsDescriptor(new HashSet<>(), testSetToDelete))));

        // different setToDelete -> false
        assertFalse(tagCommand.equals(new TagCommand(testIndex,
                new UpdatePersonTagsDescriptor(testSetToAdd, new HashSet<>()))));
    }
}
