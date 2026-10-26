
import java.util.Objects;

public class Account
{
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 1;

    public Account(String ownerName, long balance)
    {
        this.accountNumber = "AC" + String.format("%04d", accountCounter++);
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public Account(String ownerName)
    {
        this(ownerName, 0);
    }

    public Account(String accountNumber, String ownerName, long balance)
    {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public void deposit(long amount)
    {
        if (amount > 0 && active)
        {
            balance = balance + amount;
        }
    }

    public boolean withdraw(long amount)
    {
        if (amount > 0 && active && balance >= amount)
        {
            balance = balance - amount;
            return true;
        }

        return false;
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

    public String toString()
    {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (!(o instanceof Account))
        {
            return false;
        }

        Account other = (Account) o;

        return accountNumber.equals(other.accountNumber);
    }

    public int hashCode()
    {
        return Objects.hash(accountNumber);
    }
}
