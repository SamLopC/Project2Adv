package project2adv;


public interface AccountFactory {
    Account createAccount(String accountNum, double balance);
}

class CheckingFactory implements AccountFactory {
    @Override
    public Account createAccount(String accountNum, double balance) {
        return new Checking(accountNum, balance);
    }
}

class SavingFactory implements AccountFactory {
    @Override
    public Account createAccount(String accountNum, double balance) {
        return new Saving(accountNum, balance);
    }
}

class CreditFactory implements AccountFactory {
    @Override
    public Account createAccount(String accountNum, double balance) {
        return new Credit(accountNum, balance, calculateCreditLimit());
    }

    private double calculateCreditLimit() {
        // Implement logic for random credit limit based on credit score range
        return 1000.00; // Placeholder
    }
}
