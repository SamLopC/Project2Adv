package project2adv;

public abstract class Account implements AccountInfo, TransactionHandler {
    protected String accountNum;
    protected double accountBal;
    protected double startingBalance;

    public Account(String accountNum, double accountBal) {
        this.accountNum = accountNum;
        this.accountBal = accountBal;
        this.startingBalance = accountBal;  // Set starting balance at creation
    }

    public abstract void displayAccountInfo();

    protected void logTransaction(String logMessage) {
        TransactionLogger.getInstance().log(logMessage);
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

    public abstract String getAccountType();

    public String getAccountNum() {
        return accountNum;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    // Additional Method: Reset Starting Balance (for session management)
    public void resetStartingBalance() {
        this.startingBalance = accountBal;
    }

    // Additional Method: Transfer funds to another customer’s account
    @Override
    public void transfer(Customer recipient, double amount) {
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient cannot be null.");
        }
        if (accountBal < amount) {
            throw new IllegalArgumentException("Insufficient funds for transfer.");
        }

        // Withdraw from this account
        this.withdraw(amount, "Transfer", false);

        // Deposit into recipient's checking account by default
        recipient.getCheckingAccount().deposit(amount, recipient.getName(), false);

        logTransaction("Transferred $" + amount + " to " + recipient.getName() + "'s " 
            + recipient.getCheckingAccount().getAccountType() + "-" + recipient.getCheckingAccount().getAccountNum() 
            + ". New Balance: $" + accountBal);
        
        System.out.println("Transfer of $" + amount + " to " + recipient.getName() + " successful.");
    }
}
