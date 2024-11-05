package project2adv;

@SuppressWarnings("unused")


public class Checking extends Account implements AccountInfo {

    public Checking(String accountNum, double accountBal) {
        super(accountNum, accountBal);
    }

    @Override
    public void displayAccountInfo() {
        System.out.println("Checking Account Number: " + accountNum);
        System.out.println("Balance: $" + accountBal);
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }
}
