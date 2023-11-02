package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * A UI Component that displays the remark of a person.
 */
public class RemarkCard extends UiPart<Region> {
    private static final String FXML = "RemarkCard.fxml";

    @FXML
    private VBox remarkCard;
    @FXML
    private Label remark;
    @FXML
    private ImageView imageView;
    private final Image image = new Image("images/remarks.png");

    /**
     * Creates a remark card with the given {@code remark}.
     */
    public RemarkCard(String remark) {
        super(FXML);
        this.remark.setText(remark);
        imageView.setImage(image);
    }
}
