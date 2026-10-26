
import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Account;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.SavingsAccount;
import service.AccountWorker;
import service.AnnotationValidator;
import service.BankResource;
import service.DeadlockDemo;
import service.TransactionBuffer;
import service.TransactionProcessor;
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
        testPractical7();
        testPractical8();
        testPractical9();
        testPractical10();

        int choice = 0;

        do
        {
            System.out.println("\n===== MINIBANK MENU =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Display Accounts");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try
            {
                choice = sc.nextInt();

                MenuOption selected = switch (choice)
                {
                    case 1 -> MenuOption.OPEN_ACCOUNT;
                    case 2 -> MenuOption.DEPOSIT;
                    case 3 -> MenuOption.WITHDRAW;
                    case 4 -> MenuOption.TRANSFER;
                    case 6 -> MenuOption.EXIT;
                    default -> null;
                };

                if (choice == 5)
                {
                    displayAccounts();
                }
                else if (selected == null)
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
            }
            catch (InputMismatchException e)
            {
                System.out.println("Please enter a valid menu number.");
                sc.nextLine();
                choice = 0;
            }
            finally
            {
                System.out.println("Menu operation completed.");
            }

        } while (choice != 6);

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
        try
        {
            System.out.print("Enter account number: ");
            String number = sc.next();

            Account account = findAccount(number);

            if (account == null)
            {
                throw new AccountNotFoundException(number);
            }

            System.out.print("Enter amount to deposit: ");
            long amount = sc.nextLong();

            account.deposit(amount);

            System.out.println("Deposit successful!");
            System.out.println(account);
        }
        catch (AccountNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (InvalidAmountException e)
        {
            System.out.println(e.getMessage());
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid amount. Please enter a whole number.");
            sc.nextLine();
        }
        finally
        {
            System.out.println("Deposit operation completed.");
        }
    }

    public static void withdrawMoney()
    {
        try
        {
            System.out.print("Enter account number: ");
            String number = sc.next();

            Account account = findAccount(number);

            if (account == null)
            {
                throw new AccountNotFoundException(number);
            }

            System.out.print("Enter amount to withdraw: ");
            long amount = sc.nextLong();

            account.withdraw(amount);

            System.out.println("Withdrawal successful!");
            System.out.println(account);
        }
        catch (InsufficientFundsException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Shortfall: " + e.getShortfall());
        }
        catch (AccountNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (InvalidAmountException e)
        {
            System.out.println(e.getMessage());
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid amount. Please enter a whole number.");
            sc.nextLine();
        }
        finally
        {
            System.out.println("Withdrawal operation completed.");
        }
    }

    public static void transferMoney()
    {
        try
        {
            System.out.print("Enter sender account number: ");
            String senderNumber = sc.next();

            System.out.print("Enter receiver account number: ");
            String receiverNumber = sc.next();

            Account sender = findAccount(senderNumber);
            Account receiver = findAccount(receiverNumber);

            if (sender == null)
            {
                throw new AccountNotFoundException(senderNumber);
            }

            if (receiver == null)
            {
                throw new AccountNotFoundException(receiverNumber);
            }

            System.out.print("Enter transfer amount: ");
            long amount = sc.nextLong();

            sender.transfer(receiver, amount);

            System.out.println("Transfer successful!");
            System.out.println("Sender: " + sender);
            System.out.println("Receiver: " + receiver);
        }
        catch (InsufficientFundsException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Shortfall: " + e.getShortfall());
        }
        catch (AccountNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (InvalidAmountException e)
        {
            System.out.println(e.getMessage());
        }
        catch (BankException e)
        {
            System.out.println(e.getMessage());
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid amount. Please enter a whole number.");
            sc.nextLine();
        }
        finally
        {
            System.out.println("Transfer operation completed.");
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

        WithdrawRule rule1 = new WithdrawRule()
        {
            @Override
            public boolean allow(Account account, long amount)
            {
                return amount > 0
                    && account.getBalance() - amount >= 1000;
            }
        };

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

    public static void testPractical7()
    {
        System.out.println("\n===== PRACTICAL 7 TESTS =====");
        System.out.println("--- Annotation Validation ---");

        Account testAccount =
            new SavingsAccount("AC0099", "Test", -500, 1000);

        String[] errors = AnnotationValidator.validate(testAccount);

        if (errors.length == 0)
        {
            System.out.println("No validation errors found.");
        }
        else
        {
            for (String error : errors)
            {
                System.out.println("Validation error: " + error);
            }
        }
    }

    public static void testPractical8()
    {
        System.out.println("\n===== PRACTICAL 8 TESTS =====");

        System.out.println("--- Try-with-resources test ---");

    try (BankResource resource = new BankResource())
    {
        resource.useResource();
    }
    catch (Exception e)
    {
        System.out.println("Resource error: " + e.getMessage());
    }
        System.out.println("\n--- Insufficient funds test ---");

        Account testAccount =
            new SavingsAccount("AC0089", "Test", 1000, 0);

        try
        {
            testAccount.withdraw(5000);
        }
        catch (InsufficientFundsException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Shortfall: " + e.getShortfall());
            System.out.println("Balance remains: "
                + testAccount.getBalance());
        }
        catch (BankException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            System.out.println("Practical 8 test completed.");
        }
    }
    
public static void testPractical9()
{
    System.out.println("\n===== PRACTICAL 9 TESTS =====");

    Account account =
        new SavingsAccount("AC0090", "Thread Test", 0, 0);

    int numberOfThreads = 10;
    int depositsPerThread = 1000;
    long amount = 1;

    Thread[] threads = new Thread[numberOfThreads];

    for (int i = 0; i < numberOfThreads; i++)
    {
        AccountWorker worker =
            new AccountWorker(account, depositsPerThread, amount);

        threads[i] = new Thread(worker, "Worker-" + (i + 1));
        threads[i].start();
    }

    for (int i = 0; i < numberOfThreads; i++)
    {
        try
        {
            threads[i].join();
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted.");
            return;
        }
    }

    System.out.println("Expected balance: 10000");
    System.out.println("Actual balance: " + account.getBalance());

    if (account.getBalance() == 10000)
    {
        System.out.println("Test passed!");
    }
    else
    {
        System.out.println("Test failed!");
    }
}

public static void testPractical10()
{
    System.out.println("\n===== PRACTICAL 10 TESTS =====");

    
    System.out.println("\n--- Thread Pool Test ---");

    TransactionProcessor processor = new TransactionProcessor();

    for (int i = 1; i <= 10; i++)
    {
        final int taskId = i;

        processor.submit(() ->
        {
            System.out.println(
                "Task " + taskId + " executed by "
                + Thread.currentThread().getName()
            );

            try
            {
                Thread.sleep(200);
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        });
    }

    processor.stop();

    System.out.println("All thread-pool tasks completed.");

  
    System.out.println("\n--- Producer-Consumer Test ---");

    TransactionBuffer buffer = new TransactionBuffer();
    int totalTransactions = 10;

    Thread producer = new Thread(() ->
    {
        try
        {
            for (int i = 1; i <= totalTransactions; i++)
            {
                buffer.add("TXN-" + i);
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }, "Producer");

    Thread consumer = new Thread(() ->
    {
        try
        {
            for (int i = 1; i <= totalTransactions; i++)
            {
                buffer.take();
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }, "Consumer");

    producer.start();
    consumer.start();

    try
    {
        producer.join();
        consumer.join();
    }
    catch (InterruptedException e)
    {
        Thread.currentThread().interrupt();
        System.out.println("Producer-consumer test interrupted.");
        return;
    }

    System.out.println("All transactions processed.");
    DeadlockDemo.runTests();
}

}
