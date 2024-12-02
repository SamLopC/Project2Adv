package project2adv;

/**
 * The {@code Saving} class represents a savings account. It extends the {@link Account} class 
 * to provide specific details and behaviors for a savings account.
 *
 * @see Account
 */
public class Saving extends Account {

    /**
     * Constructs a new {@code Saving} account with the specified account number and initial balance.
     *
     * @param accountNum the account number to assign to this savings account
     * @param accountBal the initial balance of the savings account
     */
    public Saving(String accountNum, double accountBal) {
        super(accountNum, accountBal);
    }

    /**
     * Displays information about the savings account, including the account number and current balance.
     */
    @Override
    public void displayAccountInfo() {
        System.out.println("Saving Account Number: " + accountNum);
        System.out.println("Balance: $" + accountBal);
    }

    /**
     * Retrieves the type of the account, which is "Saving" for instances of this class.
     *
     * @return a {@code String} representing the account type, which is "Saving"
     */
    @Override
    public String getAccountType() {
        return "Saving";
    }
}
