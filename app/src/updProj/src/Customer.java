import java.util.Scanner;
/**
 * Class representing a customer with an associated account.
 */
public class Customer extends Person {
    //private Account account;
    private Checking checkingAccount;
    private Saving savingAccount;
    private Credit creditAccount;
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Constructor to initialize a customer with name and account.
     *
     * @param name the name of the customer
     * @param account the associated account
     */
    public Customer(String name, Checking checkingAccount, Saving savingAccount, Credit creditAccount) {
        super(name);
        //this.account = account;
        this.checkingAccount = checkingAccount;
        this.savingAccount = savingAccount;
        this.creditAccount = creditAccount;
    }
    
    
    public Account getAccountType(String accType) {
    	switch (accType.toLowerCase()) {
        case "checking":
            return this.getCheckingAccount();
        case "savings":
            return this.getSavingAccount();
        case "credit":
            return this.getCreditAccount();
        default:
            return null;
    }
    	
    }

    /**
     * Displays the customer's name.
     */
    @Override
    public void displayPersonInfo() {
        System.out.println("Customer Name: " + name);
    }

    /**
     * Displays both customer and account information.
     */
    public void displayAccountInfo() {
        displayPersonInfo();
        
        if (checkingAccount != null) {
            checkingAccount.displayAccountInfo();
        }
        if (savingAccount != null) {
            savingAccount.displayAccountInfo();
        }
        if (creditAccount != null) {
            creditAccount.displayAccountInfo();
        }
    }
    
 // General deposit method
    public void depositMoney(double amount) {
        //Scanner scanner = new Scanner(System.in);

        System.out.println("Where would you like to deposit the money?");
        System.out.println("1. Checking Account");
        System.out.println("2. Saving Account");
        System.out.println("3. Credit Account");
        System.out.print("Enter the number of the account: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                depositMoneyToChecking(amount);
                break;
            case 2:
                depositMoneyToSaving(amount);
                break;
            case 3:
                depositMoneyToCredit(amount);
                break;
            default:
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
        }
    }

    /*
    /**
      Deposits money into the customer's account.
     
      @param amount the amount to deposit
     
    public void depositMoney(double amount) {
        account.deposit(amount, name, true);
    }
    */
    
    /**
     * Deposits money into the customer's checking account.
     *
     * @param amount the amount to deposit
     */
    public void depositMoneyToChecking(double amount) {
        checkingAccount.deposit(amount, name, true);
    }

    /**
     * Deposits money into the customer's saving account.
     *
     * @param amount the amount to deposit
     */
    public void depositMoneyToSaving(double amount) {
        savingAccount.deposit(amount, name, true);
    }

    /**
     * Deposits money into the customer's credit account.
     *
     * @param amount the amount to deposit
     */
    public void depositMoneyToCredit(double amount) {
        creditAccount.deposit(amount, name, true);
    }
    
 // General withdraw method
    public void withdrawMoney(double amount) {
        //Scanner scanner = new Scanner(System.in);

        System.out.println("Where would you like to withdraw the money from?");
        System.out.println("1. Checking Account");
        System.out.println("2. Saving Account");
        System.out.println("3. Credit Account");
        System.out.print("Enter the number of the account: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                withdrawMoneyFromChecking(amount);
                break;
            case 2:
                withdrawMoneyFromSaving(amount);
                break;
            case 3:
                withdrawMoneyFromCredit(amount);
                break;
            default:
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
        }
    }
    
    /*
    /**
      Withdraws money from the customer's account.
     
      @param amount the amount to withdraw
     
    public void withdrawMoney(double amount) {
        account.withdraw(amount, name, true);
    }
    */
    
    /**
     * Withdraws money from the customer's checking account.
     *
     * @param amount the amount to withdraw
     */
    public void withdrawMoneyFromChecking(double amount) {
        checkingAccount.withdraw(amount, name, true);
    }

    /**
     * Withdraws money from the customer's saving account.
     *
     * @param amount the amount to withdraw
     */
    public void withdrawMoneyFromSaving(double amount) {
        savingAccount.withdraw(amount, name, true);
    }

    /**
     * Withdraws money from the customer's credit account.
     *
     * @param amount the amount to withdraw
     */
    public void withdrawMoneyFromCredit(double amount) {
        creditAccount.withdraw(amount, name, true);
    }

