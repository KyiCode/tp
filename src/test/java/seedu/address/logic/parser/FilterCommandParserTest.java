package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.application.ApplicationMatchesAllPredicate;
import seedu.address.model.application.CompanyContainsKeywordPredicate;
import seedu.address.model.application.DateMatchesPredicate;
import seedu.address.model.application.Role;
import seedu.address.model.application.RoleMatchesPredicate;
import seedu.address.model.application.StatusMatchesPredicate;
import seedu.address.model.application.TagMatchesPredicate;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_companyFilter_success() {
        assertParseSuccess(parser, " n/Google ", new FilterCommand(new ApplicationMatchesAllPredicate(
                                        List.of(new CompanyContainsKeywordPredicate("Google")))));
    }

    @Test
    public void parse_appliedFilter_success() {
        assertParseSuccess(parser, " d/2025-11-11 ", new FilterCommand(new ApplicationMatchesAllPredicate(
                                        List.of(new DateMatchesPredicate("2025-11-11")))));
    }

    @Test
    public void parse_statusFilter_success() {
        assertParseSuccess(parser, " s/Applied ", new FilterCommand(new ApplicationMatchesAllPredicate(
                                        List.of(new StatusMatchesPredicate("Applied")))));
    }

    @Test
    public void parse_roleFilter_success() {
        assertParseSuccess(parser, " r/Software Engineer ", new FilterCommand(new ApplicationMatchesAllPredicate(
                                        List.of(new RoleMatchesPredicate("Software Engineer")))));
    }

    @Test
    public void parse_tagFilter_success() {
        assertParseSuccess(parser, " t/friends ", new FilterCommand(new ApplicationMatchesAllPredicate(
                                        List.of(new TagMatchesPredicate("friends")))));
    }

    @Test
    public void parse_multipleDifferentFilters_success() {
        assertParseSuccess(parser, " r/Software Engineer s/applied d/2024-12-12",
                                        new FilterCommand(new ApplicationMatchesAllPredicate(List.of(
                                                                        new RoleMatchesPredicate("Software Engineer"),
                                                                        new StatusMatchesPredicate("applied"),
                                                                        new DateMatchesPredicate("2024-12-12")))));
    }

    @Test
    public void parse_emptyArguments_failure() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongPrefix_failure() {
        assertParseFailure(parser, " x/test",
                                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_unknownPrefixAfterValidPrefix_failure() {
        assertParseFailure(parser, " n/test j/ywteyu",
                                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingCompanyValue_failure() {
        assertParseFailure(parser, " n/ ", FilterCommandParser.MESSAGE_INVALID_COMPANY_FORMAT);
    }

    @Test
    public void parse_missingAppliedValue_failure() {
        assertParseFailure(parser, " d/ ", FilterCommandParser.MESSAGE_INVALID_APPLIED_FORMAT);
    }

    @Test
    public void parse_missingStatusValue_failure() {
        assertParseFailure(parser, " s/ ", FilterCommandParser.MESSAGE_INVALID_STATUS_FORMAT);
    }

    @Test
    public void parse_missingRoleValue_failure() {
        assertParseFailure(parser, " r/ ", FilterCommandParser.MESSAGE_INVALID_ROLE_FORMAT);
    }

    @Test
    public void parse_invalidRole_failure() {
        assertParseFailure(parser, " r/Engineer123 ", Role.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingTagValue_failure() {
        assertParseFailure(parser, " t/ ", FilterCommandParser.MESSAGE_INVALID_TAG_FORMAT);
    }

    @Test
    public void parse_invalidAppliedDateFormat_failure() {
        assertParseFailure(parser, " d/11-11-2025 ", FilterCommandParser.MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_invalidAppliedDate_failure() {
        assertParseFailure(parser, " d/2024-13-12 ", FilterCommandParser.MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_invalidTag_failure() {
        assertParseFailure(parser, " t/friends* ", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateCompanyPrefix_failure() {
        assertParseFailure(parser, " n/Google n/Meta ", FilterCommandParser.MESSAGE_MULTIPLE_COMPANY_KEYWORDS);
    }

    @Test
    public void parse_duplicateDatePrefix_failure() {
        assertParseFailure(parser, " d/2024-03-17 d/2025-12-12 ", FilterCommandParser.MESSAGE_MULTIPLE_DATES);
    }

    @Test
    public void parse_duplicateStatusPrefix_failure() {
        assertParseFailure(parser, " s/applied s/rejected ", FilterCommandParser.MESSAGE_MULTIPLE_STATUSES);
    }

    @Test
    public void parse_duplicateRolePrefix_failure() {
        assertParseFailure(parser, " r/Software Engineer r/Product Manager ",
                                        FilterCommandParser.MESSAGE_MULTIPLE_ROLES);
    }

    @Test
    public void parse_duplicateTagPrefix_failure() {
        assertParseFailure(parser, " t/tech t/priority ", FilterCommandParser.MESSAGE_MULTIPLE_TAGS);
    }
}
