
import model.Account;
import model.Repository;

public class RepositoryTest {

    public static void main(String[] args) {

        Repository<Account> accountRepository =
                new Repository<>();

        Account a1 = new Account("AC0001", "Angel", 5000);
        Account a2 = new Account("AC0002", "Riya", 2000);
        Account a3 = new Account("AC0003", "Arav", 9000);

        accountRepository.saveAll(a1, a2, a3);

        System.out.println("Account found:");
        System.out.println(
                accountRepository.findById("AC0002"));

        System.out.println("\nAll accounts:");

        for (Account account : accountRepository.findAll()) {
            System.out.println(account);
        }

        System.out.println("\nTotal accounts: "
                + accountRepository.size());

        System.out.println("\nDeleting AC0002: "
                + accountRepository.delete("AC0002"));

        System.out.println("Total after deletion: "
                + accountRepository.size());
    }
}
