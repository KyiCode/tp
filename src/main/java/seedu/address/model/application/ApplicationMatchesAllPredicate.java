package seedu.address.model.application;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Tests that an {@code Application} matches all provided predicates.
 */
public class ApplicationMatchesAllPredicate implements Predicate<Application> {
    private final List<Predicate<Application>> predicates;

    /**
     * Creates a predicate that checks whether an application's fields satisfy all predicates.
     */
    public ApplicationMatchesAllPredicate(List<Predicate<Application>> predicates) {
        requireNonNull(predicates);
        this.predicates = new ArrayList<>(predicates);
    }

    @Override
    public boolean test(Application application) {
        return predicates.stream().allMatch(predicate -> predicate.test(application));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ApplicationMatchesAllPredicate)) {
            return false;
        }

        ApplicationMatchesAllPredicate otherPredicate = (ApplicationMatchesAllPredicate) other;
        return predicates.size() == otherPredicate.predicates.size()
                && new HashSet<>(predicates).equals(new HashSet<>(otherPredicate.predicates));
    }

    @Override
    public int hashCode() {
        Set<Predicate<Application>> predicateSet = new HashSet<>(predicates);
        return Objects.hash(predicateSet);
    }

    @Override
    public String toString() {
        return ApplicationMatchesAllPredicate.class.getCanonicalName() + "{predicates=" + predicates + "}";
    }
}
