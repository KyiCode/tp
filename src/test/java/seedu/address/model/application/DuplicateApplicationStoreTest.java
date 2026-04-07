package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalApplications.ALICE;
import static seedu.address.testutil.TypicalApplications.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.testutil.ApplicationBuilder;

public class DuplicateApplicationStoreTest {

    private AddCommand aliceApplication;
    private AddCommand bobApplication;

    @BeforeEach
    public void setUp() {
        aliceApplication = new AddCommand(new ApplicationBuilder(ALICE).build());
        bobApplication = new AddCommand(new ApplicationBuilder(BOB).build());
        DuplicateApplicationStore.clear();
    }

    @Test
    public void setLastDuplicateApplication_validStorage_success() {
        DuplicateApplicationStore.setLastDuplicateApplication(aliceApplication);

        assertTrue(DuplicateApplicationStore.hasLastDuplicateApplication());
        assertEquals(aliceApplication, DuplicateApplicationStore.getLastDuplicateApplication());
    }

    @Test
    public void setLastDuplicateApplication_overwritesPreviousApplication() {
        DuplicateApplicationStore.setLastDuplicateApplication(aliceApplication);
        assertEquals(aliceApplication, DuplicateApplicationStore.getLastDuplicateApplication());

        DuplicateApplicationStore.setLastDuplicateApplication(bobApplication);
        assertEquals(bobApplication, DuplicateApplicationStore.getLastDuplicateApplication());
        assertFalse(DuplicateApplicationStore.getLastDuplicateApplication().equals(aliceApplication));
    }

    @Test
    public void getLastDuplicateApplication_noStoredApplication_returnsNull() {
        assertNull(DuplicateApplicationStore.getLastDuplicateApplication());
    }

    @Test
    public void getLastDuplicateApplication_afterClear_returnsNull() {
        DuplicateApplicationStore.setLastDuplicateApplication(aliceApplication);
        assertTrue(DuplicateApplicationStore.hasLastDuplicateApplication());

        DuplicateApplicationStore.clear();

        assertNull(DuplicateApplicationStore.getLastDuplicateApplication());
        assertFalse(DuplicateApplicationStore.hasLastDuplicateApplication());
    }

    @Test
    public void clear_emptyStore_noException() {
        DuplicateApplicationStore.clear();
        assertFalse(DuplicateApplicationStore.hasLastDuplicateApplication());

        DuplicateApplicationStore.clear();
        assertFalse(DuplicateApplicationStore.hasLastDuplicateApplication());
    }

    @Test
    public void hasLastDuplicateApplication_emptyStore_returnsFalse() {
        assertFalse(DuplicateApplicationStore.hasLastDuplicateApplication());
    }

    @Test
    public void hasLastDuplicateApplication_storeApplication_returnsTrue() {
        DuplicateApplicationStore.setLastDuplicateApplication(aliceApplication);
        assertTrue(DuplicateApplicationStore.hasLastDuplicateApplication());
    }

    @Test
    public void hasLastDuplicateApplication_clear_returnsFalse() {
        DuplicateApplicationStore.setLastDuplicateApplication(aliceApplication);
        assertTrue(DuplicateApplicationStore.hasLastDuplicateApplication());

        DuplicateApplicationStore.clear();
        assertFalse(DuplicateApplicationStore.hasLastDuplicateApplication());
    }

}
