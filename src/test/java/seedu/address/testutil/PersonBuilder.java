package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.AppointmentCount;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmptyAddress;
import seedu.address.model.person.Name;
import seedu.address.model.person.NonEmptyAddress;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Tag;
import seedu.address.model.priority.Priority;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_PRIORITY = "high";
    public static final String DEFAULT_APPOINTMENT = "10 Oct 2021";
    public static final String DEFAULT_APPOINTMENT_TIME = "2000";
    public static final String DEFAULT_APPOINTMENT_VENUE = "Jewel Changi";
    public static final String DEFAULT_APPOINTMENT_COUNT = "0";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;
    private Set<Insurance> insurances;
    private Appointment appointment;
    private AppointmentCount count;
    private Priority priority;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        remark = new Remark(DEFAULT_REMARK);
        address = new NonEmptyAddress(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        insurances = new HashSet<>();
        appointment = new Appointment(DEFAULT_APPOINTMENT, DEFAULT_APPOINTMENT_TIME, DEFAULT_APPOINTMENT_VENUE);
        count = new AppointmentCount(DEFAULT_APPOINTMENT_COUNT);
        priority = new Priority(DEFAULT_PRIORITY);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
        tags = new HashSet<>(personToCopy.getTags());
        insurances = new HashSet<>(personToCopy.getInsurances());
        appointment = personToCopy.getAppointment();
        count = personToCopy.getAppointmentCount();
        priority = personToCopy.getPriority();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Set the tags from {@code tags} to the [@code Person} that we are building.
     *
     * @param tags Set of tags.
     */
    public PersonBuilder withTags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Assigns the customer with {@code Insurance}
     */
    public PersonBuilder withInsurances(String... insurances) {
        this.insurances = SampleDataUtil.getInsuranceSet(insurances);
        return this;
    }

    /**
     * Assigns the customer with {@code Insurance}
     */
    public PersonBuilder withInsurances(Set<Insurance> insurances) {
        this.insurances = insurances;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new NonEmptyAddress(address);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building to
     * be an empty address.
     */
    public PersonBuilder withoutAddress() {
        this.address = EmptyAddress.getEmptyAddress();
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the of the {@code Person} to {@code remark}.
     *
     */
    public PersonBuilder withRemark(Remark remark) {
        this.remark = remark;
        return this;
    }

    /**
     * Sets the {@code Appointment} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppointment(String date, String time, String venue) {
        this.appointment = new Appointment(date, time, venue);
        return this;
    }

    /**
     * Sets the {@code AppointmentCount} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppointmentCount(String count) {
        this.count = new AppointmentCount(count);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Person} that we are building.
     */
    public PersonBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Create the {@code Person} with all information
     */
    public Person build() {
        return new Person(name, phone, email, address, remark, tags, insurances, appointment, count, priority);

    }

}
