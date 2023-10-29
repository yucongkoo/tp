package seedu.address.testutil;


import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DERRICK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CALMEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_DERRICK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSURANCE_CAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CALMEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DERRICK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CALMEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DERRICK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_CALMEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withInsurances("car insurance")
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withRemark("Owned me 1M")
            .withInsurances("AIA insurance", "ABC insurance")
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withInsurances("Great Eastern").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822224")
            .withEmail("werner@example.com").withAddress("michegan ave").withInsurances("InsureIQ").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94821427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94828442")
            .withEmail("anna@example.com").withAddress("4th street").withInsurances("GGO Insurance").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84829424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84824131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    public static final Person JOJO = new PersonBuilder().withName("Jojo Best").withPhone("14445656")
            .withEmail("jojo@example.com").withAddress("100th street").build();

    public static final Person KAKA = new PersonBuilder().withName("Kaka Der Many").withPhone("78756232")
            .withEmail("kaka@example.com").withAddress("kaka street").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withRemark(VALID_REMARK_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withRemark(VALID_REMARK_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    // Person without address
    public static final Person CALMEN = new PersonBuilder().withName(VALID_NAME_CALMEN).withPhone(VALID_PHONE_CALMEN)
            .withEmail(VALID_EMAIL_CALMEN).withoutAddress().withRemark(VALID_REMARK_CALMEN).build();

    // Person with priority
    public static final Person DERRICK = new PersonBuilder().withName(VALID_NAME_DERRICK).withPhone(VALID_PHONE_DERRICK)
            .withEmail(VALID_EMAIL_DERRICK).withAddress(VALID_ADDRESS_DERRICK)
            .withInsurances(VALID_INSURANCE_CAR).withPriority(VALID_PRIORITY_HIGH)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, CALMEN, JOJO, KAKA));
    }
}
