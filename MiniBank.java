
import java.util.Scanner;

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

        accounts[accountCount++] = new Account("Angel", 1300);
        accounts[accountCount++] = new Account("Riya", 700);
        accounts[accountCount++] = new Account("Arav", 2500);

        testPractical3();

        int choice;

        do
        {
            System.out.println("\n===== MINIBANK MENU =====");
            System.out.println("1. Display Accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice)
            {
                case 1:
                    displayAccounts();
                    break;

                case 2:
                    depositMoney();
                    break;

                case 3:
                    withdrawMoney();
                    break;

                case 4:
                    transferMoney();
                    break;

                case 5:
                    System.out.println("Thank you for using MiniBank!");
                    break;

                default:
                    System.out.println("Invalid choice!");
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
                System.out.println("Invalid amount or inactive account.");
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

            if (sender.withdraw(amount))
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

        Account a1 = new Account("AC0099", "Riya", 4000);
        Account a2 = new Account("AC0099", "Riya", 4000);

        System.out.println("Account: " + a1);

        System.out.println("Accounts equal: " + a1.equals(a2));

        System.out.println("Same hash code: "
                + (a1.hashCode() == a2.hashCode()));

        Object obj = a1;
        System.out.println("Is Account: " + (obj instanceof Account));

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
}
