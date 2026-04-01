package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Status;

public class StatusCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_validInput_success() {
        Status status = new Status("Applied");
        StatusCommand command = new StatusCommand("Alex Yeoh", "Software Engineer", status);

        assertEquals("Alex Yeoh", command.getName());
        assertEquals("Software Engineer", command.getRole());
        assertEquals(status, command.getStatus());
    }

    @Test
    public void execute_applicationNotFound_throwsCommandException() {
        StatusCommand command = new StatusCommand("NonExistentName", "Engineer", new Status("Applied"));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_APPLICATION_IDENTIFIER);
    }
}
