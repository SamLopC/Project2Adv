/**
 * Class representing a checking account.
 */
public class Checking extends Account {

    /**
     * Constructor to initialize a checking account with account number and balance.
     *
     * @param accountNum the account number
     * @param accountBal the initial balance
     */
    public Checking(String accountNum, double accountBal) {
        super(accountNum, accountBal);
    }

    /**
     * Displays the account information for a checking account.
     */
    @Override
    public void displayAccountInfo() {
        System.out.println("Checking Account Number: " + accountNum);
        System.out.println("Balance: $" + accountBal);
    }

    /**
     * Returns the type of account (in this case, "Checking").
     *
     * @return "Checking"
     */
    @Override
    public String getAccountType() {
        return "Checking";
    }
}
