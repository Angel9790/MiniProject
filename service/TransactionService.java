package service;

import model.Account;

public class TransactionService {

    public static boolean transfer(Account from, Account to, long amount) {
        if (from == null || to == null || from.equals(to) || amount <= 0) {
            return false;
        }
        if (from.withdraw(amount)) {
            to.deposit(amount);
            return true;
        }
        return false;
    }
}