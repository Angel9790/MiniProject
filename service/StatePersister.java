
package service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

import model.Account;

public class StatePersister {

    public static void save(Account[] accounts, Path file)
            throws IOException {

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(file.toFile()))) {

            out.writeObject(accounts);
        }

        System.out.println("Accounts saved successfully.");
    }

    public static Account[] load(Path file)
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(file.toFile()))) {

            Account[] accounts = (Account[]) in.readObject();

            System.out.println("Accounts loaded successfully.");

            return accounts;
        }
    }
}