    /*
    /**
      Transfers money to another customer.
     
      @param recipient the customer receiving the money
      @param amount the amount to transfer
     
    public void transferMoney(Customer recipient, double amount) {
        if (account.showBalance() >= amount) {
            this.account.withdraw(amount, name, false);
            recipient.account.deposit(amount, recipient.name, false);
            System.out.println("Transfer of $" + amount + " to " + recipient.name + " successful.");
            account.logTransaction(name + " transferred $" + amount + " to " + recipient.name + ". "
                + name + "’s New Balance for " + account.getAccountType() + "-" + account.getAccountNum() + ": $" + account.showBalance());
            recipient.account.logTransaction(recipient.name + " received $" + amount + " from " + name + ". "
                + recipient.name + "’s New Balance for " + recipient.account.getAccountType() + "-" + recipient.account.getAccountNum() + ": $" + recipient.account.showBalance());
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }
    */
    
 // General transfer method
    public void transferMoney(Customer recipient, double amount) {
        //Scanner scanner = new Scanner(System.in);

        System.out.println("Which account would you like to transfer money from?");
        System.out.println("1. Checking Account");
        System.out.println("2. Saving Account");
        System.out.println("3. Credit Account");
        System.out.print("Enter the number of the account: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                transferMoneyFromChecking(recipient, amount);
                break;
            case 2:
                transferMoneyFromSaving(recipient, amount);
                break;
            case 3:
                transferMoneyFromCredit(recipient, amount);
                break;
            default:
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
        }
    }
    
