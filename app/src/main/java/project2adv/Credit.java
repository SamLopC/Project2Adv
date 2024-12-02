package project2adv;

/**
 * The {@code Credit} class represents a credit account with an assigned credit limit.
 * It extends the {@link Account} class, overriding methods to handle unique credit account
 * behaviors, such as withdrawal constraints based on credit limits and deposits specifically
 * for outstanding balance repayment.
 *
 * @see Account
 */
public class Credit extends Account {
    /**
     * The credit limit assigned to the credit account.
     */
    private double credLimit;

    /**
     * Constructs a new {@code Credit} account with the specified account number, initial balance, and credit limit.
     *
     * @param accountNum the account number to assign to this credit account
     * @param accountBal the initial balance of the credit account
     * @param credLimit  the credit limit assigned to this account
     */
    public Credit(String accountNum, double accountBal, double credLimit) {
        super(accountNum, accountBal);
        this.credLimit = credLimit;
    }

    /**
     * Displays information about the credit account, including account number, current balance, 
     * and credit limit.
     */
    @Override
    public void displayAccountInfo() {
        System.out.println("Credit Account Number: " + accountNum);
        System.out.println("Balance: $" + accountBal);
        System.out.println("Credit Limit: $" + credLimit);
    }

    /**
     * Withdraws (charges) a specified amount to the credit account, as long as it does not 
     * exceed the credit limit. The transaction can be optionally logged.
     *
     * @param amount          the amount to charge, must be positive and within credit limit
     * @param customerName    the name of the customer making the charge
     * @param logTransaction  flag indicating if the transaction should be logged
     * @throws IllegalArgumentException if the amount is non-positive or exceeds the available credit limit
     */
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

    /**
     * Retrieves the type of the account, which is "Credit" for instances of this class.
     *
     * @return a {@code String} representing the account type, which is "Credit"
     */
    @Override
    public String getAccountType() {
        return "Credit";
    }

    /**
     * Retrieves the credit limit of this credit account as a {@code String}.
     *
     * @return the credit limit as a string
     */
    public String getCreditLimit() {
        return String.valueOf(credLimit);
    }

    /**
     * Deposits a specified amount to the credit account to reduce the outstanding balance. 
     * Deposits that exceed the negative balance (overpayment) are allowed but logged as a warning.
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

        // Check for overpayment
        if (accountBal + amount > 0) {
            System.out.println("Warning: Deposit exceeds the outstanding balance and results in a positive balance.");
        }

        accountBal += amount; // Update the balance
        System.out.println("Deposit of $" + amount + " successful.");

        if (logTransaction) {
            logTransaction(customerName + " deposited $" + amount + " to " + getAccountType() + "-" + accountNum 
                + ". New Balance: $" + accountBal);
        }
    }
}
