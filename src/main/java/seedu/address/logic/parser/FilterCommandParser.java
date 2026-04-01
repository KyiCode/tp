package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Application;
import seedu.address.model.person.ApplicationMatchesAllPredicate;
import seedu.address.model.person.CompanyContainsKeywordPredicate;
import seedu.address.model.person.Date;
import seedu.address.model.person.DateMatchesPredicate;
import seedu.address.model.person.RoleMatchesPredicate;
import seedu.address.model.person.StatusMatchesPredicate;
import seedu.address.model.person.TagMatchesPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code FilterCommand}.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format. Use YYYY-MM-DD.";
    public static final String MESSAGE_INVALID_DATE = "Invalid date given.";
    public static final String MESSAGE_INVALID_COMPANY_FORMAT =
            "OOPS! Invalid format, use format: filter n/<keyword>";
    public static final String MESSAGE_INVALID_APPLIED_FORMAT =
            "OOPS! Invalid format, use format: filter d/<YYYY-MM-DD>";
    public static final String MESSAGE_INVALID_ROLE_FORMAT =
            "OOPS! Invalid format, use format: filter r/<role>";
    public static final String MESSAGE_INVALID_STATUS_FORMAT =
            "OOPS! Invalid format, use format: filter s/<status>";
    public static final String MESSAGE_INVALID_TAG_FORMAT =
            "OOPS! Invalid format, use format: filter t/<tag>";
    public static final String MESSAGE_MULTIPLE_COMPANY_KEYWORDS =
            "Please filter by only 1 company name.";
    public static final String MESSAGE_MULTIPLE_DATES =
            "Please filter by only 1 date.";
    public static final String MESSAGE_MULTIPLE_ROLES =
            "Please filter by only 1 role.";
    public static final String MESSAGE_MULTIPLE_STATUSES =
            "Please filter by only 1 status.";
    public static final String MESSAGE_MULTIPLE_TAGS =
            "Please filter by only 1 tag.";
    private static final String DATE_SHAPE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    @Override
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_DATE, PREFIX_ROLE, PREFIX_STATUS, PREFIX_TAG);

        validateArguments(argMultimap);

        List<Predicate<Application>> predicates = new ArrayList<>();
        addCompanyPredicate(argMultimap, predicates);
        addDatePredicate(argMultimap, predicates);
        addRolePredicate(argMultimap, predicates);
        addStatusPredicate(argMultimap, predicates);
        addTagPredicate(argMultimap, predicates);

        return new FilterCommand(new ApplicationMatchesAllPredicate(predicates));
    }

    private void validateArguments(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getPreamble().startsWith("/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty() || !hasAnySupportedPrefix(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_COMPANY_KEYWORDS);
        }
        if (argMultimap.getAllValues(PREFIX_DATE).size() > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_DATES);
        }
        if (argMultimap.getAllValues(PREFIX_ROLE).size() > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_ROLES);
        }
        if (argMultimap.getAllValues(PREFIX_STATUS).size() > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_STATUSES);
        }
        if (argMultimap.getAllValues(PREFIX_TAG).size() > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_TAGS);
        }
    }

    private void addCompanyPredicate(ArgumentMultimap argMultimap, List<Predicate<Application>> predicates)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isEmpty()) {
            return;
        }

        String companyKeyword = argMultimap.getValue(PREFIX_NAME).get().trim();
        if (companyKeyword.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMPANY_FORMAT);
        }
        predicates.add(new CompanyContainsKeywordPredicate(companyKeyword));
    }

    private void addDatePredicate(ArgumentMultimap argMultimap, List<Predicate<Application>> predicates)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            return;
        }

        String appliedDate = argMultimap.getValue(PREFIX_DATE).get().trim();
        if (appliedDate.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_APPLIED_FORMAT);
        }
        if (!appliedDate.matches(DATE_SHAPE_REGEX)) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }
        if (!Date.isValidDate(appliedDate)) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
        predicates.add(new DateMatchesPredicate(appliedDate));
    }

    private void addRolePredicate(ArgumentMultimap argMultimap, List<Predicate<Application>> predicates)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_ROLE).isEmpty()) {
            return;
        }

        String role = argMultimap.getValue(PREFIX_ROLE).get().trim();
        if (role.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_ROLE_FORMAT);
        }
        predicates.add(new RoleMatchesPredicate(ParserUtil.parseRole(role).value));
    }

    private void addStatusPredicate(ArgumentMultimap argMultimap, List<Predicate<Application>> predicates)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_STATUS).isEmpty()) {
            return;
        }

        String status = argMultimap.getValue(PREFIX_STATUS).get().trim();
        if (status.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_STATUS_FORMAT);
        }
        predicates.add(new StatusMatchesPredicate(status));
    }

    private void addTagPredicate(ArgumentMultimap argMultimap, List<Predicate<Application>> predicates)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_TAG).isEmpty()) {
            return;
        }

        String tag = argMultimap.getValue(PREFIX_TAG).get().trim();
        if (tag.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_TAG_FORMAT);
        }
        if (tag.contains(" ")) {
            throw new ParseException(MESSAGE_MULTIPLE_TAGS);
        }
        if (!Tag.isValidTagName(tag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        predicates.add(new TagMatchesPredicate(tag));
    }

    private boolean hasAnySupportedPrefix(ArgumentMultimap argMultimap) {
        return argMultimap.getValue(PREFIX_NAME).isPresent()
                || argMultimap.getValue(PREFIX_DATE).isPresent()
                || argMultimap.getValue(PREFIX_ROLE).isPresent()
                || argMultimap.getValue(PREFIX_STATUS).isPresent()
                || argMultimap.getValue(PREFIX_TAG).isPresent();
    }
}