    /**
     * Transfers money to another customer from the checking account.
     *
     * @param recipient the customer receiving the money
     * @param amount the amount to transfer
     */
    public void transferMoneyFromChecking(Customer recipient, double amount) {
        if (checkingAccount.showBalance() >= amount) {
            this.checkingAccount.withdraw(amount, name, false);
            recipient.checkingAccount.deposit(amount, recipient.name, false);
            System.out.println("Transfer of $" + amount + " from checking to " + recipient.name + " successful.");
            checkingAccount.logTransaction(name + " transferred $" + amount + " to " + recipient.name + ". "
                + name + "’s New Balance for " + checkingAccount.getAccountType() + "-" + checkingAccount.getAccountNum() + ": $" + checkingAccount.showBalance());
            recipient.checkingAccount.logTransaction(recipient.name + " received $" + amount + " from " + name + ". "
                + recipient.name + "’s New Balance for " + recipient.checkingAccount.getAccountType() + "-" + recipient.checkingAccount.getAccountNum() + ": $" + recipient.checkingAccount.showBalance());
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }
    
    /**
     * Transfers money to another customer from the saving account.
     *
     * @param recipient the customer receiving the money
     * @param amount the amount to transfer
     */
    public void transferMoneyFromSaving(Customer recipient, double amount) {
        if (savingAccount.showBalance() >= amount) {
            this.savingAccount.withdraw(amount, name, false);
            recipient.savingAccount.deposit(amount, recipient.name, false);
            System.out.println("Transfer of $" + amount + " from saving to " + recipient.name + " successful.");
            savingAccount.logTransaction(name + " transferred $" + amount + " to " + recipient.name + ". "
                + name + "’s New Balance for " + savingAccount.getAccountType() + "-" + savingAccount.getAccountNum() + ": $" + savingAccount.showBalance());
            recipient.savingAccount.logTransaction(recipient.name + " received $" + amount + " from " + name + ". "
                + recipient.name + "’s New Balance for " + recipient.savingAccount.getAccountType() + "-" + recipient.savingAccount.getAccountNum() + ": $" + recipient.savingAccount.showBalance());
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }

    /**
     * Transfers money to another customer from the credit account.
     *
     * @param recipient the customer receiving the money
     * @param amount the amount to transfer
     */
    public void transferMoneyFromCredit(Customer recipient, double amount) {
        if (creditAccount.showBalance() >= amount) {
            this.creditAccount.withdraw(amount, name, false);
            recipient.creditAccount.deposit(amount, recipient.name, false);
            System.out.println("Transfer of $" + amount + " from credit to " + recipient.name + " successful.");
            creditAccount.logTransaction(name + " transferred $" + amount + " to " + recipient.name + ". "
                + name + "’s New Balance for " + creditAccount.getAccountType() + "-" + creditAccount.getAccountNum() + ": $" + creditAccount.showBalance());
            recipient.creditAccount.logTransaction(recipient.name + " received $" + amount + " from " + name + ". "
                + recipient.name + "’s New Balance for " + recipient.creditAccount.getAccountType() + "-" + recipient.creditAccount.getAccountNum() + ": $" + recipient.creditAccount.showBalance());
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }

    /*
    /**
     * Pays someone by transferring money to another customer.
     *
      @param recipient the customer receiving the payment
      @param amount the amount to pay
     
    public void paySomeone(Customer recipient, double amount) {
        this.transferMoney(recipient, amount);
    }
    */
    
    /**
     * Pays someone by transferring money from the checking account.
     *
     * @param recipient the customer receiving the payment
     * @param amount the amount to pay
     */
    public void paySomeoneFromChecking(Customer recipient, double amount) {
        this.transferMoneyFromChecking(recipient, amount);
    }

    /**
     * Pays someone by transferring money from the saving account.
     *
     * @param recipient the customer receiving the payment
     * @param amount the amount to pay
     */
    public void paySomeoneFromSaving(Customer recipient, double amount) {
        this.transferMoneyFromSaving(recipient, amount);
    }

    /**
     * Pays someone by transferring money from the credit account.
     *
     * @param recipient the customer receiving the payment
     * @param amount the amount to pay
     */
    public void paySomeoneFromCredit(Customer recipient, double amount) {
        this.transferMoneyFromCredit(recipient, amount);
    }
    
    /**
     * Gets the customer's checking account.
     *
     * @return the customer's checking account
     */
    public Checking getCheckingAccount() {
        return checkingAccount;
    }

    /**
     * Gets the customer's saving account.
     *
     * @return the customer's saving account
     */
    public Saving getSavingAccount() {
        return savingAccount;
    }

    /**
     * Gets the customer's credit account.
     *
     * @return the customer's credit account
     */
    public Credit getCreditAccount() {
        return creditAccount;
    }
    
    
    public void paySomeone(Customer recipient, double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("From which account would you like to pay?");
        System.out.println("1. Checking Account");
        System.out.println("2. Saving Account");
        System.out.println("3. Credit Account");
        System.out.print("Enter the number of the account: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                if (this.checkingAccount.showBalance() >= amount) {
                    this.withdrawMoneyFromChecking(amount);  // Withdraw from sender's checking account
                    recipient.depositMoneyToChecking(amount);  // Deposit to recipient's checking account
                    System.out.println("Payment of " + amount + " made to " + recipient.getName() + " from Checking Account.");
                } else {
                    System.out.println("Insufficient funds in Checking Account.");
                }
                break;
            case 2:
                if (this.savingAccount.showBalance() >= amount) {
                    this.withdrawMoneyFromSaving(amount);  // Withdraw from sender's saving account
                    recipient.depositMoneyToSaving(amount);  // Deposit to recipient's saving account
                    System.out.println("Payment of " + amount + " made to " + recipient.getName() + " from Saving Account.");
                } else {
                    System.out.println("Insufficient funds in Saving Account.");
                }
                break;
            case 3:
                if (this.creditAccount.showBalance() >= amount) {
                    this.withdrawMoneyFromCredit(amount);  // Withdraw from sender's credit account
                    recipient.depositMoneyToCredit(amount);  // Deposit to recipient's credit account
                    System.out.println("Payment of " + amount + " made to " + recipient.getName() + " from Credit Account.");
                } else {
                    System.out.println("Insufficient funds in Credit Account.");
                }
                break;
            default:
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
        }
    }
    

    /**
     * Gets the total balance of all the customer's accounts.
     *
     * @return the total balance
     */
    public double getTotalBalance() {
        double total = 0;
        if (checkingAccount != null) {
            total += checkingAccount.showBalance();
        }
        if (savingAccount != null) {
            total += savingAccount.showBalance();
        }
        if (creditAccount != null) {
            total += creditAccount.showBalance();
        }
        return total;
    }
    
    public double showBalance(String accountType) {
        switch (accountType.toLowerCase()) {
            case "checking":
                return this.checkingAccount.showBalance();
            case "saving":
                return this.savingAccount.showBalance();
            case "credit":
                return this.creditAccount.showBalance();
            default:
                throw new IllegalArgumentException("Invalid account type.");
        }
    }

}
