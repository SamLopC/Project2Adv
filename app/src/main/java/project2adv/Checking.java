package project2adv;

/**
 * The {@code Checking} class represents a checking account. It extends the {@link Account} class
 * and implements the {@link AccountInfo} interface to provide specific behavior and details
 * for checking accounts, such as displaying account information and returning the account type.
 *
 * <p>This class includes methods to display account details and retrieve the type of account as
 * "Checking". It leverages the functionality defined in the {@link Account} superclass.
 *
 * @see Account
 * @see AccountInfo
 */
@SuppressWarnings("unused")
public class Checking extends Account implements AccountInfo {

    /**
     * Constructs a new {@code Checking} account with the specified account number and balance.
     *
     * @param accountNum the account number to assign to this checking account
     * @param accountBal the initial balance of the checking account
     */
    public Checking(String accountNum, double accountBal) {
        super(accountNum, accountBal);
    }

    /**
     * Displays information about the checking account, including the account number and current balance.
     */
    @Override
    public void displayAccountInfo() {
        System.out.println("Checking Account Number: " + accountNum);
        System.out.println("Balance: $" + accountBal);
    }

    /**
     * Retrieves the type of the account, which is "Checking" for instances of this class.
     *
     * @return a {@code String} representing the account type, which is "Checking"
     */
    @Override
    public String getAccountType() {
        return "Checking";
    }
}
