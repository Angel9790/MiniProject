
import java.util.Scanner;
import model.Account;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.SavingsAccount;
import util.Command;
import util.CommandParser;
import util.StatementFormatter;
import util.Validator;
import static util.Validator.isValidEmail;
import util.WithdrawRule;

record BankInfo(String name, String branch)
{
}

enum MenuOption
{
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

public class MiniBank
{
    static Scanner sc = new Scanner(System.in);

    static Customer[] customers = new Customer[100];
    static Account[] accounts = new Account[100];

    static int customerCount = 0;
    static int accountCount = 0;

    public static void main(String[] args)
    {
        BankInfo bank = new BankInfo("MiniBank", "Anand Branch");

        System.out.println("================================");
        System.out.println(bank.name());
        System.out.println("Branch: " + bank.branch());
        System.out.println("================================");

        Customer c1 = new Customer(
            "Angel", "angel@example.com", "9876543210"
        );

        Customer c2 = new Customer(
            "Riya", "riya@example.com", "9876543211"
        );

        customers[customerCount++] = c1;
        customers[customerCount++] = c2;

        accounts[accountCount++] =
            new SavingsAccount("AC0001", "Angel", 1300, 500);

        accounts[accountCount++] =
            new CurrentAccount("AC0002", "Riya", 700, 1000);

        accounts[accountCount++] =
            new FixedDepositAccount("AC0003", "Arav", 2500);

        testPractical3();
        testPractical4();
        testPractical6();

        int choice;

        do
        {
            System.out.println("\n===== MINIBANK MENU =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            MenuOption selected = switch (choice)
            {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            if (selected == null)
            {
                System.out.println("Invalid choice!");
            }
            else
            {
                switch (selected)
                {
                    case OPEN_ACCOUNT:
                        System.out.println(
                            "Open Account - to be implemented in a later lab"
                        );
                        break;

                    case DEPOSIT:
                        depositMoney();
                        break;

                    case WITHDRAW:
                        withdrawMoney();
                        break;

                    case TRANSFER:
                        transferMoney();
                        break;

                    case EXIT:
                        System.out.println(
                            "Thank you for using MiniBank!"
                        );
                        break;
                }
            }

        } while (choice != 5);

        sc.close();
    }

    public static void displayAccounts()
    {
        System.out.println("\n--- Account Details ---");

        for (int i = 0; i < accountCount; i++)
        {
            System.out.println(accounts[i]);
        }
    }

    public static void depositMoney()
    {
        System.out.print("Enter account number: ");
        String number = sc.next();

        Account account = findAccount(number);

        if (account != null)
        {
            System.out.print("Enter amount to deposit: ");
            long amount = sc.nextLong();

            if (amount > 0 && account.isActive())
            {
                account.deposit(amount);
                System.out.println("Deposit successful!");
                System.out.println(account);
            }
            else
            {
                System.out.println(
                    "Invalid amount or inactive account."
                );
            }
        }
        else
        {
            System.out.println("Account not found!");
        }
    }

    public static void withdrawMoney()
    {
        System.out.print("Enter account number: ");
        String number = sc.next();

        Account account = findAccount(number);

        if (account != null)
        {
            System.out.print("Enter amount to withdraw: ");
            long amount = sc.nextLong();

            if (account.withdraw(amount))
            {
                System.out.println("Withdrawal successful!");
                System.out.println(account);
            }
            else
            {
                System.out.println("Withdrawal failed!");
            }
        }
        else
        {
            System.out.println("Account not found!");
        }
    }

    public static void transferMoney()
    {
        System.out.print("Enter sender account number: ");
        String senderNumber = sc.next();

        System.out.print("Enter receiver account number: ");
        String receiverNumber = sc.next();

        Account sender = findAccount(senderNumber);
        Account receiver = findAccount(receiverNumber);

        if (sender != null && receiver != null
                && !sender.equals(receiver))
        {
            System.out.print("Enter transfer amount: ");
            long amount = sc.nextLong();

            if (amount > 0 && sender.withdraw(amount))
            {
                receiver.deposit(amount);

                System.out.println("Transfer successful!");
                System.out.println("Sender: " + sender);
                System.out.println("Receiver: " + receiver);
            }
            else
            {
                System.out.println("Transfer failed!");
            }
        }
        else
        {
            System.out.println("Invalid accounts!");
        }
    }

    public static Account findAccount(String number)
    {
        for (int i = 0; i < accountCount; i++)
        {
            if (accounts[i].getAccountNumber().equals(number))
            {
                return accounts[i];
            }
        }

        return null;
    }

    public static void testPractical3()
    {
        System.out.println("\n===== PRACTICAL 3 TESTS =====");

        Account a1 =
            new SavingsAccount("AC0099", "Riya", 4000, 0);

        Account a2 =
            new SavingsAccount("AC0099", "Riya", 4000, 0);

        System.out.println("Account: " + a1);
        System.out.println("Accounts equal: " + a1.equals(a2));

        System.out.println("Same hash code: "
                + (a1.hashCode() == a2.hashCode()));

        Object obj = a1;

        System.out.println("Is Account: "
                + (obj instanceof Account));

        Customer.Address address = new Customer.Address(
            "Main Road", "Anand", "388001"
        );

        Customer c1 = new Customer(
            "Angel", "angel@example.com", "9876543210", address
        );

        System.out.println("Customer ID: " + c1.getCustomerId());
        System.out.println("Address: " + c1.getAddress());

        Customer c2 = c1.clone();

        System.out.println("Original ID: " + c1.getCustomerId());
        System.out.println("Cloned ID: " + c2.getCustomerId());

        System.out.println("Different customer objects: "
                + (c1 != c2));

        System.out.println("Different address objects: "
                + (c1.getAddress() != c2.getAddress()));
    }

    public static void testPractical4()
    {
        System.out.println("\n===== PRACTICAL 4 TESTS =====");

        System.out.println("Valid mobile: "
                + Validator.isValidMobile("9876543210"));

        System.out.println("Invalid mobile: "
                + Validator.isValidMobile("12345"));

        // Static import: no Validator class name needed
        System.out.println("Valid email: "
                + isValidEmail("angel@example.com"));

        System.out.println("Invalid email: "
                + isValidEmail("angel@"));

        System.out.println("Valid PAN: "
                + Validator.isValidPan("ABCDE1234F"));

        System.out.println("Invalid PAN: "
                + Validator.isValidPan("ABC123"));

        System.out.println("Valid IFSC: "
                + Validator.isValidIfsc("SBIN0001234"));

        System.out.println("Invalid IFSC: "
                + Validator.isValidIfsc("123"));

        System.out.println("\n--- Command Parser Test ---");

        try
        {
            Command command =
                CommandParser.parse("DEPOSIT AC0001 500");

            System.out.println("Command type: " + command.type());
            System.out.println("Account number: "
                    + command.accountNumber());
            System.out.println("Amount: " + command.amount());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n--- Account Statement ---");

        if (accountCount > 0)
        {
            System.out.println(
                StatementFormatter.buildStatement(accounts[0])
            );
        }
    }

  
public static void testPractical6()
{
    System.out.println("\n===== PRACTICAL 6 TESTS =====");

    Account account =
        new SavingsAccount("AC0088", "Test", 10000, 1000);

    // Anonymous class
    WithdrawRule rule1 = new WithdrawRule()
    {
        @Override
        public boolean allow(Account account, long amount)
        {
            return amount > 0
                && account.getBalance() - amount >= 1000;
        }
    };

    // Lambda expression
    WithdrawRule rule2 =
        (acc, amount) -> amount > 0
            && acc.getBalance() - amount >= 1000;

    System.out.println("Anonymous class result: "
        + rule1.allow(account, 2000));

    System.out.println("Lambda result: "
        + rule2.allow(account, 2000));

    System.out.println("\n--- Interest Rates ---");

    Account[] testAccounts = {
        new SavingsAccount("AC0001", "Angel", 1300, 500),
        new CurrentAccount("AC0002", "Riya", 700, 1000),
        new FixedDepositAccount("AC0003", "Arav", 2500)
    };

    for (Account acc : testAccounts)
    {
        System.out.println(
            acc.getOwnerName()
            + " | Rate: " + acc.interestRate() + "%"
            + " | Yearly interest: Rs. "
            + acc.yearlyInterest()
        );
    }

    if (account instanceof SavingsAccount)
    {
        System.out.println("Savings account detected.");
    }
}

    }

