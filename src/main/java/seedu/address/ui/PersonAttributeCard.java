package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


public class PersonAttributeCard extends UiPart<Region> {

    private static final String FXML = "PersonAttributeCard.fxml";

    @FXML
    private VBox attributeCard;
    @FXML
    private Label header;
    @FXML
    private Label body;

    public PersonAttributeCard(String headerText, String bodyText) {
        super(FXML);
        header.setText(headerText);
        body.setText(bodyText);
    }

}
