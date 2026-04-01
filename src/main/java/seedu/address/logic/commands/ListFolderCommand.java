package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Model;

/**
 * Lists all existing folders (JSON files) in the data directory.
 */
public class ListFolderCommand extends Command {

    public static final String COMMAND_WORD = "folders";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all existing folders in the data directory.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_NO_FOLDERS = "No folders found in data directory.";
    public static final String MESSAGE_FOLDERS_LISTED = "Folders available:\n";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        File dataDir = new File("data");
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            return new CommandResult(MESSAGE_NO_FOLDERS);
        }

        File[] jsonFiles = dataDir.listFiles((dir, name) -> name.endsWith(".json"));
        if (jsonFiles == null || jsonFiles.length == 0) {
            return new CommandResult(MESSAGE_NO_FOLDERS);
        }

        List<String> folderNames = Arrays.stream(jsonFiles)
                .map(f -> f.getName().replace(".json", ""))
                .sorted()
                .collect(Collectors.toList());

        return new CommandResult(MESSAGE_FOLDERS_LISTED + String.join("\n", folderNames));
    }

}
