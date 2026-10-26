
package service;

import model.Account;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class TransactionService {

    public static boolean transfer(Account from, Account to, long amount)
            throws BankException {

        if (from == null || to == null) {
            throw new BankException("Account not found.");
        }

        if (from == to) {
            throw new BankException(
                "Cannot transfer to the same account."
            );
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                "Amount must be greater than zero."
            );
        }

        if (from.withdraw(amount)) {
            try {
                to.deposit(amount);
                return true;
            } catch (InvalidAmountException e) {
                from.deposit(amount);
                throw e;
            }
        }

        return false;
    }
}
