package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FolderCommand;

public class FolderCommandParserTest {

    private final FolderCommandParser folderParser = new FolderCommandParser(true);
    private final FolderCommandParser toggleParser = new FolderCommandParser(false);

    @Test
    public void parse_emptyFolderArgs_throwsParseException() {
        assertParseFailure(folderParser, " ",
                "Folder name cannot be empty.\n" + FolderCommand.MESSAGE_USAGE_FOLDER);
    }

    @Test
    public void parse_emptyToggleArgs_throwsParseException() {
        assertParseFailure(toggleParser, " ",
                "Folder name cannot be empty.\n" + FolderCommand.MESSAGE_USAGE_TOGGLE);
    }

    @Test
    public void parse_nameWithSpaces_throwsParseException() {
        assertParseFailure(folderParser, "Y1 S2",
                "Folder name can only contain letters, numbers, underscores, and hyphens.");
    }

    @Test
    public void parse_nameWithSpecialChars_throwsParseException() {
        assertParseFailure(folderParser, "name@folder",
                "Folder name can only contain letters, numbers, underscores, and hyphens.");
    }

    @Test
    public void parse_validFolderArgs_returnsFolderCommand() {
        assertParseSuccess(folderParser, "Y1S2", new FolderCommand("Y1S2", true));
    }

    @Test
    public void parse_validToggleArgs_returnsFolderCommand() {
        assertParseSuccess(toggleParser, "Y1S2", new FolderCommand("Y1S2", false));
    }

    @Test
    public void parse_validArgsWithWhitespace_returnsFolderCommand() {
        assertParseSuccess(folderParser, "  Y1S2  ", new FolderCommand("Y1S2", true));
    }

    @Test
    public void parse_validArgsWithHyphen_returnsFolderCommand() {
        assertParseSuccess(folderParser, "Y1-S2", new FolderCommand("Y1-S2", true));
    }
}
