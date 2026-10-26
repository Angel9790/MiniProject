package model;

public class FixedDepositAccount extends Account {

    public FixedDepositAccount(String accountNumber, String ownerName, long balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return false;   // deposit is locked
    }
}