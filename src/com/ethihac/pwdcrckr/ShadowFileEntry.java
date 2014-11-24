/*
 * Shadow Password Entry
 *
 * 1. User login name
 * 2. Salt and hashed password OR a status exception value e.g.:
 *    - "$id$salt$hashed", the printable form of a password hash as produced by crypt (C),
 *       where "$id" is the algorithm used. (On GNU/Linux, "$1$" stands for MD5, "$2a$" is Blowfish,
 *       "$2y$" is Blowfish (correct handling of 8-bit chars), "$5$" is SHA-256 and "$6$" is SHA-512,
 *       crypt(3) manpage, other Unix may have different values, like NetBSD. Key stretching is used to
 *       increase password cracking difficulty, using by default 1000 rounds of modified MD5,[4] 64 rounds
 *       of Blowfish, 5000 rounds of SHA-256 or SHA-512.[5] The number of rounds may be varied for Blowfish,
 *       or for SHA-256 and SHA-512 by using e.g. "$6$rounds=50000$".)
 *
 *    - Empty string - No password, the account has no password. (Reported by passwd on Solaris with "NP")
 *
 *    - "!" - the account is password Locked, user will be unable to log-in via password authentication
 *      but other methods (e.g. ssh key) may be still allowed)
 *
 *    - "*LK*" or "*" - the account is Locked, user will be unable to log-in via password authentication
 *      but other methods (e.g. ssh key) may be still allowed)
 *
 *    - "!!" - the password has never been set (RedHat)
 *
 * 3. Days since epoch of last password change
 * 4. Days until change allowed
 * 5. Days before change required
 * 6. Days warning for expiration
 * 7. Days before account inactive
 * 8. Days since Epoch when account expires
 * 9. Reserved
 *
 * Source: http://en.wikipedia.org/wiki/Passwd#Shadow_file
 */

package com.ethihac.pwdcrckr;

import javax.xml.crypto.Data;

public class ShadowFileEntry {

    private static final int USERNAME_FIELD = 0;
    private static final int DATA_FIELD = 1;

    private static final int ID_FIELD = 1;
    private static final int SALT_FIELD = 2;
    private static final int HASH_FIELD = 3;

    private String username;
    private String data;

    private String id;
    private String salt;
    private String hash;

    public ShadowFileEntry(String entry) {
        this.parse(entry);
    }

    public boolean hasPassword() {
        return this.data.length() >= 1 && this.data.contains("$");
    }

    public String getUsername() {
        return this.username;
    }

    public String getData() {
        return this.data;
    }

    public String getId() {
        return this.id;
    }

    public String getSalt() {
        return this.salt;
    }

    public String getHash() {
        return this.hash;
    }

    private void parse(String entry) {
        String[] tokens = entry.split(":");

        this.username = tokens[USERNAME_FIELD];
        this.data = tokens[DATA_FIELD];

        if (data.contains("$")) {
            tokens = data.split("\\$");

            this.id = tokens[ID_FIELD];
            this.salt = tokens[SALT_FIELD];
            this.hash = tokens[HASH_FIELD];
        }
    }
}
