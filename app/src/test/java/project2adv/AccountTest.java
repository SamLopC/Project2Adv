package project2adv;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

    private Checking checkingAccount;
    private Saving savingAccount;
    private Credit creditAccount;

    @BeforeEach
    public void setUp() {
        checkingAccount = new Checking("12345", 500.0);
        savingAccount = new Saving("54321", 1000.0);
        creditAccount = new Credit("67890", -50.0, 1000.0); // Initial balance is negative for credit, with a limit
    }

    // Test for successful withdrawal from Checking Account
    @Test
    public void testWithdrawPositiveAmount() {
        checkingAccount.withdraw(100, "Test Customer", true);
        assertEquals(400.0, checkingAccount.showBalance(), "Balance should decrease by withdrawn amount.");
    }

    // Test for withdrawal with insufficient funds
    @Test
    public void testWithdrawInsufficientFunds() {
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(600, "Test Customer", true),
            "Withdrawal with insufficient funds should throw IllegalArgumentException.");
    }

    // Test for withdrawal with invalid (negative) amount
    @Test
    public void testWithdrawNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(-50, "Test Customer", true),
            "Withdrawal of a negative amount should throw IllegalArgumentException.");
    }

    // Test for successful transfer between accounts
    @Test
    public void testTransferFundsBetweenAccounts() {
        Customer sender = new Customer(0, "Alice", "1990-01-01", "123 Main St", "CityName", "StateName", "12345", "1234567890", null, checkingAccount, savingAccount, checkingAccount);
        Customer recipient = new Customer(0, "Bob", "1985-05-05", "456 Side St", "CityName", "StateName", "67890", "0987654321", null, checkingAccount, savingAccount, checkingAccount);
        sender.paySomeone(recipient, 200);
        
    }

    // Test for transfer with insufficient funds
    @Test
    public void testTransferInsufficientFunds() {
        Customer sender = new Customer(0, "Alice", "1990-01-01", "123 Main St", "CityName", "StateName", "12345", "1234567890", null, checkingAccount, savingAccount, creditAccount);
        Customer recipient = new Customer(0, "Bob", "1985-05-05", "456 Side St", "CityName", "StateName", "67890", "0987654321", null, checkingAccount, savingAccount, creditAccount);
        assertThrows(IllegalArgumentException.class, () -> sender.paySomeone(recipient, 600),
            "Transfer with insufficient funds should throw IllegalArgumentException.");
    }

    // Test for credit account withdrawal within the credit limit
    @Test
    public void testCreditWithdrawWithinLimit() {
        creditAccount.withdraw(200, "Test Customer", true);
        assertEquals(150.0, creditAccount.showBalance(),
            "Balance should reflect the added amount after withdrawing within the credit limit.");
    }

    // Test for credit account withdrawal exceeding the credit limit
    @Test
    public void testCreditWithdrawExceedingLimit() {
        assertThrows(IllegalArgumentException.class, () -> creditAccount.withdraw(1200, "Test Customer", true),
            "Withdrawal exceeding credit limit should throw IllegalArgumentException.");
    }

    // Test for deposit into credit account reducing the outstanding balance
    @Test
    public void testCreditDepositReducesOutstandingBalance() {
        creditAccount.deposit(50, "Test Customer", true);
        assertEquals(0.0, creditAccount.showBalance(), "Balance should be zero after depositing to clear the debt.");
    }

    // Test for deposit that exceeds outstanding balance on credit account
    @Test
    public void testCreditDepositExceedsOutstandingBalance() {
        Credit creditAccount = new Credit("67890", -50.0, 1000.0);
        assertThrows(IllegalArgumentException.class, () -> creditAccount.deposit(100, "Test Customer", true),
            "Deposit that exceeds outstanding balance should throw IllegalArgumentException.");
    }
    
}
