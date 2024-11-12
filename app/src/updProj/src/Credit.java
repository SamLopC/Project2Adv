/**
 * Class representing a credit account.
 */
public class Credit extends Account implements AccountBehavior{
    private double credLimit;

    /**
     * Constructor to initialize a credit account with account number, balance, and credit limit.
     *
     * @param accountNum the account number
     * @param accountBal the initial balance
     * @param credLimit the credit limit
     */
    public Credit(String accountNum, double accountBal, double credLimit) {
        super(accountNum, accountBal);
        this.credLimit = credLimit;
    }

    /**
     * Displays the account information for a credit account.
     */
    @Override
    public void displayAccountInfo() {
        System.out.println("Credit Account Number: " + accountNum);
        System.out.println("Balance: $" + accountBal);
        System.out.println("Credit Limit: $" + credLimit);
    }

    /**
     * Withdraws money from the credit account.
     * 
     * @param amount the amount to withdraw
     * @param customerName the name of the customer
     * @param logTransaction whether to log the transaction
     */
    @Override
    public void withdraw(double amount, String customerName, boolean logTransaction) {
        if (amount > 0 && (accountBal + amount) <= credLimit) {
            accountBal += amount;
            System.out.println("Charged $" + amount + " to your credit account.");
            if (logTransaction) {
                logTransaction(customerName + " charged $" + amount + " to Credit-" + accountNum + ". "
                    + customerName + "’s New Balance for Credit-" + accountNum + ": $" + accountBal);
            }
        } else {
            System.out.println("Credit limit exceeded.");
        }
    }

    /**
     * Returns the type of account (in this case, "Credit").
     *
     * @return "Credit"
     */
    @Override
    public String getAccountType() {
        return "Credit";
    }
}

