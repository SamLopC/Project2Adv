package project2adv;

/**
 * Interface representing transaction handling methods.
 */
public interface TransactionHandler {
    /**
     * Deposits a specified amount into the account.
     *
     * @param amount the amount to deposit
     * @param customerName the name of the customer
     * @param logTransaction whether to log the transaction
     */
    void deposit(double amount, String customerName, boolean logTransaction);

    /**
     * Withdraws a specified amount from the account.
     *
     * @param amount the amount to withdraw
     * @param customerName the name of the customer
     * @param logTransaction whether to log the transaction
     */
    void withdraw(double amount, String customerName, boolean logTransaction);

    /**
     * Transfers a specified amount to another customer.
     *
     * @param recipient the customer receiving the funds
     * @param amount the amount to transfer
     */
    void transfer(Customer recipient, double amount);
}
