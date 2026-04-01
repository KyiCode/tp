package seedu.address.logic.commands;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.ParserMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Application;
import seedu.address.model.person.IsBeingEditedPredicate;


/** Command to exit editing mode. */
public class EditExitCommand extends Command {

    public static final String COMMAND_WORD = "exitedit";
    public static final String MESSAGE_EXIT_EDITING_MODE_ACKNOWLEDGEMENT = "Exiting Editing mode as requested ...";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Application> lastShownList = model.getFilteredPersonList();
        IsBeingEditedPredicate predicate = new IsBeingEditedPredicate();
        lastShownList.stream().filter(predicate).findFirst().ifPresent((i) -> i.setBeingEdited(false));
        return new CommandResult(MESSAGE_EXIT_EDITING_MODE_ACKNOWLEDGEMENT, false, ParserMode.NORMAL, false);
    }
}
