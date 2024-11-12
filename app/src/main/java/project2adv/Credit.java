package project2adv;

public class Credit extends Account {
    private double credLimit;

    public Credit(String accountNum, double accountBal, double credLimit) {
        super(accountNum, accountBal);
        this.credLimit = credLimit;
    }

    @Override
    public void displayAccountInfo() {
        System.out.println("Credit Account Number: " + accountNum);
        System.out.println("Balance: $" + accountBal);
        System.out.println("Credit Limit: $" + credLimit);
    }

    @Override
    public void withdraw(double amount, String customerName, boolean logTransaction) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if ((accountBal + amount) > credLimit) {
            throw new IllegalArgumentException("Credit limit exceeded.");
        }
        accountBal += amount;
        System.out.println("Charged $" + amount + " to your credit account.");
        if (logTransaction) {
            logTransaction(customerName + " charged $" + amount + " to Credit-" + accountNum + ". "
                + "New Balance: $" + accountBal);
        }
    }

    @Override
    public String getAccountType() {
        return "Credit";
    }

    public String getCreditLimit() {
        return String.valueOf(credLimit); // Returns credit limit as a String
    }

    @Override
    public void deposit(double amount, String customerName, boolean logTransaction) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        
        // New logic: Check if the deposit exceeds the outstanding balance
        if (accountBal + amount > 0) {  // Assuming balance is negative for credit accounts
            throw new IllegalArgumentException("Deposit exceeds the outstanding balance.");
        }

        accountBal += amount;
        System.out.println("Deposit of $" + amount + " successful.");
        if (logTransaction) {
            logTransaction(customerName + " deposited $" + amount + " to " + getAccountType() + "-" + accountNum 
                + ". New Balance: $" + accountBal);
        }
    }

}
