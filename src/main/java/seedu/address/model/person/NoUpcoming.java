package seedu.address.model.person;

/**
 * Class to represent no upcoming events.
 */
public class NoUpcoming extends Upcoming {

    public NoUpcoming() {
        super("No upcoming tasks"); // sentinel value
    }

    @Override
    public String getEventName() { return ""; }

    @Override
    public Date getEventDate() { return null; }

    @Override
    public boolean hasUpcoming() {
        return false;
    }
}
