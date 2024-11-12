package project2adv;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer extends Person {
    private int userID;
    private String firstName;
    private String lastName;
    private Account checkingAccount;
    private Account savingAccount;
    private Account creditAccount;
    private String dob;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    public Customer(int userID, String firstName, String lastName, String dob, String address, String city, String state, String zip, 
                    String phoneNumber, Account checkingAccount, Account savingAccount, Account creditAccount) {
        super(firstName + " " + lastName);
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.checkingAccount = checkingAccount;
        this.savingAccount = savingAccount;
        this.creditAccount = creditAccount;
    }

    public void paySomeone(Customer recipient, double amount) {
        if (this == recipient) {
            throw new IllegalArgumentException("Cannot pay yourself.");
        }
        if (checkingAccount.showBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds for payment.");
        }

        // Withdraw amount from payer's checking account
        this.checkingAccount.withdraw(amount, this.getName(), true);

        // Deposit amount to recipient's checking account
        recipient.getCheckingAccount().deposit(amount, recipient.getName(), true);

        System.out.println(this.getName() + " paid $" + amount + " to " + recipient.getName() + ".");
    }

    @Override
    public void displayPersonInfo() {
        System.out.println("Customer Name: " + firstName + " " + lastName + ", User ID: " + userID);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Address: " + address + ", " + city + ", " + state + " " + zip);
        System.out.println("Phone Number: " + phoneNumber);
    }

    public void displayAccountInfo() {
        displayPersonInfo();
        checkingAccount.displayAccountInfo();
        savingAccount.displayAccountInfo();
        creditAccount.displayAccountInfo();
    }

    public void depositMoney(double amount) {
        checkingAccount.deposit(amount, name, true);
    }

    public void withdrawMoney(double amount) {
        checkingAccount.withdraw(amount, name, true);
    }

    public void transferMoney(Customer recipient, double amount) {
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient cannot be null.");
        }
        // Use the checking account by default for the transfer
        this.checkingAccount.transfer(recipient, amount);
    }

    public int getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Account getCheckingAccount() {
        return checkingAccount;
    }

    public Account getSavingAccount() {
        return savingAccount;
    }

    public Account getCreditAccount() {
        return creditAccount;
    }
    public String getDob() {
        return dob;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getState() {
        return state;
    }
    
    public String getZip() {
        return zip;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getCreditLimit() {
        return creditAccount instanceof Credit ? ((Credit) creditAccount).getCreditLimit() : "N/A";
    }
    
    
}
