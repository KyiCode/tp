package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Event class to represent upcoming tasks of an application.
 */
public class Event {
    private final String eventName;
    private final Date eventDate;


    /**
     * Event with no deadlines.
     * @param eventName event Description.
     */
    public Event(String eventName) {
        this.eventName = eventName;
        this.eventDate = null;
    }

    /**
     * Event with a deadline.
     * @param eventName event Description.
     * @param eventDate event date.
     */
    public Event(String eventName, String eventDate) {
        this.eventName = eventName;
        this.eventDate = new Date(eventDate);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return false;
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, eventDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Event", eventName)
                .add("Date", eventDate).toString();
    }

}
