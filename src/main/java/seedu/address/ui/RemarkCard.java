package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class RemarkCard  extends UiPart<Region> {
    private static final String FXML = "RemarkCard.fxml";

    @FXML
    private VBox remarkCard;
    @FXML
    private Label remarkTitle;
    @FXML
    private Label remark;

    /**
     * Creates a remark card with the given {@code remarkTitle} and {@code remark}.
     */
    public RemarkCard(String remarkTitle, String remark) {
        super(FXML);
        this.remarkTitle.setText(remarkTitle);
        this.remark.setText(remark);
    }

}
