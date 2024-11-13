package project2adv;
/**
 * The {@code Account} abstract class represents a generic bank account with basic functionalities
 * for handling deposits, withdrawals, and transfers. It maintains essential details such as account 
 * number, balance, and starting balance, and provides basic transaction logging capabilities.
 * 
 * <p>This class implements {@link AccountInfo} and {@link TransactionHandler} interfaces to define
 * necessary methods for managing account-related actions. Specific account types should extend this 
 * class and implement additional details as needed.
 * 
 * @see AccountInfo
 * @see TransactionHandler
 * @see TransactionLogger
 */
public abstract class Account implements AccountInfo, TransactionHandler {
    /**
     * The account number associated with this account.
     */
    protected String accountNum;

    /**
     * The current balance of this account.
     */
    protected double accountBal;

    /**
     * The starting balance of the account, initially set to the opening balance.
     */
    protected double startingBalance;

    /**
     * Constructs a new {@code Account} instance with the specified account number and initial balance.
     *
     * @param accountNum  the account number to be assigned
     * @param accountBal  the opening balance of the account
     */
    public Account(String accountNum, double accountBal) {
        this.accountNum = accountNum;
        this.accountBal = accountBal;
        this.startingBalance = accountBal;  // Set starting balance at creation
    }

    /**
     * Displays the account information. This is an abstract method that must be implemented by subclasses.
     */
    public abstract void displayAccountInfo();

    /**
     * Logs a transaction with the specified message.
     *
     * @param logMessage  the message to log
     */
    protected void logTransaction(String logMessage) {
        TransactionLogger.getInstance().log(logMessage);
    }

    /**
     * Deposits a specified amount into the account. The transaction can be optionally logged.
     *
     * @param amount          the amount to deposit, must be positive
     * @param customerName    the name of the customer making the deposit
     * @param logTransaction  flag indicating if the transaction should be logged
     * @throws IllegalArgumentException if the amount is non-positive
     */
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

    /**
     * Withdraws a specified amount from the account. The transaction can be optionally logged.
     *
     * @param amount          the amount to withdraw, must be positive and within account balance
     * @param customerName    the name of the customer making the withdrawal
     * @param logTransaction  flag indicating if the transaction should be logged
     * @throws IllegalArgumentException if the amount is non-positive or exceeds current balance
     */
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

    /**
     * Retrieves the current balance of the account.
     *
     * @return the current balance
     */
    @Override
    public double showBalance() {
        return accountBal;
    }

    /**
     * Retrieves the account type. This is an abstract method to be implemented by subclasses.
     *
     * @return the account type as a string
     */
    public abstract String getAccountType();

    /**
     * Retrieves the account number.
     *
     * @return the account number
     */
    public String getAccountNum() {
        return accountNum;
    }

    /**
     * Retrieves the starting balance of the account.
     *
     * @return the starting balance
     */
    public double getStartingBalance() {
        return startingBalance;
    }

    /**
     * Resets the starting balance to the current account balance. This can be used for session management.
     */
    public void resetStartingBalance() {
        this.startingBalance = accountBal;
    }

    /**
     * Transfers a specified amount to another customer's checking account. The transaction is logged,
     * and the current account balance is updated.
     *
     * @param recipient  the recipient customer who will receive the funds
     * @param amount     the amount to transfer, must be positive and within account balance
     * @throws IllegalArgumentException if the recipient is null or if the amount exceeds current balance
     */
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
