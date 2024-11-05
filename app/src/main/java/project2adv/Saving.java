package project2adv;

/**
 * Class representing a savings account.
 */
public class Saving extends Account {

    /**
     * Constructor to initialize a savings account with account number and balance.
     *
     * @param accountNum the account number
     * @param accountBal the initial balance
     */
    public Saving(String accountNum, double accountBal) {
        super(accountNum, accountBal);
    }

    /**
     * Displays the account information for a savings account.
     */
    @Override
    public void displayAccountInfo() {
        System.out.println("Saving Account Number: " + accountNum);
        System.out.println("Balance: $" + accountBal);
    }

    /**
     * Returns the type of account (in this case, "Saving").
     *
     * @return "Saving"
     */
    @Override
    public String getAccountType() {
        return "Saving";
    }
}
