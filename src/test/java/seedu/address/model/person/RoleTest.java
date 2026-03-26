package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null email
        assertThrows(NullPointerException.class, () -> Role.isValidJobRole(null));

        // blank email
        assertFalse(Role.isValidJobRole("")); // empty string
        assertFalse(Role.isValidJobRole(" ")); // spaces only

        // missing parts
        assertFalse(Role.isValidJobRole("@example.com")); // missing local part
        assertFalse(Role.isValidJobRole("peterjackexample.com")); // missing '@' symbol
        assertFalse(Role.isValidJobRole("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Role.isValidJobRole("job1"));
        assertFalse(Role.isValidJobRole("1job"));
        assertTrue(Role.isValidJobRole("peterjack"));
    }

    @Test
    public void equals() {
        Role role = new Role("validRole");

        // same values -> returns true
        assertTrue(role.equals(new Role("validRole")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        assertFalse(role.equals(5.0f));
    }

}
