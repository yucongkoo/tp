package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.insurance.Insurance;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.priority.Priority;
import seedu.address.model.tag.Tag;

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
    private FlowPane tags;
    @FXML
    private Label remark;
    @FXML
    private Label remarkTitle;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
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
    }

    private void loadPriority() {
        if (person.getPriority().getPriorityLevel() != Priority.Level.NONE) {
            // priority.setText(person.getPriority().toString());

            // TODO: Use priority enum to construct the label, so that each priority will have differen display
            tags.getChildren().add(0, new FlowPaneLabel(person.getPriority().toString(),
                    FlowPaneLabel.Type.PRIORITY).getRoot());
        }
    }

    private void loadName() {
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
    }

    private void loadInsurance() {
        person.getInsurances().stream()
                .sorted(Comparator.comparing(Insurance::getInsuranceName))
                .forEach(insurance -> tags.getChildren()
                        .add(new FlowPaneLabel(insurance.getInsuranceName(),
                                FlowPaneLabel.Type.INSURANCE).getRoot()));
    }

    private void loadTags() {
        person.getTags().stream()
                .sorted(Comparator.comparing(Tag::getTagName))
                .forEach(tag -> tags.getChildren().add(new FlowPaneLabel(tag.getTagName(),
                        FlowPaneLabel.Type.TAG).getRoot()));
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
            remarkTitle.setText(Remark.REMARK_TITLE_NO_REMARK);
            return;
        }
        remarkTitle.setText(Remark.REMARK_TITLE);
        remark.setText(remarkString);
    }
}
