
package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public synchronized long getBalance() {
        return balance;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public synchronized void deposit(long amount)
            throws InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                "Amount must be greater than zero."
            );
        }

        balance += amount;
    }

    public synchronized boolean canWithdraw(long amount) {
        return amount > 0 && amount <= balance;
    }

    public synchronized boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                "Amount must be greater than zero."
            );
        }

        if (amount > balance) {
            throw new InsufficientFundsException(amount - balance);
        }

        balance -= amount;
        return true;
    }

    public void transfer(Account receiver, long amount)
            throws BankException {

        if (receiver == null) {
            throw new BankException("Receiver account not found.");
        }

        if (receiver == this) {
            throw new BankException(
                "Cannot transfer to the same account."
            );
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                "Amount must be greater than zero."
            );
        }

        synchronized (this) {
            synchronized (receiver) {
                if (amount > balance) {
                    throw new InsufficientFundsException(
                        amount - balance
                    );
                }

                balance -= amount;
                receiver.balance += amount;
            }
        }
    }

    public double interestRate() {
        return 0.0;
    }

    public double yearlyInterest() {
        return getBalance() * interestRate() / 100;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance
                + " | Active: " + active
                + " | Rate: " + interestRate() + "%"
                + " | Yearly interest: Rs. " + yearlyInterest();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Account)) {
            return false;
        }

        Account other = (Account) obj;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}
