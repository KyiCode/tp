package seedu.address.model.application;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Tests that an {@code Application}'s applied date matches the target date.
 */
public class DateMatchesPredicate implements Predicate<Application> {
    private final Date targetDate;

    /**
     * Creates a predicate that checks whether an application's applied date matches {@code targetDate}.
     */
    public DateMatchesPredicate(String targetDate) {
        requireNonNull(targetDate);
        this.targetDate = new Date(targetDate);
    }

    @Override
    public boolean test(Application application) {
        return targetDate.equals(application.getDate());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateMatchesPredicate)) {
            return false;
        }

        DateMatchesPredicate otherPredicate = (DateMatchesPredicate) other;
        return targetDate.equals(otherPredicate.targetDate);
    }

    @Override
    public int hashCode() {
        return targetDate.hashCode();
    }

    @Override
    public String toString() {
        return DateMatchesPredicate.class.getCanonicalName() + "{targetDate=" + targetDate + "}";
    }
}
