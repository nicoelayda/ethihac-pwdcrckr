package com.ethihac.pwdcrckr;

import java.util.Scanner;

public class Pwdcrckr {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String passwdPath;
        String shadowPath;
        String dictionaryPath;

        System.out.println("Enter path to passwd file:");
        System.out.print("?>");

        passwdPath = scanner.nextLine();

        System.out.println("Enter path to shadow file:");
        System.out.print("?>");

        shadowPath = scanner.nextLine();

        System.out.println("Enter path to dictionary file:");
        System.out.print("?>");

        dictionaryPath = scanner.nextLine();

        System.out.println("Cracking...");

        PasswordCracker cracker = new PasswordCracker(passwdPath, shadowPath, dictionaryPath);
        cracker.crack();

        System.out.println("Done");
    }
}
