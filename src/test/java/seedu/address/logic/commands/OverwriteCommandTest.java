package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.ALICE;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.DuplicateApplicationStore;
import seedu.address.model.application.exceptions.DuplicateApplicationException;
import seedu.address.testutil.ApplicationBuilder;

public class OverwriteCommandTest {
    private Model model;
    private Application alice;
    private Application duplicateAlice;
    private Application newApplication;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        alice = new ApplicationBuilder(ALICE).build();

        if (!model.hasApplication(alice)) {
            model.addApplication(alice);
        }

        duplicateAlice = new ApplicationBuilder(ALICE).withPhone("98989898").withAddress("test street").build();

        newApplication = new ApplicationBuilder().withName("TEST").withRole("TESTER").withPhone("77527897")
                                        .withEmail("bqshxbg2hj2@n.com").withAddress("tjahbsjkw").withDate("2024-01-01")
                                        .withStatus("rejected").build();

        DuplicateApplicationStore.clear();
    }

    @Test
    public void execute_duplicateApplicationStored_overwriteSuccess() throws Exception {
        assertTrue(model.hasApplication(alice));
        AddCommand duplicateApplication = new AddCommand(duplicateAlice);

        DuplicateApplicationStore.setLastDuplicateApplication(duplicateApplication);
        assertTrue(DuplicateApplicationStore.hasLastDuplicateApplication());

        OverwriteCommand overwriteCommand = new OverwriteCommand();
        CommandResult result = overwriteCommand.execute(model);

        assertEquals(String.format(OverwriteCommand.MESSAGE_SUCCESS, Messages.format(duplicateAlice)),
                                        result.getFeedbackToUser());

        assertTrue(model.hasApplication(duplicateAlice));
        assertFalse(DuplicateApplicationStore.hasLastDuplicateApplication());
    }

    @Test
    public void execute_duplicateApplicationStoredButNoPreExistingApplication_throwsException() {
        Application newAddition = new ApplicationBuilder().withName("TEST").withRole("TESTER").build();
        AddCommand duplicateApplication = new AddCommand(newAddition);

        DuplicateApplicationStore.setLastDuplicateApplication(duplicateApplication);
        assertTrue(DuplicateApplicationStore.hasLastDuplicateApplication());

        OverwriteCommand overwriteCommand = new OverwriteCommand();
        assertThrows(DuplicateApplicationException.class, () -> overwriteCommand.execute(model));

        assertFalse(DuplicateApplicationStore.hasLastDuplicateApplication());
    }

    @Test
    public void execute_noDuplicateApplicationStored_throwsException() {
        assertFalse(DuplicateApplicationStore.hasLastDuplicateApplication());

        OverwriteCommand overwriteCommand = new OverwriteCommand();
        Executable executeOverwrite = () -> overwriteCommand.execute(model);
        assertThrows(
                DuplicateApplicationException.class,
                OverwriteCommand.MESSAGE_NO_DUPLICATE,
                executeOverwrite);
    }
}
