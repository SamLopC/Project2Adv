package project2adv;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer extends Person {
    private static final AtomicInteger idGenerator = new AtomicInteger(1000);
    private static final AtomicInteger accountNumGenerator = new AtomicInteger(5000);
    private int userID;
    protected Account account; // Declare the account field here

    public Customer(String name, Account account) {
        super(name);
        this.userID = idGenerator.getAndIncrement();
        this.account = account; // Initialize the account field here
        account.accountNum = String.valueOf(accountNumGenerator.getAndIncrement());
    }

    public void paySomeone(Customer recipient, double amount) {
        if (this == recipient) {
            throw new IllegalArgumentException("Cannot pay yourself.");
        }
        if (account.showBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds for payment.");
        }

        // Withdraw amount from payer's account
        this.account.withdraw(amount, this.getName(), true);

        // Deposit amount to recipient's account
        recipient.getAccount().deposit(amount, recipient.getName(), true);

        System.out.println(this.getName() + " paid $" + amount + " to " + recipient.getName() + ".");
    }

    @Override
    public void displayPersonInfo() {
        System.out.println("Customer Name: " + name + ", User ID: " + userID);
    }

    public void displayAccountInfo() {
        displayPersonInfo();
        account.displayAccountInfo();
    }

    public void depositMoney(double amount) {
        account.deposit(amount, name, true);
    }

    public void withdrawMoney(double amount) {
        account.withdraw(amount, name, true);
    }

    public void transferMoney(Customer recipient, double amount) {
        account.transfer(recipient, amount);
    }

    public Account getAccount() {
        return account;
    }
}
