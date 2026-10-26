
package service;

import java.util.concurrent.CountDownLatch;

import model.Account;
import model.SavingsAccount;
import exception.BankException;

public class DeadlockDemo
{
    public static void runTests()
    {
        System.out.println("\n--- Deadlock Demonstration ---");

        Account a = new SavingsAccount("AC0101", "Angel", 1000, 0);
        Account b = new SavingsAccount("AC0102", "Riya", 1000, 0);

        CountDownLatch bothLocked = new CountDownLatch(2);

        Thread t1 = new Thread(() ->
        {
            synchronized (a)
            {
                bothLocked.countDown();

                try
                {
                    bothLocked.await();
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                    return;
                }

                synchronized (b)
                {
                    System.out.println("Thread 1 acquired both accounts.");
                }
            }
        }, "Transfer-A-to-B");

        Thread t2 = new Thread(() ->
        {
            synchronized (b)
            {
                bothLocked.countDown();

                try
                {
                    bothLocked.await();
                }
                catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                    return;
                }

                synchronized (a)
                {
                    System.out.println("Thread 2 acquired both accounts.");
                }
            }
        }, "Transfer-B-to-A");

        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        try
        {
            t1.join(500);
            t2.join(500);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            System.out.println("Deadlock demonstration interrupted.");
            return;
        }

        if (t1.isAlive() && t2.isAlive())
        {
            System.out.println("Deadlock detected: both threads are waiting.");
        }
        else
        {
            System.out.println("The deadlock did not occur in this run.");
        }

        System.out.println("\n--- Fixed Transfer Test ---");

        Account first = new SavingsAccount("AC0201", "Angel", 1000, 0);
        Account second = new SavingsAccount("AC0202", "Riya", 1000, 0);

        Thread transfer1 = new Thread(() ->
        {
            orderedTransfer(first, second, 100);
        }, "Fixed-A-to-B");

        Thread transfer2 = new Thread(() ->
        {
            orderedTransfer(second, first, 100);
        }, "Fixed-B-to-A");

        transfer1.start();
        transfer2.start();

        try
        {
            transfer1.join();
            transfer2.join();
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            System.out.println("Fixed transfer test interrupted.");
            return;
        }

        System.out.println("Both transfers completed.");
        System.out.println("Account A balance: " + first.getBalance());
        System.out.println("Account B balance: " + second.getBalance());
    }

    private static void orderedTransfer(
        Account from, Account to, long amount)
    {
        Account firstLock;
        Account secondLock;

        if (from.getAccountNumber().compareTo(
            to.getAccountNumber()) < 0)
        {
            firstLock = from;
            secondLock = to;
        }
        else
        {
            firstLock = to;
            secondLock = from;
        }

        synchronized (firstLock)
        {
            synchronized (secondLock)
            {
                try
                {
                    from.withdraw(amount);
                    to.deposit(amount);

                    System.out.println(
                        Thread.currentThread().getName()
                        + " completed transfer."
                    );
                }
                catch (BankException e)
                {
                    System.out.println(
                        "Transfer failed: " + e.getMessage()
                    );
                }
            }
        }
    }
}
