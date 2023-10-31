package seedu.address.model.util;

import static seedu.address.model.person.Address.createAddress;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.AppointmentCount;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Appointment EMPTY_APPOINTMENT = new Appointment(Appointment.NO_APPOINTMENT,
            Appointment.NO_TIME, Appointment.NO_VENUE);
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                createAddress("Blk 30 Geylang Street 29, #06-40"), new Remark(""),
                getTagSet("friends"), getInsuranceSet("car insurance"),
                    EMPTY_APPOINTMENT, new AppointmentCount("0")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                createAddress("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark(""),
                getTagSet("colleagues", "friends"),
                    getInsuranceSet("car insurance", "AIA insurance"),
            EMPTY_APPOINTMENT, new AppointmentCount("0")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                createAddress("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""),
                getTagSet("neighbours"), getInsuranceSet("life insurance"),
                    EMPTY_APPOINTMENT, new AppointmentCount("0")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                createAddress("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark(""),
                getTagSet("family"), getInsuranceSet("car insurance", "life insurance"),
                    new Appointment("11 Mar 2023", "", ""), new AppointmentCount("0")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                createAddress("Blk 47 Tampines Street 20, #17-35"), new Remark(""),
                getTagSet("classmates"), getInsuranceSet("health insurance"),
                    new Appointment("15 Mar 2023", "2359", ""), new AppointmentCount("0")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                createAddress("Blk 45 Aljunied Street 85, #11-31"), new Remark(""),
                getTagSet("colleagues"), getInsuranceSet("ABC insurance"),
                    new Appointment("15 Mar 2023", "2359", "SK CC"), new AppointmentCount("0"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Set<Insurance> getInsuranceSet(String... strings) {
        return Arrays.stream(strings)
                .map(s -> new Insurance(s))
                .collect(Collectors.toSet());
    }

}
