package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that an Application matches Both Name and Role provided.
 */
public class IsBeingEditedPredicate implements Predicate<Application> {

    @Override
    public boolean test(Application application) {
        return application.getEditingStatus();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IsBeingEditedPredicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
