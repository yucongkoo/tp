package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


/**
 * An UI Component that displays the attribute of a person.
 */
public class PersonAttributeCard extends UiPart<Region> {

    private static final String FXML = "PersonAttributeCard.fxml";

    @FXML
    private VBox attributeCard;
    @FXML
    private Label header;
    @FXML
    private Label body;

    /**
     * Creates an attribute card with the given headerText and bodyText.
     */
    public PersonAttributeCard(String headerText, String bodyText) {
        super(FXML);
        header.setText(headerText);
        body.setText(bodyText);
    }

}
