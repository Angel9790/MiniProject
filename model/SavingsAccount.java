
package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class SavingsAccount extends Account {

    private double rate;

    public SavingsAccount(String accountNumber, String ownerName,
                          long balance, double rate) {
        super(accountNumber, ownerName, balance);
        this.rate = rate;
    }

 

    @Override
public String toString() {
    return super.toString()
            + " | Rate: " + interestRate() + "%"
            + " | Yearly interest: Rs. " + yearlyInterest();
}
@Override
public double interestRate() {
    return rate;
}

@Override
public double yearlyInterest() {
    return getBalance() * interestRate() / 100;
}
}
