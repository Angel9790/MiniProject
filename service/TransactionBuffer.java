
package service;

public class TransactionBuffer
{
    private String[] buffer = new String[3];
    private int count = 0;
    private int in = 0;
    private int out = 0;

    public synchronized void add(String transaction)
        throws InterruptedException
    {
        while (count == buffer.length)
        {
            wait();
        }

        buffer[in] = transaction;
        in = (in + 1) % buffer.length;
        count++;

        System.out.println("Produced: " + transaction);

        notifyAll();
    }

    public synchronized String take()
        throws InterruptedException
    {
        while (count == 0)
        {
            wait();
        }

        String transaction = buffer[out];
        out = (out + 1) % buffer.length;
        count--;

        System.out.println("Processed: " + transaction);

        notifyAll();

        return transaction;
    }
}
