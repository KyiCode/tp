package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.application.Application;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withApplication("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Application} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withApplication(Application application) {
        addressBook.addApplication(application);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
