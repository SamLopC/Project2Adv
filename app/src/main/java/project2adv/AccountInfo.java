package project2adv;

/**
 * The {@code AccountInfo} interface defines methods for retrieving basic account information.
 * This interface can be implemented by various account types to provide a standardized way 
 * of displaying account details, checking the current balance, and identifying the account type.
 */
public interface AccountInfo {
    /**
     * Displays information about the account. This may include details such as the account number,
     * current balance, and other relevant account-specific information.
     */
    void displayAccountInfo();

    /**
     * Retrieves the current balance of the account.
     *
     * @return the current balance of the account
     */
    double showBalance();

    /**
     * Retrieves the type of the account. Implementations should specify the type, such as "Checking" 
     * or "Saving", as appropriate.
     *
     * @return a {@code String} representing the account type
     */
    String getAccountType();
}
