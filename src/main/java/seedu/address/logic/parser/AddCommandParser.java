package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Application;
import seedu.address.model.person.Date;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Reminder;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_ROLE, PREFIX_STATUS, PREFIX_DATE, PREFIX_REMINDER, PREFIX_REMINDER_DATE);
        validatePrefixes(argMultimap);

        try {
            validateDate(argMultimap);
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }

        Application application = buildApplication(argMultimap);
        return new AddCommand(application);
    }

    private Application buildApplication(ArgumentMultimap argMultimap) throws ParseException {
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Phone phone = arePrefixesPresent(argMultimap, PREFIX_PHONE)
                ? ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get())
                : null;
        Email email = arePrefixesPresent(argMultimap, PREFIX_EMAIL)
                ? ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get())
                : null;
        Address address = arePrefixesPresent(argMultimap, PREFIX_ADDRESS)
                ? ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get())
                : null;
        Date date = arePrefixesPresent(argMultimap, PREFIX_DATE)
                ? ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get())
                : null;
        Status status = arePrefixesPresent(argMultimap, PREFIX_STATUS)
                ? ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get())
                : null;
        Reminder reminder = arePrefixesPresent(argMultimap, PREFIX_REMINDER, PREFIX_REMINDER_DATE)
                ? ParserUtil.parseReminder(argMultimap.getValue(PREFIX_REMINDER).get(),
                            argMultimap.getValue(PREFIX_REMINDER_DATE).get())
                : null;
        return new Application(name, phone, email, address, tagList, date, role, status, reminder);
    }

    /**
     * Checks if the date is a future date.
     * @param argMultimap the ArgumentMultimap containing the date to validate
     * @throws CommandException if the date is in the future
     */
    private void validateDate(ArgumentMultimap argMultimap) throws CommandException {
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            String dateString = argMultimap.getValue(PREFIX_DATE).get();
            Date applicationDate = new Date(dateString);
            applicationDate.checkNotFutureDate();
        }
    }

    /**
     * Validates that the required prefixes are present and that there are no duplicate prefixes.
     * @param argMultimap the ArgumentMultimap to validate
     * @throws ParseException if the validation fails
     */
    private void validatePrefixes(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_DATE, PREFIX_ROLE, PREFIX_STATUS, PREFIX_REMINDER, PREFIX_REMINDER_DATE);
    }

    /**
     * Tokenizes the given argument string using the specified prefixes
     *
     * @param args the argument string to tokenize
     * @return an ArgumentMultimap containing the tokenized arguments
     */
    private ArgumentMultimap tokenizeArguments(String args) {
        assert args != null : "argument string should not be null";
        return ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_ROLE, PREFIX_STATUS, PREFIX_DATE, PREFIX_REMINDER, PREFIX_REMINDER_DATE);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private Phone parsePhone(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
            logger.info("phone: " + argMultimap.getValue(PREFIX_PHONE).get());
            return ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } else {
            return null;
        }
    }

    private Email parseEmail(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
            logger.info("email: " + argMultimap.getValue(PREFIX_EMAIL).get());
            return ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        } else {
            return null;
        }
    }

    private Address parseAddress(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
            logger.info("address: " + argMultimap.getValue(PREFIX_ADDRESS).get());
            return ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        } else {
            return null;
        }
    }

    private Status parseStatus(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_STATUS)) {
            logger.info("status: " + argMultimap.getValue(PREFIX_STATUS).get());
            return ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        } else {
            return null;
        }
    }

    private Date parseDate(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            logger.info("date: " + argMultimap.getValue(PREFIX_DATE).get());
            return ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        } else {
            return null;
        }
    }

    private Reminder parseReminder(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_REMINDER, PREFIX_REMINDER_DATE)) {

            logger.info("reminder: " + argMultimap.getValue(PREFIX_REMINDER).get());
            logger.info("reminder date: " + argMultimap.getValue(PREFIX_REMINDER_DATE).get());

            return ParserUtil.parseReminder(argMultimap.getValue(PREFIX_REMINDER).get(),
                        argMultimap.getValue(PREFIX_REMINDER_DATE).get());
        } else {
            return null;
        }
    }

    private Set<Tag> parseTags(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
    }

    private Name parseName(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
    }

    private Role parseRole(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
    }
}
