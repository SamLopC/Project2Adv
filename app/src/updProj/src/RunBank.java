import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;


/**
 * The RunBank class is the main class that runs the banking system for El Paso Miners Bank.
 * It allows users to perform actions like balance inquiry, deposit, withdrawal, and transfer.
 */
public class RunBank {
    
	private static final int MAX_CUSTOMERS = 150;
	private static Customer[] customers = new Customer[MAX_CUSTOMERS];
	private static int customerCount = 0;
	
	private static void loadCustomersFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip header line
            br.readLine();

            while ((line = br.readLine()) != null && customerCount < MAX_CUSTOMERS) {
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                
                data[4] = data[4].replaceAll("\""," ").trim();
                String name = data[1] + " " + data[2];
                String checkingAccountNum = data[6];
                double checkingStartingBalance = Double.parseDouble(data[7]);
                String savingAccountNum = data[8];
                double savingStartingBalance = Double.parseDouble(data[9]);
                String creditAccountNum = data[10];
                double creditMax = Double.parseDouble(data[11]);
                double creditStartingBalance = Double.parseDouble(data[12]);

                // Create accounts based on CSV data
                Checking checkingAccount = new Checking(checkingAccountNum, checkingStartingBalance);
                Saving savingAccount = new Saving(savingAccountNum, savingStartingBalance);
                Credit creditAccount = new Credit(creditAccountNum, creditStartingBalance, creditMax);

                // Create a customer and store in the array
                customers[customerCount] = new Customer(name, checkingAccount, savingAccount, creditAccount);
                customerCount++;
            }
            System.out.println("Loaded " + customerCount + " customers from CSV.");
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
    }
	
	private static void logTransaction(String message) {
	    String logFilename = "transaction_log.txt";  // Log file name
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	    String timestamp = dtf.format(LocalDateTime.now());

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilename, true))) {
	        writer.write("[" + timestamp + "] " + message);
	        writer.newLine();
	    } catch (IOException e) {
	        System.err.println("Error writing to log file: " + e.getMessage());
	    }
	}
	
	// Method to generate next available account number
    private static String generateNextAccountNumber(String lastAccountNumber) {
        int accountNumber = Integer.parseInt(lastAccountNumber);
        accountNumber++;  // Increment by 1
        return String.valueOf(accountNumber);
    }
	
    private static void createNewUser(Scanner scanner) {
        System.out.println("Please enter the following information to create a new user:");

        // Collect user details
        System.out.print("Name (First Last): ");
        String name = scanner.nextLine().trim();

        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine().trim();

        System.out.print("Address: ");
        String address = scanner.nextLine().trim();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();

        // Generate the new user ID
        int newId = generateNewId("CS 3331 - Bank Users.csv");

        // Generate new account numbers based on last user in CSV
        String lastCheckingAccountNum = customers[customerCount - 1].getCheckingAccount().getAccountNum();
        String lastSavingAccountNum = customers[customerCount - 1].getSavingAccount().getAccountNum();
        String lastCreditAccountNum = customers[customerCount - 1].getCreditAccount().getAccountNum();

        // Increment account numbers
        String newCheckingAccountNum = generateNextAccountNumber(lastCheckingAccountNum);
        String newSavingAccountNum = generateNextAccountNumber(lastSavingAccountNum);
        String newCreditAccountNum = generateNextAccountNumber(lastCreditAccountNum);

        // Ask user for starting balances (or set default)
        System.out.print("Starting Checking Balance: ");
        double checkingStartingBalance = scanner.nextDouble();

        System.out.print("Starting Savings Balance: ");
        double savingsStartingBalance = scanner.nextDouble();

        System.out.print("Starting Credit Balance: ");
        double creditStartingBalance = scanner.nextDouble();
        
        System.out.print("Credit Max Limit: ");
        double creditMax = scanner.nextDouble();
        
        scanner.nextLine(); // Clear buffer

        // Create accounts
        Checking newCheckingAccount = new Checking(newCheckingAccountNum, checkingStartingBalance);
        Saving newSavingAccount = new Saving(newSavingAccountNum, savingsStartingBalance);
        Credit newCreditAccount = new Credit(newCreditAccountNum, creditStartingBalance, creditMax);

        // Create new customer
        Customer newCustomer = new Customer(name, newCheckingAccount, newSavingAccount, newCreditAccount);

        // Add to customers array
        if (customerCount < MAX_CUSTOMERS) {
            customers[customerCount] = newCustomer;
            customerCount++;
        }

        // Log the new user to a new CSV (Updated Bank Users file)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Updated_Bank_Users.csv", true))) {
            // Check if it's the first line (header)
            if (customerCount == 1) {
                writer.write("Identification Number,First Name,Last Name,Date of Birth,Address,Phone Number,Checking Account Number,Checking Starting Balance,Savings Account Number,Savings Starting Balance,Credit Account Number,Credit Max,Credit Starting Balance");
                writer.newLine();  // Write the header if it's the first user
            }
            // Write new customer data
            writer.write(newId + "," + newCustomer.getName().split(" ")[0] + "," + newCustomer.getName().split(" ")[1] + "," + dob + "," + address + "," + phoneNumber + "," +
                newCheckingAccountNum + "," + checkingStartingBalance + "," + newSavingAccountNum + "," + savingsStartingBalance + "," + newCreditAccountNum + "," + creditMax + "," + creditStartingBalance);
            writer.newLine();
            
            System.out.println("New user created and saved to Updated_Bank_Users.csv.");
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
    
 // Method to generate a new ID by checking the existing CSV file
    private static int generateNewId(String csvFile) {
    	int maxId = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            // Skip the header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                // Ensure that the ID is parsed correctly
                int id = Integer.parseInt(values[0].trim()); // Assuming the ID is in the first column
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException e) {
            // Handle case where the file doesn't exist or is empty
            System.out.println("File not found or empty, starting with ID 1.");
        }

        // Increment ID by 1
        return maxId + 1;
    }
	
	/**
     * The main method that runs the interactive bank system for managing customer accounts.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
    	
    	loadCustomersFromCSV("CS 3331 - Bank Users.csv");
    	
        Scanner scanner = new Scanner(System.in);
        String action; 
		 
        // Start user interaction loop
        do {
            System.out.println("Welcome to El Paso Miners Bank! Please choose an option:");
            System.out.println("1. Inquire Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Pay Someone");
            System.out.println("6. Bank Manager Access");
            System.out.println("7. Create new user");
            System.out.println("Type EXIT to leave.");

            action = scanner.nextLine().trim().toUpperCase();

            switch (action) {
                case "1":  // Inquire balance
                    System.out.println("Please enter your full name: ");
                    String customerName = scanner.nextLine().trim();
                    Customer customer = findCustomerByName(customerName);
                    
                    if (customer != null) {
                        System.out.println("Which account type (Checking/Savings/Credit)?");
                        String accountType = scanner.nextLine().trim().toLowerCase();
                        Account account = customer.getAccountType(accountType);

                        if (account != null) {
                            System.out.println(accountType + " Balance: $" + account.showBalance());
                            logTransaction(customer.getName() + " made a balance inquiry on " + accountType +
                                           "-" + account.getAccountNum() + ". " + customer.getName() +
                                           "'s Balance for " + accountType + "-" + account.getAccountNum() +
                                           ": $" + account.showBalance());
                        } else {
                            System.out.println("Invalid account type.");
                        }
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case "2":  // Deposit Money
                	System.out.println("Pleae enter your full name: ");
                    customerName = scanner.nextLine().trim();
                    customer = findCustomerByName(customerName);
                    
                    if (customer != null) {
                        System.out.println("Which account type (Checking/Savings/Credit)?");
                        String accountType = scanner.nextLine().trim().toLowerCase();
                        Account account = customer.getAccountType(accountType);

                        if (account != null) {
                            System.out.println("Enter amount to deposit:");
                            double depositAmount = scanner.nextDouble();
                            scanner.nextLine();  // Clear input buffer
                            customer.depositMoney(depositAmount);
                            logTransaction(customer.getName() + " deposited $" + depositAmount + " to " +
                                           accountType + "-" + account.getAccountNum() + ". " +
                                           customer.getName() + "'s New Balance for " + accountType + "-" +
                                           account.getAccountNum() + ": $" + account.showBalance());
                        } else {
                            System.out.println("Invalid account type.");
                        }
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case "3":  // Withdraw Money
                    System.out.println("Please enter your full name: ");
                    customerName = scanner.nextLine().trim();
                    customer = findCustomerByName(customerName);
                    
                    if (customer != null) {
                        System.out.println("Which account type (Checking/Savings/Credit)?");
                        String accountType = scanner.nextLine().trim().toLowerCase();
                        Account account = customer.getAccountType(accountType);

                        if (account != null) {
                            System.out.println("Enter amount to withdraw:");
                            double withdrawAmount = scanner.nextDouble();
                            scanner.nextLine();  // Clear input buffer
                            customer.withdrawMoney(withdrawAmount);
                            logTransaction(customer.getName() + " withdrew $" + withdrawAmount + " in cash from " +
                                           accountType + "-" + account.getAccountNum() + ". " +
                                           customer.getName() + "'s Balance for " + accountType + "-" +
                                           account.getAccountNum() + ": $" + account.showBalance());
                        } else {
                            System.out.println("Invalid account type.");
                        }
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                case "4":  // Transfer Money
                	System.out.println("Who is sending the money?");
                    String senderName = scanner.nextLine().trim();
                    System.out.println("Sender's account type (Checking/Savings/Credit):");
                    String senderAccountType = scanner.nextLine().trim().toLowerCase();
                    
                    System.out.println("Who is receiving the money?");
                    String recipientName = scanner.nextLine().trim();
                    System.out.println("Recipient's account type (Checking/Savings/Credit):");
                    String recipientAccountType = scanner.nextLine().trim().toLowerCase();
                    
                    System.out.println("Enter transfer amount:");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine();  // Clear input buffer

                    Customer senderCustomer = findCustomerByName(senderName);
                    Customer recipientCustomer = findCustomerByName(recipientName);

                    if (senderCustomer != null && recipientCustomer != null) {
                       
                        senderCustomer.withdrawMoney(transferAmount); 

                        
                        recipientCustomer.depositMoney(transferAmount); 

                        //log
                        logTransaction(
                            senderCustomer.getName() + " transferred $" + transferAmount +
                            " from " + senderAccountType + " to " + recipientAccountType + ". " +
                            senderCustomer.getName() + "'s new balance for " + senderAccountType + ": $" + 
                            senderCustomer.showBalance(senderAccountType) + ". " +
                            recipientCustomer.getName() + "'s new balance for " + recipientAccountType + ": $" + 
                            recipientCustomer.showBalance(recipientAccountType)
                        );

                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Invalid sender or recipient.");
                    }
                    break;

                case "5":  // Pay Someone
                	System.out.println("Who is paying (Enter full name)?");
                    String senderNamee = scanner.nextLine().trim();
                    System.out.println("From which account? (Checking/Savings/Credit)?");
                    String accType = scanner.nextLine().trim().toLowerCase();
                    System.out.println("Who are you paying (Enter full name)?");
                    String recipientNamee = scanner.nextLine().trim();
                    System.out.println("Enter payment amount:");
                    double payAmount = scanner.nextDouble();
                    scanner.nextLine();  // Clear input buffer

                    
                    Customer sendCustomer = findCustomerByName(senderNamee);
                    Customer recipCustomer = findCustomerByName(recipientNamee);
                    Account account = sendCustomer.getAccountType(accType);

                    if (sendCustomer != null && recipCustomer != null) {
                      
                        sendCustomer.paySomeone(recipCustomer, payAmount);

                        // Log 
                        logTransaction(
                            sendCustomer.getName() + " paid " + recipCustomer.getName() + " $" + payAmount +
                            " from " + accType + "-" + account.getAccountNum() + ". " +
                            sendCustomer.getName() + "'s new balance for " + accType + "-" + account.getAccountNum() + ": $" +
                            sendCustomer.showBalance(accType) + "."
                        );
                    } else {
                        System.out.println("Invalid sender or recipient.");
                    }
                    break;

                case "6":  // Bank Manager Access
                    System.out.println("Welcome Bank Manager! How would you like to inquire?");
                    System.out.println("A. Inquire account by name.");
                    System.out.println("B. Inquire account by type/number.");
                    String managerChoice = scanner.nextLine().trim().toUpperCase();

                    if (managerChoice.equals("A")) {
                        // Inquire account by name
                        System.out.println("Whose account would you like to inquire about?");
                        String managerCustomerName = scanner.nextLine().trim();
                        Customer customerByName = findCustomerByName(managerCustomerName);

                        if (customerByName != null) {
                            customerByName.displayAccountInfo();
                        } else {
                            System.out.println("Customer with name " + managerCustomerName + " not found.");
                        }
                    } else if (managerChoice.equals("B")) {
                        // Inquire account by type/number
                        System.out.println("What is the account type? (Checking/Saving/Credit)");
                        String accountType = scanner.nextLine().trim();
                        System.out.println("Enter the account number:");
                        String accountNumber = scanner.nextLine().trim();

                        Customer customerByAccount = findCustomerByAccount(accountType, accountNumber);

                        if (customerByAccount != null) {
                            customerByAccount.displayAccountInfo();
                        } else {
                            System.out.println("Account not found with type " + accountType + " and number " + accountNumber);
                        }
                    }
                    break;
                case "7":
                	createNewUser(scanner);
                	break;

                default:
                    if (!action.equals("EXIT")) {
                        System.out.println("Invalid option. Please try again.");
                    }
            }
        } while (!action.equals("EXIT"));

        System.out.println("Thank you for using El Paso Miners Bank. Goodbye!");
        scanner.close();
    }
    
    //find the customer
    private static Customer findCustomerByName(String name) {
    	for(int i = 0; i < customerCount; i++) {
    		if(customers[i].getName().equalsIgnoreCase(name)) {
    			return customers[i];
    		}
    	}
    	return null;
    }
    
    private static Customer findCustomerByAccount(String accountType, String accountNumber) {
        for (int i = 0; i < customerCount; i++) {
            if (accountType.equalsIgnoreCase("Checking") && customers[i].getCheckingAccount().getAccountNum().equals(accountNumber)) {
                return customers[i];
            } else if (accountType.equalsIgnoreCase("Saving") && customers[i].getSavingAccount().getAccountNum().equals(accountNumber)) {
                return customers[i];
            } else if (accountType.equalsIgnoreCase("Credit") && customers[i].getCreditAccount().getAccountNum().equals(accountNumber)) {
                return customers[i];
            }
        }
        return null;
    } 
}
