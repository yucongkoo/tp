package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INSURANCE_COUNT_EXCEED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.InsuranceCommand.MESSAGE_INSURANCE_CONFLICT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.InsuranceCommand.UpdatePersonInsuranceDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class InsuranceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Index firstIndex = Index.fromOneBased(1);

    private Index secondIndex = Index.fromOneBased(2);

    private Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredPersonListSize());

    private Person alice = ALICE; //first person in typical address book

    private Person benson = BENSON; // second person in typical address book

    private UpdatePersonInsuranceDescriptor defaultDescriptor;

    private String carInsurance = "car insurance";

    private String healthInsurance = "health insurance";

    private String lifeInsurance = "life insurance";


    @BeforeEach
    public void setUp() {
        defaultDescriptor = new UpdatePersonInsuranceDescriptor(new HashSet<>(), new HashSet<>());
    }

    @Test
    public void constructor_nullArgument_throwException() {

        // both argument are null
        assertThrows(NullPointerException.class, () -> new InsuranceCommand(null, null));

        // UpdatePersonInsuranceDescriptor is null
        assertThrows(NullPointerException.class, () -> new InsuranceCommand(firstIndex, null));

        // Index is null
        assertThrows(NullPointerException.class, () -> new InsuranceCommand(null, defaultDescriptor));

        // insurancesToDelete is null
        assertThrows(NullPointerException.class, () -> new InsuranceCommand(firstIndex,
                new UpdatePersonInsuranceDescriptor(new HashSet<>(), null)));

        // insurancesToAdd is null
        assertThrows(NullPointerException.class, () -> new InsuranceCommand(firstIndex,
                new UpdatePersonInsuranceDescriptor(null, new HashSet<>())));


    }
    @Test
    public void constructor_noInsuranceToUpdate_assertionError() {
        assertThrows(AssertionError.class, () -> new InsuranceCommand(firstIndex, defaultDescriptor));
    }

    @Test
    public void execute_oneInsuranceToUpdate_success() {
        defaultDescriptor.setInsurancesToAdd(new Insurance(healthInsurance));
        InsuranceCommand command = new InsuranceCommand(firstIndex, defaultDescriptor);

        Person updatedAlice = new PersonBuilder(alice).withInsurances(carInsurance, healthInsurance).build();
        String expectedMessage = String.format(InsuranceCommand.MESSAGE_INSURANCE_PERSON_SUCCESS,
                Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_twoInsuranceToUpdate_success() {
        defaultDescriptor.setInsurancesToAdd(new Insurance(healthInsurance));
        defaultDescriptor.setInsurancesToAdd(new Insurance(lifeInsurance));
        InsuranceCommand command = new InsuranceCommand(firstIndex, defaultDescriptor);

        Person updatedAlice = new PersonBuilder(alice)
                .withInsurances(lifeInsurance, carInsurance, healthInsurance).build();

        String expectedMessage = String.format(InsuranceCommand.MESSAGE_INSURANCE_PERSON_SUCCESS,
                Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_oneInsuranceToDelete_success() {
        defaultDescriptor.setInsurancesToDelete(new Insurance(carInsurance));
        InsuranceCommand command = new InsuranceCommand(firstIndex, defaultDescriptor);

        Person updatedAlice = new PersonBuilder(alice).withInsurances(new HashSet<>()).build();
        String expectedMessage = String.format(InsuranceCommand.MESSAGE_INSURANCE_PERSON_SUCCESS,
                Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_twoInsuranceToDelete_success() {
        defaultDescriptor.setInsurancesToDelete(new Insurance("AIA insurance"));
        defaultDescriptor.setInsurancesToDelete(new Insurance("ABC insurance"));

        InsuranceCommand command = new InsuranceCommand(secondIndex, defaultDescriptor);

        Person updatedBenson = new PersonBuilder(benson).withInsurances(new HashSet<>()).build();
        String expectedMessage = String.format(InsuranceCommand.MESSAGE_INSURANCE_PERSON_SUCCESS,
                Messages.format(updatedBenson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(benson, updatedBenson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneInsuranceToUpdateDelete_success() {
        defaultDescriptor.setInsurancesToAdd(new Insurance(healthInsurance));
        defaultDescriptor.setInsurancesToDelete(new Insurance(carInsurance));

        InsuranceCommand command = new InsuranceCommand(firstIndex, defaultDescriptor);

        Person updatedAlice = new PersonBuilder(alice).withInsurances(healthInsurance).build();
        String expectedMessage = String.format(InsuranceCommand.MESSAGE_INSURANCE_PERSON_SUCCESS,
                Messages.format(updatedAlice));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(alice, updatedAlice);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_insuranceCountAtLimit_success() {
        defaultDescriptor.setInsurancesToAdd(new Insurance(carInsurance));
        defaultDescriptor.setInsurancesToAdd(new Insurance(healthInsurance));
        defaultDescriptor.setInsurancesToAdd(new Insurance(lifeInsurance));

        InsuranceCommand command = new InsuranceCommand(secondIndex, defaultDescriptor);

        Person updatedBenson = new PersonBuilder(benson)
                .withInsurances("AIA insurance", "ABC insurance", carInsurance, healthInsurance, lifeInsurance)
                .build();

        String expectedMessage = String.format(InsuranceCommand.MESSAGE_INSURANCE_PERSON_SUCCESS,
                Messages.format(updatedBenson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(benson, updatedBenson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }


    @Test
    public void execute_conflictingAddDelete_throwsCommandException() {
        defaultDescriptor.setInsurancesToAdd(new Insurance(healthInsurance));
        defaultDescriptor.setInsurancesToDelete(new Insurance(healthInsurance));

        InsuranceCommand command = new InsuranceCommand(firstIndex, defaultDescriptor);

        assertCommandFailure(command, model, MESSAGE_INSURANCE_CONFLICT);
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() {
        defaultDescriptor.setInsurancesToAdd(new Insurance(healthInsurance));

        InsuranceCommand command = new InsuranceCommand(outOfBoundIndex, defaultDescriptor);

        assertCommandFailure(command, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_exceedInsuranceMaxCount_throwsCommandException() {
        defaultDescriptor.setInsurancesToAdd(new Insurance(healthInsurance));
        defaultDescriptor.setInsurancesToAdd(new Insurance(carInsurance));
        defaultDescriptor.setInsurancesToAdd(new Insurance(lifeInsurance));
        defaultDescriptor.setInsurancesToAdd(new Insurance("Great eastern insurance"));

        InsuranceCommand command = new InsuranceCommand(secondIndex, defaultDescriptor);

        assertCommandFailure(command, model, MESSAGE_INSURANCE_COUNT_EXCEED);
    }

    @Test
    public void execute_nullArgument_throwsNullPointerException() {
        defaultDescriptor.setInsurancesToAdd(new Insurance(healthInsurance));
        InsuranceCommand command = new InsuranceCommand(firstIndex, defaultDescriptor);

        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void equals() {
        defaultDescriptor.setInsurancesToAdd(new Insurance(healthInsurance));
        UpdatePersonInsuranceDescriptor newDescriptor =
                new UpdatePersonInsuranceDescriptor(new HashSet<>(), new HashSet<>());
        newDescriptor.setInsurancesToAdd(new Insurance(carInsurance));

        InsuranceCommand command = new InsuranceCommand(firstIndex, defaultDescriptor);
        InsuranceCommand sameCommand = new InsuranceCommand(firstIndex, defaultDescriptor);
        InsuranceCommand diffIndexCommand = new InsuranceCommand(secondIndex, defaultDescriptor);
        InsuranceCommand diffDescriptorCommand = new InsuranceCommand(firstIndex, newDescriptor);

        // null value
        assertFalse(command.equals(null));

        // object of different type
        assertFalse(command.equals(0.5f));

        // different Index
        assertFalse(command.equals(diffIndexCommand));

        // different UpdatePersonInsuranceDescriptor
        assertFalse(command.equals(diffDescriptorCommand));

        // same object
        assertTrue(command.equals(command));

        // same command
        assertTrue(command.equals(sameCommand));

    }

    @Test
    public void updatePersonInsuranceDescriptor_equals() {
        HashSet<Insurance> toAddSame = new HashSet<>();
        toAddSame.add(new Insurance(carInsurance));

        HashSet<Insurance> toAddDiff = new HashSet<>();
        toAddDiff.add(new Insurance(healthInsurance));

        HashSet<Insurance> toDeleteSame = new HashSet<>();
        toDeleteSame.add(new Insurance(lifeInsurance));

        HashSet<Insurance> toDeleteDiff = new HashSet<>();
        toDeleteDiff.add(new Insurance("AIA Insurance"));

        defaultDescriptor.setInsurancesToAdd(new Insurance(carInsurance));
        defaultDescriptor.setInsurancesToDelete(new Insurance(lifeInsurance));

        UpdatePersonInsuranceDescriptor addSameDeleteSameDescriptor =
                new UpdatePersonInsuranceDescriptor(toAddSame, toDeleteSame);

        UpdatePersonInsuranceDescriptor addDiffDeleteSameDescriptor =
                new UpdatePersonInsuranceDescriptor(toAddDiff, toDeleteSame);

        UpdatePersonInsuranceDescriptor addSameDeleteDiffDescriptor =
                new UpdatePersonInsuranceDescriptor(toAddSame, toDeleteDiff);

        UpdatePersonInsuranceDescriptor addDiffDeleteDiffDescriptor =
                new UpdatePersonInsuranceDescriptor(toAddDiff, toDeleteDiff);

        // insurancesToDelete and insurancesToAdd are different
        assertFalse(defaultDescriptor.equals(addDiffDeleteDiffDescriptor));

        // insurancesToDelete is different
        assertFalse(defaultDescriptor.equals(addSameDeleteDiffDescriptor));

        // insurancesToAdd is different
        assertFalse(defaultDescriptor.equals(addDiffDeleteSameDescriptor));

        // object of different type
        assertFalse(defaultDescriptor.equals(0.5f));

        // null value
        assertFalse(defaultDescriptor.equals(null));

        // same object
        assertTrue(defaultDescriptor.equals(defaultDescriptor));

        // object with same insurancesToAdd and insurancesToDelete
        assertTrue(defaultDescriptor.equals(addSameDeleteSameDescriptor));

    }
}
