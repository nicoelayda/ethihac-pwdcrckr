/*
 * User Account Entry
 *
 * 1. Username
 * 2. Stores information used to validate a user's password
 * 3. User identifier, the number that the operating system uses for internal purposes
 * 4. Group identifier
 * 5. Commentary that describes the person or account
 * 6. Path to the user's home directory
 * 7. Program that is started every time a user logs into the system.
 */

package com.ethihac.pwdcrckr;

public class PasswordFileEntry {

    private static final int USERNAME_FIELD = 0;
    private static final int USER_ID_FIELD = 2;

    private String username;
    private int userId;

    public PasswordFileEntry(String entry) {
        this.parse(entry);
    }

    public String getUsername() {
        return this.username;
    }

    public int getUserId() {
        return this.userId;
    }

    private void parse(String entry) {
        String[] tokens = entry.split(":");

        this.username = tokens[USERNAME_FIELD];
        this.userId = Integer.parseInt(tokens[USER_ID_FIELD]);
    }
}
