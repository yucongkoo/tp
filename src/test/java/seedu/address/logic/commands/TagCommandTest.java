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
import seedu.address.model.person.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.UpdatePersonTagsDescriptorBuilder;

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
        assertThrows(NullPointerException.class, () -> new TagCommand(null,
                new UpdatePersonTagsDescriptorBuilder().build()));

        assertThrows(NullPointerException.class, () ->
                new TagCommand(INDEX_FIRST_PERSON, null));

        assertThrows(NullPointerException.class, () ->
                new TagCommand(INDEX_FIRST_PERSON, new UpdatePersonTagsDescriptor(null, new HashSet<>())));

        assertThrows(NullPointerException.class, () ->
                new TagCommand(INDEX_FIRST_PERSON, new UpdatePersonTagsDescriptor(new HashSet<>(), null)));
    }

    @Test
    public void constructor_noTagToUpdate_assertionError() {
        assertThrows(AssertionError.class, () ->
                new TagCommand(INDEX_FIRST_PERSON, new UpdatePersonTagsDescriptorBuilder().build()));
    }

    @Test
    public void execute_onlyAddSingleTag_success() {
        UpdatePersonTagsDescriptor descriptor =
                new UpdatePersonTagsDescriptorBuilder().withTagsToAdd(VALID_TAG_HUSBAND).build();
        Command command = new TagCommand(aliceIndex, descriptor);

        Person updatedAlice = new PersonBuilder(alice).withTags(aliceTag, VALID_TAG_HUSBAND).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_onlyAddMultipleTags_success() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToAdd(VALID_TAG_HUSBAND, VALID_TAG_MALE).build();
        Command command = new TagCommand(aliceIndex, descriptor);

        Person updatedAlice = new PersonBuilder(alice).withTags(aliceTag, VALID_TAG_HUSBAND, VALID_TAG_MALE).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_addDuplicateTag_success() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToAdd(bensonFirstTag, VALID_TAG_MALE).build();
        Command command = new TagCommand(bensonIndex, descriptor);

        Person updatedBenson = new PersonBuilder(benson)
                .withTags(bensonFirstTag, bensonSecondTag, VALID_TAG_MALE).build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedBenson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(benson, updatedBenson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateTag_failure() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder().withTagsToAdd(aliceTag).build();
        Command command = new TagCommand(aliceIndex, descriptor);
        assertCommandFailure(command, model, Messages.MESSAGE_PERSON_NOT_CHANGED);
    }


    @Test
    public void execute_onlyDeleteSingleTag_success() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToDelete(aliceTag).build();
        Command command = new TagCommand(aliceIndex, descriptor);

        Person updatedAlice = new PersonBuilder(alice).withTags().build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyDeleteMultipleTag_success() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToDelete(bensonFirstTag, bensonSecondTag).build();
        Command command = new TagCommand(bensonIndex, descriptor);

        Person updatedBenson = new PersonBuilder(benson).withTags().build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedBenson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(benson, updatedBenson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNonExistingTag_success() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToDelete(aliceTag, "nonExistingTag").build();
        Command command = new TagCommand(aliceIndex, descriptor);

        Person updatedAlice = new PersonBuilder(alice).withTags().build();
        String expectedMessage = String.format(TagCommand.MESSAGE_TAG_PERSON_SUCCESS, Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteNonExistingTag_failure() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToDelete("nonExistingTag").build();
        Command command = new TagCommand(aliceIndex, descriptor);
        assertCommandFailure(command, model, Messages.MESSAGE_PERSON_NOT_CHANGED);
    }

    @Test
    public void execute_addAndDeleteTags_success() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToAdd(VALID_TAG_HUSBAND, VALID_TAG_MALE, bensonFirstTag)
                .withTagsToDelete(bensonSecondTag, "nonExistingTag")
                .build();
        Command command = new TagCommand(bensonIndex, descriptor);

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
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToAdd("tagToAdd").build();
        Command command = new TagCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addAndDeleteCommonTag_failure() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToAdd("commonTag").withTagsToDelete("commonTag").build();
        Command command = new TagCommand(aliceIndex, descriptor);
        assertCommandFailure(command, model, TagCommand.MESSAGE_COMMON_TAG_FAILURE);

        descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToAdd("commonTag", "validTag").withTagsToDelete("commonTag").build();
        command = new TagCommand(aliceIndex, descriptor);
        assertCommandFailure(command, model, TagCommand.MESSAGE_COMMON_TAG_FAILURE);

        descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToAdd("commonTag").withTagsToDelete("commonTag", "validTag").build();
        command = new TagCommand(aliceIndex, descriptor);
        assertCommandFailure(command, model, TagCommand.MESSAGE_COMMON_TAG_FAILURE);
    }

    @Test
    public void execute_tagCountExceedLimit_failure() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withEnumeratedTagsToAdd(9).build();
        Command command = new TagCommand(bensonIndex, descriptor);
        assertCommandFailure(command, model, Messages.MESSAGE_TAG_COUNT_EXCEED);

        descriptor = new UpdatePersonTagsDescriptorBuilder().withEnumeratedTagsToAdd(10).build();
        command = new TagCommand(bensonIndex, descriptor);
        assertCommandFailure(command, model, Messages.MESSAGE_TAG_COUNT_EXCEED);
    }

    @Test
    public void execute_tagCountAtLimit_success() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withEnumeratedTagsToAdd(8).build();
        Command command = new TagCommand(bensonIndex, descriptor);

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
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder()
                .withTagsToAdd("tagToAdd").withTagsToDelete("tagToDelete").build();
        TagCommand tagCommand = new TagCommand(testIndex, descriptor);

        Set<Tag> testSetToAdd = getTagSet("tagToAdd");
        Set<Tag> testSetToDelete = getTagSet("tagToDelete");

        assertTrue(tagCommand.equals(tagCommand)); // same object -> true
        assertFalse(tagCommand.equals(new Object())); // different class -> false
        assertFalse(tagCommand.equals(null)); // null -> false

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

    @Test
    public void updatePersonDescriptorEquals() {
        UpdatePersonTagsDescriptor descriptor = new UpdatePersonTagsDescriptorBuilder().build();

        assertTrue(descriptor.equals(descriptor)); // same object -> true

        assertFalse(descriptor.equals(new Object())); // different object type -> false

        assertFalse(descriptor.equals(null)); // null -> false
    }
}
