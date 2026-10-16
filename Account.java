public class Account {

    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static long accountCounter = 0;

    public Account(String ownerName, long openingBalance) {
        accountCounter++;
        this.accountNumber = String.format("AC%04d", accountCounter);
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        if (active && amount > 0) {
            balance = balance + amount;
            System.out.println("Deposit successful: Rs. " + amount);
        } else {
            System.out.println("Invalid deposit amount or inactive account.");
        }
    }

    public boolean withdraw(long amount) {
        if (active && amount > 0 && balance >= amount) {
            balance = balance - amount;
            System.out.println("Withdrawal successful: Rs. " + amount);
            return true;
        }

        System.out.println("Withdrawal failed! Insufficient balance or invalid amount.");
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }
}