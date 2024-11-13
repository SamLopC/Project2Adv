package project2adv;

/**
 * The {@code AccountFactory} interface provides a method for creating {@link Account} objects.
 * It defines a factory pattern that allows for different types of accounts to be created without 
 * needing to know the specific class of the account. Implementations of this interface can 
 * produce instances of specific account types.
 *
 * @see CheckingFactory
 * @see SavingFactory
 * @see CreditFactory
 */
public interface AccountFactory {
    /**
     * Creates an {@link Account} instance with the specified account number and balance.
     *
     * @param accountNum the account number to assign to the new account
     * @param balance    the initial balance of the account
     * @return an {@link Account} instance of a specific type
     */
    Account createAccount(String accountNum, double balance);
}

/**
 * The {@code CheckingFactory} class is a concrete implementation of {@link AccountFactory} 
 * that creates instances of {@link Checking} accounts. It overrides the {@code createAccount} 
 * method to return a {@link Checking} account with a specified account number and balance.
 *
 * @see AccountFactory
 * @see Checking
 */
class CheckingFactory implements AccountFactory {
    /**
     * Creates a {@link Checking} account with the specified account number and balance.
     *
     * @param accountNum the account number to assign to the new account
     * @param balance    the initial balance of the account
     * @return a new {@link Checking} account instance
     */
    @Override
    public Account createAccount(String accountNum, double balance) {
        return new Checking(accountNum, balance);
    }
}

/**
 * The {@code SavingFactory} class is a concrete implementation of {@link AccountFactory} 
 * that creates instances of {@link Saving} accounts. It overrides the {@code createAccount} 
 * method to return a {@link Saving} account with a specified account number and balance.
 *
 * @see AccountFactory
 * @see Saving
 */
class SavingFactory implements AccountFactory {
    /**
     * Creates a {@link Saving} account with the specified account number and balance.
     *
     * @param accountNum the account number to assign to the new account
     * @param balance    the initial balance of the account
     * @return a new {@link Saving} account instance
     */
    @Override
    public Account createAccount(String accountNum, double balance) {
        return new Saving(accountNum, balance);
    }
}

/**
 * The {@code CreditFactory} class is a concrete implementation of {@link AccountFactory} 
 * that creates instances of {@link Credit} accounts. It includes a method to calculate a 
 * credit limit, which is used when creating a new {@link Credit} account.
 *
 * @see AccountFactory
 * @see Credit
 */
class CreditFactory implements AccountFactory {
    /**
     * Creates a {@link Credit} account with the specified account number, balance, and a calculated credit limit.
     *
     * @param accountNum the account number to assign to the new account
     * @param balance    the initial balance of the account
     * @return a new {@link Credit} account instance
     */
    @Override
    public Account createAccount(String accountNum, double balance) {
        return new Credit(accountNum, balance, calculateCreditLimit());
    }

    /**
     * Calculates the credit limit for a {@link Credit} account. This method contains the logic 
     * for determining the credit limit based on criteria such as credit score range.
     *
     * @return the calculated credit limit, with a default value of $1000.00 as a placeholder
     */
    private double calculateCreditLimit() {
        // Implement logic for random credit limit based on credit score range
        return 1000.00; // Placeholder
    }
}
