package seedu.address.ui;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.priority.Level.NONE;
import static seedu.address.ui.FlowPaneLabel.createFlowPaneLabel;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Insurance;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Person person;
    private final int displayedIndex;

    // Independent UI Parts residing in this PersonCard
    private PersonAttributeCard phoneCard;
    private PersonAttributeCard emailCard;
    private PersonAttributeCard addressCard;
    private RemarkCard remarkCard;

    private AppointmentAttributeCard appointmentCard;


    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private VBox phoneCardPlaceholder;
    @FXML
    private VBox emailCardPlaceholder;
    @FXML
    private VBox addressCardPlaceholder;
    @FXML
    private VBox remarkCardPlaceholder;
    @FXML
    private VBox appointmentCardPlaceholder;
    @FXML
    private FlowPane flowPaneLabels;
    @FXML
    private HBox informationBox;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);

        requireNonNull(person);

        this.person = person;
        this.displayedIndex = displayedIndex;

        fillPersonDetails();
    }

    private void fillPersonDetails() {
        loadName();
        loadPriority();
        loadPhoneCard();
        loadEmailCard();
        loadAddressCard();
        loadInsurance();
        loadTags();
        loadRemarkCard();
        loadAppointmentCard();
        setInformationBox();
    }

    private void loadPriority() {
        if (person.getPriorityLevel() != NONE) {
            flowPaneLabels.getChildren().add(0,
                    createFlowPaneLabel(person.getPriority()).getRoot());
        }
    }

    private void loadName() {
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
    }

    private void loadInsurance() {
        person.getInsurances().stream()
                .sorted(Comparator.comparing(Insurance::getInsuranceName))
                .forEach(insurance -> flowPaneLabels.getChildren()
                        .add(createFlowPaneLabel(insurance).getRoot()));
    }

    private void loadTags() {
        person.getTags().stream()
                .sorted(Comparator.comparing(Tag::getTagName))
                .forEach(tag -> flowPaneLabels.getChildren()
                        .add(createFlowPaneLabel(tag).getRoot()));
    }

    private void loadPhoneCard() {
        phoneCard = new PersonAttributeCard(Attribute.PHONE, person.getPhone().value);
        phoneCardPlaceholder.getChildren().add(phoneCard.getRoot());
    }

    private void loadEmailCard() {
        emailCard = new PersonAttributeCard(Attribute.EMAIL, person.getEmail().value);
        emailCardPlaceholder.getChildren().add(emailCard.getRoot());
    }

    private void loadAddressCard() {

        if (person.getAddress().isEmptyAddress()) {
            return;
        }

        addressCard = new PersonAttributeCard(Attribute.ADDRESS, person.getAddress().getValue());
        addressCardPlaceholder.getChildren().add(addressCard.getRoot());
    }

    private void loadRemarkCard() {
        String remarkString = person.getRemark().toString();

        if (remarkString.isEmpty()) {
            return;
        }

        remarkCard = new RemarkCard(remarkString);
        remarkCardPlaceholder.getChildren().add(remarkCard.getRoot());
    }

    private void loadAppointmentCard() {
        /*
        String appointmentDateString = person.getAppointment().getDate();

        if (appointmentDateString.equals("-")) {
            return;
        } currently thinking of a way to do this, current method is not feasible
        as it causes appointmentCard to disappear when it is unmarked/decremented.
         */
        appointmentCard = new AppointmentAttributeCard(person.getAppointment(), person.getAppointmentCount());
        appointmentCardPlaceholder.getChildren().add(appointmentCard.getRoot());
    }

    private void setInformationBox() {
        String remarkString = person.getRemark().toString();

        if (remarkString.isEmpty()) {
            informationBox.setSpacing(5);
        } else {
            informationBox.setSpacing(10);
        }
    }
}
