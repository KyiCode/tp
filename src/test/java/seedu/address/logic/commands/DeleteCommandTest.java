package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showApplicationAtIndex;
import static seedu.address.testutil.TypicalApplications.AMY;
import static seedu.address.testutil.TypicalApplications.BOB;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.Name;
import seedu.address.model.application.Role;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Application applicationToDelete = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_APPLICATION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(applicationToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteApplication(applicationToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Application applicationToDelete = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_APPLICATION);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(applicationToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteApplication(applicationToDelete);
        showNoApplication(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Index outOfBoundIndex = INDEX_SECOND_APPLICATION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getApplicationList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    // Non indexed Deletion Testcases
    @Test
    public void execute_validNormalDeleteCommand_success() throws CommandException {
        Application deleteTarget = BOB;
        model.addApplication(BOB);
        DeleteCommand deleteCommand = new DeleteCommand(deleteTarget.getName(), deleteTarget.getRole());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(deleteTarget));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteApplication(deleteTarget);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_normalDeleteNoTarget_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(new Name("NonExistentName"), new Role("Engineer"));
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_IDENTIFIER);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommandIndex = new DeleteCommand(INDEX_FIRST_APPLICATION);
        DeleteCommand deleteSecondCommandIndex = new DeleteCommand(INDEX_SECOND_APPLICATION);
        DeleteCommand deleteFirstCommandApp = new DeleteCommand(BOB.getName(), BOB.getRole());
        DeleteCommand deleteSecondCommandApp = new DeleteCommand(AMY.getName(), AMY.getRole());

        // same object -> returns true
        assertTrue(deleteFirstCommandIndex.equals(deleteFirstCommandIndex));

        // same values -> returns true
        DeleteCommand deleteFirstCommandIndexCopy = new DeleteCommand(INDEX_FIRST_APPLICATION);
        assertTrue(deleteFirstCommandIndex.equals(deleteFirstCommandIndexCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommandIndex.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommandIndex.equals(null));

        // different application index -> returns false
        assertFalse(deleteFirstCommandIndex.equals(deleteSecondCommandIndex));

        DeleteCommand deleteFirstCommandAppCopy = new DeleteCommand(BOB.getName(), BOB.getRole());
        assertTrue(deleteFirstCommandApp.equals(deleteFirstCommandApp));
        assertTrue(deleteFirstCommandApp.equals(deleteFirstCommandAppCopy));
        assertFalse(deleteFirstCommandApp.equals(1));
        assertFalse(deleteFirstCommandApp.equals(null));
        assertFalse(deleteFirstCommandApp.equals(deleteSecondCommandApp));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoApplication(Model model) {
        model.updateFilteredApplicationList(p -> false);

        assertTrue(model.getFilteredApplicationList().isEmpty());
    }
}
