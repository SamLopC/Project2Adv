import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Abstract class representing a general bank account.
 */
public abstract class Account {
    protected String accountNum;
    protected double accountBal;

    /**
     * Constructor to initialize an account with account number and balance.
     *
     * @param accountNum the account number
     * @param accountBal the initial balance
     */
    public Account(String accountNum, double accountBal) {
        this.accountNum = accountNum;
        this.accountBal = accountBal;
    }

    /**
     * Displays the account information. Must be implemented by subclasses.
     */
    public abstract void displayAccountInfo();

    /**
     * Logs a transaction to a file.
     *
     * @param logMessage the message to log
     */
    protected void logTransaction(String logMessage) {
        try (FileWriter writer = new FileWriter("Atransactions_log.txt", true)) {
            writer.write(LocalDateTime.now() + " - " + logMessage + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }

    /**
     * Deposits money into the account.
     *
     * @param amount the amount to deposit
     * @param customerName the name of the customer
     * @param logTransaction whether to log the transaction
     */
    
    public void deposit(double amount, String customerName, boolean logTransaction) {
        if (amount > 0) {
            accountBal += amount;
            System.out.println("Deposit of $" + amount + " successful.");
            if (logTransaction) {
                logTransaction(customerName + " deposited $" + amount + " to " + getAccountType() + "-" + accountNum 
                + ". " + customerName + "’s New Balance for " + getAccountType() + "-" + accountNum + ": $" + accountBal);
            }
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }
    

    /**
     * Withdraws money from the account.
     *
     * @param amount the amount to withdraw
     * @param customerName the name of the customer
     * @param logTransaction whether to log the transaction
     */
    
    public void withdraw(double amount, String customerName, boolean logTransaction) {
        if (amount > 0 && accountBal >= amount) {
            accountBal -= amount;
            System.out.println("Withdrawal of $" + amount + " successful.");
            if (logTransaction) {
                logTransaction(customerName + " withdrew $" + amount + " from " + getAccountType() + "-" + accountNum 
                + ". " + customerName + "’s Balance for " + getAccountType() + "-" + accountNum + ": $" + accountBal);
            }
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }
    

    /**
     * Returns the current balance of the account.
     *
     * @return the account balance
     */
    public double showBalance() {
        return accountBal;
    }

    /**
     * Logs a balance inquiry made by the customer.
     *
     * @param customerName the name of the customer
     */
   public void logBalanceInquiry(String customerName) {
        logTransaction(customerName + " made a balance inquiry on " + getAccountType() + "-" + accountNum 
        + ". " + customerName + "’s Balance for " + getAccountType() + "-" + accountNum + ": $" + accountBal);
    }

    /**
     * Returns the type of account (e.g., Checking, Saving).
     *
     * @return the account type
     */
    public abstract String getAccountType();

    /**
     * Returns the account number.
     *
     * @return the account number
     */
    public String getAccountNum() {
        return accountNum;
    }
}
