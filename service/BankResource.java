package service;

public class BankResource implements AutoCloseable
{
    public BankResource()
    {
        System.out.println("Bank resource opened.");
    }

    public void useResource()
    {
        System.out.println("Bank resource is being used.");
    }

    @Override
    public void close()
    {
        System.out.println("Bank resource closed.");
    }
}