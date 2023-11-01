package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A UI Component that displays the appointment of a person.
 */
public class AppoinmentAttributeCard extends UiPart<Region> {
    private static final String FXML = "AppointmentAttributeCard.fxml";
    @FXML
    private HBox appointmentAttributeCard;
    @FXML
    private Label body;
    @FXML
    private Label appointment;
    @FXML
    private ImageView imageView;
    private Image image;

    /**
     * Creates an appointmentAttribute card for code
     * {@appointmentAttribute} with the given {@code bodyText}.
     */
    public AppoinmentAttributeCard(AppointmentAttribute appointmentAttribute, String bodyText) {
        super(FXML);
        switch (appointmentAttribute) {
        case DATE:
            image = new Image("images/date.png");
            imageView.setImage(image);
            break;
        case TIME:
            image = new Image("images/time.png");
            imageView.setImage(image);
            break;
        case VENUE:
            image = new Image("images/venue.png");
            imageView.setImage(image);
            break;
        default:
            //do nothingA
        }
        body.setText(bodyText);
    }
}
