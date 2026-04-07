package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalApplications.ALICE;
import static seedu.address.testutil.TypicalApplications.BENSON_WITH_REMINDER_INTERVIEW;
import static seedu.address.testutil.TypicalApplications.FIONA;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.ApplicationMatchesAllPredicate;
import seedu.address.model.application.CompanyContainsKeywordPredicate;
import seedu.address.model.application.DateMatchesPredicate;
import seedu.address.model.application.RoleMatchesPredicate;
import seedu.address.model.application.StatusMatchesPredicate;
import seedu.address.model.application.TagMatchesPredicate;

/**
 * Contains integration tests for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        CompanyContainsKeywordPredicate firstPredicate = new CompanyContainsKeywordPredicate("Alice");
        CompanyContainsKeywordPredicate secondPredicate = new CompanyContainsKeywordPredicate("Benson");

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        assertTrue(filterFirstCommand.equals(filterFirstCommand));
        assertTrue(filterFirstCommand.equals(new FilterCommand(firstPredicate)));
        assertFalse(filterFirstCommand.equals(1));
        assertFalse(filterFirstCommand.equals(null));
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_companyKeyword_singleMatchFound() {
        CompanyContainsKeywordPredicate predicate = new CompanyContainsKeywordPredicate("alice");
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, String.format(FilterCommand.MESSAGE_MATCHES_FOUND, 1), expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredApplicationList());
    }

    @Test
    public void execute_appliedDate_singleMatchFound() {
        DateMatchesPredicate predicate = new DateMatchesPredicate("2024-02-20");
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, String.format(FilterCommand.MESSAGE_MATCHES_FOUND, 1), expectedModel);
        assertEquals(Collections.singletonList(BENSON_WITH_REMINDER_INTERVIEW), model.getFilteredApplicationList());
    }

    @Test
    public void execute_status_noMatchesFound() {
        StatusMatchesPredicate predicate = new StatusMatchesPredicate("withdrawn");
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, FilterCommand.MESSAGE_NO_MATCHES, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_role_singleMatchFound() {
        RoleMatchesPredicate predicate = new RoleMatchesPredicate("Software Engineer");
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, String.format(FilterCommand.MESSAGE_MATCHES_FOUND, 1), expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredApplicationList());
    }

    @Test
    public void execute_tag_singleMatchFound() {
        TagMatchesPredicate predicate = new TagMatchesPredicate("owesMoney");
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, String.format(FilterCommand.MESSAGE_MATCHES_FOUND, 1), expectedModel);
        assertEquals(Collections.singletonList(BENSON_WITH_REMINDER_INTERVIEW), model.getFilteredApplicationList());
    }

    @Test
    public void execute_multipleFilters_singleMatchFound() {
        ApplicationMatchesAllPredicate predicate = new ApplicationMatchesAllPredicate(List.of(
                                        new RoleMatchesPredicate("DevOps Engineer"),
                                        new StatusMatchesPredicate("applied"), new DateMatchesPredicate("2024-03-22")));
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredApplicationList(predicate);
        assertCommandSuccess(command, model, String.format(FilterCommand.MESSAGE_MATCHES_FOUND, 1), expectedModel);
        assertEquals(Collections.singletonList(FIONA), model.getFilteredApplicationList());
    }
}
