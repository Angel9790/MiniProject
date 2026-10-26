
package model;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.util.Objects;
import model.annotation.Id;
import model.annotation.Positive;

public abstract class Account implements Transactable, InterestBearing
{
    @Id
    private final String accountNumber;

    private final String ownerName;

    @Positive
    private long balance;

    private boolean active;

    public Account(String accountNumber, String ownerName, long balance)
    {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public long getBalance()
    {
        return balance;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override
    public void deposit(long amount) throws InvalidAmountException
    {
        if (amount <= 0)
        {
            throw new InvalidAmountException(
                "Deposit amount must be greater than zero."
            );
        }

        if (!active)
        {
            throw new InvalidAmountException(
                "Cannot deposit into an inactive account."
            );
        }

        balance += amount;
    }

    @Override
    public void withdraw(long amount)
        throws InsufficientFundsException, InvalidAmountException
    {
        if (amount <= 0)
        {
            throw new InvalidAmountException(
                "Withdrawal amount must be greater than zero."
            );
        }

        if (!active)
        {
            throw new InvalidAmountException(
                "Cannot withdraw from an inactive account."
            );
        }

        if (amount > balance)
        {
            throw new InsufficientFundsException(amount - balance);
        }

        balance -= amount;
    }

    public void transfer(Account to, long amount) throws BankException
    {
        try
        {
            if (to == null)
            {
                throw new AccountNotFoundException("Receiver account");
            }

            if (this.equals(to))
            {
                throw new InvalidAmountException(
                    "Cannot transfer to the same account."
                );
            }

            withdraw(amount);
            to.deposit(amount);

            System.out.println("Transfer completed successfully.");
        }
        catch (BankException e)
        {
            System.out.println("Transfer failed: " + e.getMessage());
            throw e;
        }
        finally
        {
            System.out.println("Transfer attempt completed.");
        }
    }

    public double yearlyInterest()
    {
        return getBalance() * interestRate() / 100.0;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof Account))
        {
            return false;
        }

        Account other = (Account) obj;

        return Objects.equals(accountNumber, other.accountNumber);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString()
    {
        return "Account No: " + accountNumber
            + " | Owner: " + ownerName
            + " | Balance: Rs. " + balance
            + " | Status: " + (active ? "Active" : "Inactive");
    }
}
