
package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class AccountStore {

    private HashMap<String, Account> accounts;
    private ArrayList<String> transactionHistory;

    public AccountStore() {
        accounts = new HashMap<>();
        transactionHistory = new ArrayList<>();
    }

    public void add(Account account) {
        if (account != null) {
            accounts.put(account.getAccountNumber(), account);
            transactionHistory.add(
                "Account added: " + account.getAccountNumber()
            );
        }
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public ArrayList<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public ArrayList<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> sorted = new ArrayList<>(accounts.values());

        sorted.sort(
            Comparator.comparingLong(Account::getBalance).reversed()
        );

        return sorted;
    }

    public ArrayList<Account> getAccountsAbove(long amount) {
        ArrayList<Account> result = new ArrayList<>();

        for (Account account : accounts.values()) {
            if (account.getBalance() > amount) {
                result.add(account);
            }
        }

        return result;
    }
}
