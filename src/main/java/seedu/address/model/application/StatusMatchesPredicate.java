package seedu.address.model.application;

import static java.util.Objects.requireNonNull;

import java.util.Locale;
import java.util.function.Predicate;

/**
 * Tests that an {@code Application}'s status matches the target status.
 */
public class StatusMatchesPredicate implements Predicate<Application> {
    private final String targetStatus;

    /**
     * Creates a predicate that checks whether an application's status matches {@code targetStatus}.
     */
    public StatusMatchesPredicate(String targetStatus) {
        requireNonNull(targetStatus);
        this.targetStatus = targetStatus;
    }

    @Override
    public boolean test(Application application) {
        return application.getStatus().value.equalsIgnoreCase(targetStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusMatchesPredicate)) {
            return false;
        }

        StatusMatchesPredicate otherPredicate = (StatusMatchesPredicate) other;
        return targetStatus.equalsIgnoreCase(otherPredicate.targetStatus);
    }

    @Override
    public int hashCode() {
        return targetStatus.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return StatusMatchesPredicate.class.getCanonicalName() + "{targetStatus=" + targetStatus + "}";
    }
}
