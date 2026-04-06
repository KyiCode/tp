package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Applications
 * (Applications are considered duplicates if they have the same
 * name and role).
 */
public class DuplicateApplicationException extends RuntimeException {
    public DuplicateApplicationException(String message) {
        super(message);
    }
}
