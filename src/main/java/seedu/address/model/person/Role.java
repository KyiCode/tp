package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * A Class to represent the name of the role the user applied for.
 */
public class Role {


    public static final String MESSAGE_CONSTRAINTS = "Job Role should be letters, and it should not be blank";

    /*
     * Job Role name should be entirely letters.
     */
    public static final String VALIDATION_REGEX = "[A-Za-z][A-Za-z ]*";

    public final String value;

    /**
     * Constructs an {@code Role}.
     *
     * @param jobRole A valid job role  .
     */
    public Role(String jobRole) {
        requireNonNull(jobRole);
        checkArgument(isValidJobRole(jobRole), MESSAGE_CONSTRAINTS);
        value = jobRole;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidJobRole(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equalsIgnoreCase(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
