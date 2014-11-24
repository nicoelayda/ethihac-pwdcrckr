package com.ethihac.pwdcrckr.test;

import com.ethihac.pwdcrckr.PasswordCracker;

public class PasswordCrackerTest {

    private static final String PASSWD_PATH = "res/passwd";
    private static final String SHADOW_PATH = "res/shadow";
    private static final String DICTIONARY_PATH = "res/500-worst-passwords.txt";

    public static void main(String[] args) {
        PasswordCracker cracker = new PasswordCracker(PASSWD_PATH, SHADOW_PATH, DICTIONARY_PATH);
        cracker.crack();
    }
}
