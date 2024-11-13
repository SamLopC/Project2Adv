package project2adv;

/**
 * The {@code Customer} class represents a customer in the banking system. Each customer is
 * associated with personal information and various account types, including checking, saving,
 * and credit accounts. The class provides methods for making payments to other customers, 
 * depositing, and withdrawing money.
 *
 * <p>This class extends {@link Person} and adds customer-specific attributes such as user ID, 
 * address, and contact information. It also includes methods for displaying both personal 
 * and account information.
 *
 * @see Person
 * @see Account
 */
public class Customer extends Person {
    /**
     * The unique ID associated with the customer.
     */
    private int userID;

    /**
     * The first name of the customer.
     */
    private String firstName;

    /**
     * The last name of the customer.
     */
    private String lastName;

    /**
     * The checking account associated with the customer.
     */
    private Account checkingAccount;

    /**
     * The saving account associated with the customer.
     */
    private Account savingAccount;

    /**
     * The credit account associated with the customer.
     */
    private Account creditAccount;

    /**
     * The date of birth of the customer.
     */
    private String dob;

    /**
     * The address of the customer.
     */
    private String address;

    /**
     * The city where the customer resides.
     */
    private String city;

    /**
     * The state where the customer resides.
     */
    private String state;

    /**
     * The zip code of the customer's residence.
     */
    private String zip;

    /**
     * The phone number of the customer.
     */
    private String phoneNumber;

    /**
     * Constructs a new {@code Customer} instance with the specified personal and account information.
     *
     * @param userID          the unique ID for the customer
     * @param firstName       the first name of the customer
     * @param lastName        the last name of the customer
     * @param dob             the date of birth of the customer
     * @param address         the customer's address
     * @param city            the city where the customer resides
     * @param state           the state where the customer resides
     * @param zip             the zip code of the customer's residence
     * @param phoneNumber     the phone number of the customer
     * @param checkingAccount the checking account associated with the customer
     * @param savingAccount   the saving account associated with the customer
     * @param creditAccount   the credit account associated with the customer
     */
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

    /**
     * Allows the customer to pay another customer a specified amount from their checking account.
     *
     * @param recipient the recipient of the payment
     * @param amount    the amount to pay, must be less than or equal to the balance of the checking account
     * @throws IllegalArgumentException if the customer tries to pay themselves or if there are insufficient funds
     */
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

    /**
     * Displays personal information of the customer, including name, user ID, date of birth, 
     * address, and phone number.
     */
    @Override
    public void displayPersonInfo() {
        System.out.println("Customer Name: " + firstName + " " + lastName + ", User ID: " + userID);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Address: " + address + ", " + city + ", " + state + " " + zip);
        System.out.println("Phone Number: " + phoneNumber);
    }

    /**
     * Displays the customer's personal and account information for checking, saving, and credit accounts.
     */
    public void displayAccountInfo() {
        displayPersonInfo();
        checkingAccount.displayAccountInfo();
        savingAccount.displayAccountInfo();
        creditAccount.displayAccountInfo();
    }

    /**
     * Deposits a specified amount into the customer's checking account.
     *
     * @param amount the amount to deposit, must be positive
     */
    public void depositMoney(double amount) {
        checkingAccount.deposit(amount, getName(), true);
    }
    
    /**
     * Withdraws a specified amount from the customer's checking account.
     *
     * @param amount the amount to withdraw, must be positive and within the balance of the checking account
     */
    public void withdrawMoney(double amount) {
        checkingAccount.withdraw(amount, getName(), true);
    }

    // Getter methods

    /**
     * Retrieves the user ID of the customer.
     *
     * @return the user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Retrieves the first name of the customer.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the last name of the customer.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the checking account of the customer.
     *
     * @return the checking account
     */
    public Account getCheckingAccount() {
        return checkingAccount;
    }

    /**
     * Retrieves the saving account of the customer.
     *
     * @return the saving account
     */
    public Account getSavingAccount() {
        return savingAccount;
    }

    /**
     * Retrieves the credit account of the customer.
     *
     * @return the credit account
     */
    public Account getCreditAccount() {
        return creditAccount;
    }

    /**
     * Retrieves the date of birth of the customer.
     *
     * @return the date of birth
     */
    public String getDob() {
        return dob;
    }
    
    /**
     * Retrieves the address of the customer.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * Retrieves the city where the customer resides.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }
    
    /**
     * Retrieves the state where the customer resides.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }
    
    /**
     * Retrieves the zip code of the customer's residence.
     *
     * @return the zip code
     */
    public String getZip() {
        return zip;
    }
    
    /**
     * Retrieves the phone number of the customer.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Retrieves the credit limit of the customer's credit account if it exists; otherwise returns "N/A".
     *
     * @return the credit limit as a string or "N/A" if no credit account is present
     */
    public String getCreditLimit() {
        return creditAccount instanceof Credit ? ((Credit) creditAccount).getCreditLimit() : "N/A";
    }
}
