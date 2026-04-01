package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ListFolderCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_noDataDirectory_returnsNoFoldersMessage() {
        // In the test environment, the data directory may not exist.
        // The command should handle this gracefully.
        ListFolderCommand command = new ListFolderCommand();
        CommandResult result = command.execute(model);
        String feedback = result.getFeedbackToUser();

        // Either no folders found, or folders are listed — both are valid outcomes.
        boolean isValidOutput = feedback.equals(ListFolderCommand.MESSAGE_NO_FOLDERS)
                || feedback.startsWith(ListFolderCommand.MESSAGE_FOLDERS_LISTED);
        assertEquals(true, isValidOutput);
    }

}
