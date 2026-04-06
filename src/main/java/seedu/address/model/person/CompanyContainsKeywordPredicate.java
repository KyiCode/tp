package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Locale;
import java.util.function.Predicate;

/**
 * Tests that an {@code Application}'s company name contains the given keyword.
 */
public class CompanyContainsKeywordPredicate implements Predicate<Application> {
    private final String keyword;

    /**
     * Creates a predicate that checks whether an application's company name contains {@code keyword}.
     */
    public CompanyContainsKeywordPredicate(String keyword) {
        requireNonNull(keyword);
        this.keyword = keyword;
    }

    @Override
    public boolean test(Application application) {
        return application.getName().fullName.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CompanyContainsKeywordPredicate)) {
            return false;
        }

        CompanyContainsKeywordPredicate otherPredicate = (CompanyContainsKeywordPredicate) other;
        return keyword.equalsIgnoreCase(otherPredicate.keyword);
    }

    @Override
    public int hashCode() {
        return keyword.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return CompanyContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
    }
}
