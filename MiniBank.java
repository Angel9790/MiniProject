import java.util.Scanner;

record BankInfo(String name, String branch) { }

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

public class MiniBank {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BankInfo bank = new BankInfo("MiniBank", "Anand Branch");

        System.out.println("==================================");
        System.out.println("        " + bank.name());
        System.out.println("     Branch : " + bank.branch());
        System.out.println("==================================");

        Customer c1 = new Customer("Angel", "angel@gmail.com", "9876543210");

        System.out.println("\nCustomer Details");
        System.out.println("Customer ID: " + c1.getCustomerId());
        System.out.println("Name: " + c1.getName());
        System.out.println("Email: " + c1.getEmail());
        System.out.println("Mobile: " + c1.getMobile());


        Account[] accounts = new Account[3];

        accounts[0] = new Account("Angel", 1000);
        accounts[1] = new Account("Riya");
        accounts[2] = new Account("Arav", 2000);

        accounts[0].deposit(500);
        accounts[0].withdraw(200);

        accounts[1].deposit(1000);
        accounts[1].withdraw(300);

        accounts[2].deposit(500);
        accounts[2].withdraw(3000);

    
        System.out.println("\n----------- ACCOUNT DETAILS -----------");

        for (int i = 0; i < accounts.length; i++) {
            System.out.println("\nAccount Number: " +
                    accounts[i].getAccountNumber());
            System.out.println("Owner: " +
                    accounts[i].getOwnerName());
            System.out.println("Balance: Rs. " +
                    accounts[i].getBalance());
            System.out.println("Active: " +
                    accounts[i].isActive());
        }

        int choice;

        do {
            System.out.println("\n----------- MENU -----------");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            String message = switch (choice) {
                case 1 -> "Open Account - To be implemented in a later lab.";
                case 2 -> "Deposit - To be implemented in a later lab.";
                case 3 -> "Withdraw - To be implemented in a later lab.";
                case 4 -> "Transfer - To be implemented in a later lab.";
                case 5 -> "Thank you for using MiniBank!";
                default -> "Invalid Choice! Please try again.";
            };

            System.out.println(message);

        } while (choice != 5);

        sc.close();
    }
}