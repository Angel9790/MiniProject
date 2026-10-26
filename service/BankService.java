package service;

import model.Account;

public class BankService {
    private final Account[] accounts = new Account[100];
    private int count = 0;

    public boolean addAccount(Account account) {
        if (count >= accounts.length) {
            return false;
        }
        accounts[count++] = account;
        return true;
    }

    public Account findAccount(String accountNumber) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    public int getCount() {
        return count;
    }
}