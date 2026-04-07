package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.application.Application;
import seedu.address.model.application.Reminder;

/**
 * An UI component that displays information of a {@code Application}.
 */
public class ApplicationCard extends UiPart<Region> {

    private static final String FXML = "ApplicationListCard.fxml";
    private static final String NONE_STRING = "None";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Application application;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label role;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label status;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;
    @FXML
    private Label reminder;
    @FXML
    private VBox reminderBox;

    /**
     * Creates a {@code ApplicationCode} with the given {@code Application} and index to display.
     */
    public ApplicationCard(Application application, int displayedIndex) {
        super(FXML);
        this.application = application;
        setBasicInfo(displayedIndex);
        setContactInfo();
        setStatusInfo();
        setReminderInfo();
        setTags();
    }

    /**
     * Sets Index, Name, Role and Date of Application.
     *
     * @param displayedIndex Index of Application.
     */
    private void setBasicInfo(int displayedIndex) {
        id.setText(displayedIndex + ". ");
        name.setText(application.getName().fullName);
        role.setText(" - " + application.getRole().value + "  ");
        date.setText("Date: " + (application.getDate() != null ? application.getDate().toString() : NONE_STRING));
    }

    /**
     * Sets Phone, Email and Address of Application.
     */
    private void setContactInfo() {
        phone.setText("Phone: " + (application.getPhone() != null ? application.getPhone().value : NONE_STRING));
        address.setText(
                "Address: " + (application.getAddress() != null ? application.getAddress().value : NONE_STRING));
        email.setText("Email: " + (application.getEmail() != null ? application.getEmail().value : NONE_STRING));
    }

    /**
     * Set Status of Application.
     */
    private void setStatusInfo() {
        if (application.getStatus() != null) {
            status.setText(application.getStatus().value);
            status.getStyleClass().removeIf(s -> s.startsWith("status-"));
            status.getStyleClass().add("status-" + application.getStatus().getStyleClass());
        } else {
            status.setText(NONE_STRING);
        }
    }

    /**
     * Set Reminder of Application.
     */
    private void setReminderInfo() {
        if (application.hasReminder()) {
            Reminder u = application.getReminder();
            this.reminder.setVisible(true);
            String text = u.getReminderName() + "\n" + u.getReminderDate().value;
            reminderBox.getStyleClass().removeIf(s -> s.startsWith("reminder-"));
            reminderBox.getStyleClass().add("reminder-" + application.getReminder().getStyleClass());
            this.reminder.setText(text);
        } else {
            this.reminder.setVisible(false);
        }
    }

    /**
     * Set Tags of Application.
     */
    private void setTags() {
        application.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
