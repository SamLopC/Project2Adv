package project2adv;

/**
 * Interface representing account information methods.
 */
public interface AccountInfo {
    /**
     * Displays account information.
     */
    void displayAccountInfo();

    /**
     * Returns the current balance of the account.
     *
     * @return the account balance
     */
    double showBalance();

    /**
     * Returns the account type (e.g., Checking or Saving).
     *
     * @return the account type
     */
    String getAccountType();
}
