package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the role the user applied for.
 */
public class Role {


    public static final String MESSAGE_CONSTRAINTS = "Job Role must not be blank, "
            + "contain letters only and no consecutive spaces";

    /*
     * Job Role name should be entirely letters.
     */
    public static final String VALIDATION_REGEX = "(?!.* {2})[A-Za-z][A-Za-z ]*";

    public final String value;

    /**
     * Constructs an {@code Role}.
     */
    public Role(String jobRole) {
        requireNonNull(jobRole);
        checkArgument(isValidJobRole(jobRole), MESSAGE_CONSTRAINTS);
        value = jobRole;
    }

    /**
     * Returns true if a given string is a valid role.
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
