package seedu.address.storage.jsonadaptedperson;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.priority.Priority;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
public class JsonAdaptedPerson {

    //TODO: UPDATE JsonAdaptedPerson

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final JsonAdaptedName name;
    private final JsonAdaptedPhone phone;
    private final JsonAdaptedEmail email;
    private final JsonAdaptedAddress address;
    private final JsonAdaptedRemark remark;
    private final JsonAdaptedPriority priority;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    private final List<JsonAdaptedInsurance> insurances = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") JsonAdaptedName name,
                             @JsonProperty("phone") JsonAdaptedPhone phone,
                             @JsonProperty("email") JsonAdaptedEmail email,
                             @JsonProperty("address") JsonAdaptedAddress address,
                             @JsonProperty("remark") JsonAdaptedRemark remark,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("insurance") List<JsonAdaptedInsurance> insurances,
                             @JsonProperty("priority") JsonAdaptedPriority priority) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (insurances != null) {
            this.insurances.addAll(insurances);
        }
        this.priority = priority;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        requireNonNull(source);

        name = new JsonAdaptedName(source.getName());
        phone = new JsonAdaptedPhone(source.getPhone());
        email = new JsonAdaptedEmail(source.getEmail());
        address = new JsonAdaptedAddress(source.getAddress());
        remark = new JsonAdaptedRemark(source.getRemark());
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        insurances.addAll(source.getInsurances()
                .stream()
                .map(JsonAdaptedInsurance::new)
                .collect(Collectors.toList()));
        priority = new JsonAdaptedPriority(source.getPriority());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        return new Person(getModelName(), getModelPhone(), getModelEmail(), getModelAddress(), getModelRemark(),
                getModelTags(), getModelInsurances(), getModelPriority());

    }

    private Name getModelName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        return name.toModelType();
    }

    private Phone getModelPhone() throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        return phone.toModelType();
    }

    private Email getModelEmail() throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        return email.toModelType();
    }

    private Address getModelAddress() throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        return address.toModelType();
    }

    private Remark getModelRemark() throws IllegalValueException {
        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        return remark.toModelType();
    }

    private Set<Tag> getModelTags() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        return new HashSet<>(personTags);
    }

    private Set<Insurance> getModelInsurances() throws IllegalValueException {
        List<Insurance> personInsurances = new ArrayList<>();

        for (JsonAdaptedInsurance i : insurances) {
            personInsurances.add(i.toModelType());
        }

        return new HashSet<>(personInsurances);
    }

    private Priority getModelPriority() throws IllegalValueException {
        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        return priority.toModelType();
    }
}
