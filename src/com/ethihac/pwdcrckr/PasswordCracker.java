package com.ethihac.pwdcrckr;

import org.apache.commons.codec.digest.Crypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasswordCracker {

    private List<PasswordFileEntry> passwd;
    private List<ShadowFileEntry> shadow;
    private List<String> dictionary;

    public PasswordCracker() {
        this.passwd = new ArrayList<PasswordFileEntry>();
        this.shadow = new ArrayList<ShadowFileEntry>();
        this.dictionary = new ArrayList<String>();
    }

    public PasswordCracker(String pathToPasswdFile, String pathToShadowFile,
                           String pathToDictionaryFile) {
        this();

        // Load passwd file.
        try {
            this.loadPasswdFile(pathToPasswdFile);
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        // Load shadow file.
        try {
            this.loadShadowFile(pathToShadowFile);
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        // Load dictionary file.
        try {
            this.loadDictionaryFile(pathToDictionaryFile);
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void crack() {
        // Error handler: No passwd file.
        if (passwd.isEmpty()) {
            System.err.println("ERROR: No passwd file loaded!");
            return;
        }

        // Error handler: No shadow file.
        if (shadow.isEmpty()) {
            System.err.println("ERROR: No shadow file loaded!");
            return;
        }

        // Error handler: No dictionary file.
        if (dictionary.isEmpty()) {
            System.err.println("ERROR: No dictionary file loaded!");
            return;
        }

        // Error handler: passwd and shadow file mismatch.
        if (passwd.size() != shadow.size()) {
            System.err.println("ERROR: passwd and shadow file don't match!");
            return;
        }

        // Process each user
        for (int i = 0; i < passwd.size(); i++) {
            if (passwd.get(i).getUserId() >= 1000 && shadow.get(i).hasPassword()) {
                String password = null;

                String id = shadow.get(i).getId();
                String salt = shadow.get(i).getSalt();

                String data = shadow.get(i).getData();

                for (String entry : dictionary) {
                    if (Crypt.crypt(entry, "$" + id + "$" + salt).equals(data)) {
                        password = entry;
                        break;
                    }
                }

                if (password == null) {
                    password = "<not found>";
                }

                System.out.println("User ID  : " + passwd.get(i).getUserId());
                System.out.println("Username : " + passwd.get(i).getUsername());
                System.out.println("Password : " + password);
                System.out.println();
            }
        }
    }

    public void loadPasswdFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String entry;

        while ((entry = reader.readLine()) != null) {
            this.passwd.add(new PasswordFileEntry(entry));
        }
    }

    public void loadShadowFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String entry;

        while ((entry = reader.readLine()) != null) {
            this.shadow.add(new ShadowFileEntry(entry));
        }
    }

    public void loadDictionaryFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String entry;

        while ((entry = reader.readLine()) != null) {
            this.dictionary.add(entry);
        }
    }
}
