package seedu.address.storage.jsonadaptedperson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.priority.Priority;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
public class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final JsonAdaptedName name;
    private final JsonAdaptedPhone phone;
    private final JsonAdaptedEmail email;
    private final JsonAdaptedAddress address;
    private final JsonAdaptedPriority priority;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") JsonAdaptedName name,
                             @JsonProperty("phone") JsonAdaptedPhone phone,
                             @JsonProperty("email") JsonAdaptedEmail email,
                             @JsonProperty("address") JsonAdaptedAddress address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("priority") JsonAdaptedPriority priority) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.priority = priority;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = new JsonAdaptedName(source.getName());
        phone = new JsonAdaptedPhone(source.getPhone());
        email = new JsonAdaptedEmail(source.getEmail());
        address = new JsonAdaptedAddress(source.getAddress());
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        priority = new JsonAdaptedPriority(source.getPriority());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        return new Person(getModelName(), getModelPhone(), getModelEmail(), getModelAddress(), getModelTags(),
                getModelPriority());
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

    private Set<Tag> getModelTags() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        return new HashSet<>(personTags);
    }

    private Priority getModelPriority() throws IllegalValueException {
        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        return priority.toModelType();
    }
}
