package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.AppointmentCount;

/**
 * A UI Component that displays the appointment of a person.
 */
public class AppointmentAttributeCard extends UiPart<Region> {
    private static final String FXML = "AppointmentCard.fxml";
    @FXML
    private VBox appointmentCard;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label venue;
    @FXML
    private Label count;

    @FXML
    private ImageView dateIcon;
    private final Image dateImg = new Image("images/date.png");

    @FXML
    private ImageView timeIcon;
    private final Image timeImg = new Image("images/time.png");

    @FXML
    private ImageView venueIcon;
    private final Image venueImg = new Image("images/venue.png");


    /**
     * Creates an appointmentAttribute card for code
     * {@appointmentAttribute} with the given {@code bodyText}.
     */
    public AppointmentAttributeCard(Appointment appointment, AppointmentCount appointmentCount) {
        super(FXML);
        dateIcon.setImage(dateImg);
        timeIcon.setImage(timeImg);
        venueIcon.setImage(venueImg);

        date.setText(appointment.getDate());
        time.setText(appointment.getTime());
        venue.setText(appointment.getVenue());
        count.setText("Appointments completed: " + String.valueOf(appointmentCount.getCount()));

    }
}
