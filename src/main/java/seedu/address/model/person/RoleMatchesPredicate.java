package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Locale;
import java.util.function.Predicate;

/**
 * Tests that an {@code Application}'s role matches the target role.
 */
public class RoleMatchesPredicate implements Predicate<Application> {
    private final String targetRole;

    /**
     * Creates a predicate that checks whether an application's role matches {@code targetRole}.
     */
    public RoleMatchesPredicate(String targetRole) {
        requireNonNull(targetRole);
        this.targetRole = targetRole;
    }

    @Override
    public boolean test(Application application) {
        return application.getRole().value.equalsIgnoreCase(targetRole);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RoleMatchesPredicate)) {
            return false;
        }

        RoleMatchesPredicate otherPredicate = (RoleMatchesPredicate) other;
        return targetRole.equalsIgnoreCase(otherPredicate.targetRole);
    }

    @Override
    public int hashCode() {
        return targetRole.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return RoleMatchesPredicate.class.getCanonicalName() + "{targetRole=" + targetRole + "}";
    }
}
