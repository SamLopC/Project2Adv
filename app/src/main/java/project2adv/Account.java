package project2adv;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public abstract class Account implements AccountInfo, TransactionHandler {
    protected String accountNum;
    protected double accountBal;

    public Account(String accountNum, double accountBal) {
        this.accountNum = accountNum;
        this.accountBal = accountBal;
    }

    public abstract void displayAccountInfo();
    
    protected void logTransaction(String logMessage) {
        try (FileWriter writer = new FileWriter("Atransactions_log.txt", true)) {
            writer.write(LocalDateTime.now() + " - " + logMessage + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }

    @Override
    public void deposit(double amount, String customerName, boolean logTransaction) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        accountBal += amount;
        System.out.println("Deposit of $" + amount + " successful.");
        if (logTransaction) {
            logTransaction(customerName + " deposited $" + amount + " to " + getAccountType() + "-" + accountNum 
                + ". New Balance: $" + accountBal);
        }
    }

    @Override
    public void withdraw(double amount, String customerName, boolean logTransaction) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (accountBal < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
        accountBal -= amount;
        System.out.println("Withdrawal of $" + amount + " successful.");
        if (logTransaction) {
            logTransaction(customerName + " withdrew $" + amount + " from " + getAccountType() + "-" + accountNum 
                + ". New Balance: $" + accountBal);
        }
    }

    @Override
    public double showBalance() {
        return accountBal;
    }  

    @Override
    public void transfer(Customer recipient, double amount) {
        if (this == recipient.getAccount()) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        if (accountBal < amount) {
            throw new IllegalArgumentException("Insufficient funds for transfer.");
        }
        this.withdraw(amount, recipient.getName(), false);
        recipient.getAccount().deposit(amount, recipient.getName(), false);
        logTransaction("Transfer of $" + amount + " to " + recipient.getName() + " successful.");
    }
    

    public abstract String getAccountType();

    public String getAccountNum() {
        return accountNum;
    }
}
