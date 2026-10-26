
public class CommandParser
{
    public static Command parse(String line)
    {
        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3)
        {
            throw new IllegalArgumentException(
                "Enter: TYPE ACCOUNT_NUMBER AMOUNT"
            );
        }

        TransactionType type =
            TransactionType.valueOf(parts[0].toUpperCase());

        String accountNumber = parts[1];
        long amount = Long.parseLong(parts[2]);

        if (amount <= 0)
        {
            throw new IllegalArgumentException(
                "Amount must be positive."
            );
        }

        if (type == TransactionType.TRANSFER)
        {
            throw new IllegalArgumentException(
                "TRANSFER needs a receiver account too."
            );
        }

        return new Command(type, accountNumber, amount);
    }
}
