package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A UI Component that displays the attribute of a person.
 */
public class PersonAttributeCard extends UiPart<Region> {

    private static final String FXML = "PersonAttributeCard.fxml";

    @FXML
    private HBox attributeCard;
    @FXML
    private Label body;
    @FXML
    private ImageView imageView;
    private Image image;

    /**
     * Creates an attribute card for {@code attribute} with the given {@code bodyText}.
     */
    public PersonAttributeCard(Attribute attribute, String bodyText) {
        super(FXML);
        switch (attribute) {
        case PHONE:
            image = new Image("images/phone.png");
            imageView.setImage(image);
            break;
        case ADDRESS:
            image = new Image("images/address.png");
            imageView.setImage(image);
            break;
        case EMAIL:
            image = new Image("images/email.png");
            imageView.setImage(image);
            break;
        default:
            //do nothing
        }
        body.setText(bodyText);
    }
}
