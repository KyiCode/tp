package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showApplicationAtIndex;
import static seedu.address.testutil.TypicalApplications.BENSON_WITH_REMINDER_INTERVIEW;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditApplicationDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.SameCompanySameRolePredicate;
import seedu.address.testutil.ApplicationBuilder;
import seedu.address.testutil.EditApplicationDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Application editedApplication = new ApplicationBuilder().withDate(LocalDate.now().minusDays(1).toString())
                                        .build();
        Application originalApplication = model.getFilteredApplicationList()
                                        .get(INDEX_FIRST_APPLICATION.getZeroBased());
        EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder(editedApplication).build();

        EditCommand editCommand = new EditCommand(descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS,
                                        Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setApplication(originalApplication, editedApplication);

        originalApplication.setBeingEdited(true);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        originalApplication.setBeingEdited(false);
        editedApplication.setBeingEdited(false);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // Why is this here? Because for some ungodly reason I cannot fathom
        // the first application does not have its editing status reset no
        // matter
        // what I do in the previous test and breaks the editing flow.
        // Why does it do this? Good question, I don't know, and I'm not paid at
        // all
        // so I'm going to implement this very shitty quick fix and go to bed.
        model.getFilteredApplicationList().get(0).setBeingEdited(false);

        Index indexLastApplication = Index.fromOneBased(model.getFilteredApplicationList().size());
        Application lastApplication = model.getFilteredApplicationList().get(indexLastApplication.getZeroBased());
        ApplicationBuilder applicationInList = new ApplicationBuilder(lastApplication);
        Application editedApplication = applicationInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                                        .withTags(VALID_TAG_HUSBAND).build();

        EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder().withName(VALID_NAME_BOB)
                                        .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS,
                                        Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setApplication(lastApplication, editedApplication);

        lastApplication.setBeingEdited(true);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        editedApplication.setBeingEdited(false);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(new EditApplicationDescriptor());
        Application editedApplication = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        editedApplication.setBeingEdited(true);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS,
                                        Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        editedApplication.setBeingEdited(false);
    }

    @Test
    public void execute_filteredList_success() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        Application applicationInFilteredList = model.getFilteredApplicationList()
                                        .get(INDEX_FIRST_APPLICATION.getZeroBased());
        applicationInFilteredList.setBeingEdited(true);
        Application editedApplication = new ApplicationBuilder(applicationInFilteredList).withName(VALID_NAME_BOB)
                                        .build();
        EditCommand editCommand = new EditCommand(
                                        new EditApplicationDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS,
                                        Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setApplication(model.getFilteredApplicationList().get(0), editedApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        editedApplication.setBeingEdited(false);
    }

    @Test
    public void execute_duplicateApplicationUnfilteredList_failure() {
        Application firstApplication = model.getFilteredApplicationList().get(INDEX_FIRST_APPLICATION.getZeroBased());
        Application otherApplicant = model.getFilteredApplicationList()
                .get(INDEX_SECOND_APPLICATION.getZeroBased());
        otherApplicant.setBeingEdited(true);
        EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder(firstApplication).build();
        EditCommand editCommand = new EditCommand(descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_APPLICATION);
        otherApplicant.setBeingEdited(false);
    }

    @Test
    public void execute_duplicateApplicationFilteredList_failure() {
        showApplicationAtIndex(model, INDEX_FIRST_APPLICATION);

        // edit application in filtered list into a duplicate in address book
        Application applicationInList = model.getAddressBook().getApplicationList()
                                        .get(INDEX_SECOND_APPLICATION.getZeroBased());
        Application otherApplicant = model.getAddressBook().getApplicationList()
                                        .get(INDEX_FIRST_APPLICATION.getZeroBased());
        otherApplicant.setBeingEdited(true);
        EditCommand editCommand = new EditCommand(new EditApplicationDescriptorBuilder(applicationInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_APPLICATION);
        otherApplicant.setBeingEdited(false);
    }

    @Test
    public void execute_futureDate_failure() {
        Application editedApplication = new ApplicationBuilder().withDate(LocalDate.now().plusDays(1).toString())
                                        .build();
        Application originalApplication = model.getFilteredApplicationList()
                                        .get(INDEX_FIRST_APPLICATION.getZeroBased());
        EditApplicationDescriptor descriptor = new EditApplicationDescriptorBuilder(editedApplication).build();

        EditCommand editCommand = new EditCommand(descriptor);

        originalApplication.setBeingEdited(true);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DATE_NOT_ALLOWED);

        originalApplication.setBeingEdited(false);
        editedApplication.setBeingEdited(false);
    }

    @Test
    public void execute_setReminderEvent_success() {
        Application applicationToEdit = model.getFilteredApplicationList()
                .get(INDEX_FIRST_APPLICATION.getZeroBased());
        applicationToEdit.setBeingEdited(true);

        Application editedApplication = new ApplicationBuilder(applicationToEdit)
                                        .withReminder("Technical Interview", "2024-06-01").buildWithReminder();

        EditCommand editCommand = new EditCommand(new EditApplicationDescriptorBuilder()
                                        .withReminder("Technical Interview", "2024-06-01").build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS,
                                        Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setApplication(applicationToEdit, editedApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        applicationToEdit.setBeingEdited(false);
    }

    @Test
    public void execute_editReminderEvent_success() throws CommandException {
        SameCompanySameRolePredicate predicate = new SameCompanySameRolePredicate(
                                        BENSON_WITH_REMINDER_INTERVIEW.getName(),
                                        BENSON_WITH_REMINDER_INTERVIEW.getRole());

        Application applicationToEdit = model.getFilteredApplicationList().stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_APPLICATION_IDENTIFIER));

        applicationToEdit.setBeingEdited(true);

        Application editedApplication = new ApplicationBuilder(applicationToEdit)
                                        .withReminder("Accept Offer", "2024-12-01").buildWithReminder();

        EditCommand editCommand = new EditCommand(new EditApplicationDescriptorBuilder()
                                        .withReminder("Accept Offer", "2024-12-01").build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_APPLICATION_SUCCESS,
                                        Messages.format(editedApplication));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setApplication(applicationToEdit, editedApplication);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        applicationToEdit.setBeingEdited(false);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(DESC_AMY);

        // same values -> returns true
        EditApplicationDescriptor copyDescriptor = new EditApplicationDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditApplicationDescriptor editApplicationDescriptor = new EditApplicationDescriptor();
        EditCommand editCommand = new EditCommand(editApplicationDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{editApplicationDescriptor="
                                        + editApplicationDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
