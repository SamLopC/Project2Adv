package project2adv;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RunBank {
    private static final AtomicInteger userIdGenerator = new AtomicInteger(1001); // Starts at 10011
    private static final AtomicInteger accountNumGenerator = new AtomicInteger(5001); // Starts at 5001
    private static final Scanner scanner = new Scanner(System.in);

    private static final String MANAGER_PASSWORD = "secret"; // Define the manager password
    private static final String CSV_FILE_PATH = "customers.csv";


    
    // Map to store customers by userID for unique identification
    private static final Map<Integer, Customer> customers = new HashMap<>();

    public static void createNewUser() {
        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();
    
        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();
    
        System.out.println("Enter Date of Birth (YYYY-MM-DD):");
        String dob = scanner.nextLine();
    
        System.out.println("Enter Address:");
        String address = scanner.nextLine();
    
        System.out.println("Enter City:");
        String city = scanner.nextLine();
    
        System.out.println("Enter State:");
        String state = scanner.nextLine();
    
        System.out.println("Enter Zip:");
        String zip = scanner.nextLine();
    
        System.out.println("Enter Phone Number:");
        String phoneNumber = scanner.nextLine();
    
        int creditScore = 0;
        boolean validInput = false;
    
        while (!validInput) {
            System.out.println("Enter Credit Score:");
            try {
                creditScore = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for the credit score.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        int userId = userIdGenerator.getAndIncrement();
        String checkingAccountNum = String.valueOf(accountNumGenerator.getAndIncrement());
        String savingAccountNum = String.valueOf(accountNumGenerator.getAndIncrement());
        String creditAccountNum = String.valueOf(accountNumGenerator.getAndIncrement());

        double creditLimit = generateCreditLimit(creditScore);

        Account checking = new Checking(checkingAccountNum, 0.0);
        Account saving = new Saving(savingAccountNum, 0.0);
        Account credit = new Credit(creditAccountNum, 0.0, creditLimit);

        Customer newCustomer = new Customer(userId, firstName, lastName, dob, address, city, state, zip, phoneNumber, checking, saving, credit);
        customers.put(userId, newCustomer);

        System.out.println("New user created successfully!");
        newCustomer.displayAccountInfo();

        writeCustomerToCSV(newCustomer);
    }

    private static void writeCustomerToCSV(Customer customer) {
        java.io.File file = new java.io.File(CSV_FILE_PATH);
        
        try {
            boolean isNewFile = !file.exists() && file.createNewFile();
    
            try (FileWriter writer = new FileWriter(file, true)) {
                if (isNewFile) {
                    writer.append("User ID,Name,DOB,Address,City,State,Zip,Phone Number,Checking Account,Checking Balance,Saving Account,Saving Balance,Credit Account,Credit Balance,Credit Limit\n");
                }
    
                writer.append(String.valueOf(customer.getUserID())).append(",");
                writer.append(customer.getName()).append(",");
                writer.append(customer.getDob()).append(",");
                writer.append(customer.getAddress()).append(",");
                writer.append(customer.getCity()).append(",");
                writer.append(customer.getState()).append(",");
                writer.append(customer.getZip()).append(",");
                writer.append(customer.getPhoneNumber()).append(",");
                writer.append(customer.getCheckingAccount().getAccountNum()).append(",");
                writer.append(String.valueOf(customer.getCheckingAccount().showBalance())).append(",");
                writer.append(customer.getSavingAccount().getAccountNum()).append(",");
                writer.append(String.valueOf(customer.getSavingAccount().showBalance())).append(",");
                writer.append(customer.getCreditAccount().getAccountNum()).append(",");
                writer.append(String.valueOf(customer.getCreditAccount().showBalance())).append(",");
                writer.append(String.valueOf(((Credit) customer.getCreditAccount()).getCreditLimit())).append("\n");
    
                System.out.println("Customer details saved to CSV successfully.");
            }
        } catch (IOException e) {
            System.out.println("Error writing to CSV file.");
            e.printStackTrace();
        }
    }
    
    private static void loadCustomersFromCSV() {
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line = br.readLine();  // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int userID = Integer.parseInt(data[0]);
                String[] nameParts = data[1].split(" ");
                String firstName = nameParts[0];
                String lastName = nameParts.length > 1 ? nameParts[1] : "";
                String dob = data[2];
                String address = data[3];
                String city = data[4];
                String state = data[5];
                String zip = data[6];
                String phoneNumber = data[7];
                
                String checkingAccountNum = data[8];
                double checkingBalance = Double.parseDouble(data[9]);
                Account checking = new Checking(checkingAccountNum, checkingBalance);
    
                String savingAccountNum = data[10];
                double savingBalance = Double.parseDouble(data[11]);
                Account saving = new Saving(savingAccountNum, savingBalance);
    
                String creditAccountNum = data[12];
                double creditBalance = Double.parseDouble(data[13]);
                double creditLimit = Double.parseDouble(data[14]);
                Account credit = new Credit(creditAccountNum, creditBalance, creditLimit);
    
                Customer customer = new Customer(userID, firstName, lastName, dob, address, city, state, zip, phoneNumber, checking, saving, credit);
                customers.put(userID, customer);
    
                userIdGenerator.set(Math.max(userIdGenerator.get(), userID + 1));
                accountNumGenerator.set(Math.max(accountNumGenerator.get(), Integer.parseInt(creditAccountNum) + 1));
            }
            System.out.println("Customers loaded successfully from CSV.");
        } catch (IOException e) {
            System.out.println("Error reading from CSV file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing customer data from CSV file.");
            e.printStackTrace();
        }
    }

    public static void generateTransactionStatement(Customer customer) {
        String fileName = customer.getFirstName() + "_" + customer.getLastName() + "_Transaction_Statement.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Transaction Statement for " + customer.getFirstName() + " " + customer.getLastName() + "\n");
            writer.write("Date of Statement: " + LocalDate.now() + "\n\n");
            
            // Account information
            writer.write("Account Information:\n");
            writer.write("Checking Account Number: " + customer.getCheckingAccount().getAccountNum() + "\n");
            writer.write("Saving Account Number: " + customer.getSavingAccount().getAccountNum() + "\n");
            writer.write("Credit Account Number: " + customer.getCreditAccount().getAccountNum() + "\n\n");
            
            // Balances
            writer.write("Starting Balance: $" + customer.getCheckingAccount().getStartingBalance() + "\n");
            writer.write("Ending Balance: $" + customer.getCheckingAccount().showBalance() + "\n\n");

            // Transaction history
            writer.write("Transaction History:\n");
            List<String> transactionHistory = TransactionLogger.getInstance().getTransactionHistory();
            for (String transaction : transactionHistory) {
                writer.write(transaction + "\n");
            }
            
            System.out.println("Transaction statement generated: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing transaction statement.");
            e.printStackTrace();
        }
    }

    public static Customer searchCustomerByFirstName(String firstName) {
        List<Customer> matches = new ArrayList<>();
        for (Customer customer : customers.values()) {
            if (customer.getFirstName().equalsIgnoreCase(firstName)) {
                matches.add(customer);
            }
        }
        return handleSearchResults(matches);
    }

    public static Customer searchCustomerByLastName(String lastName) {
        List<Customer> matches = new ArrayList<>();
        for (Customer customer : customers.values()) {
            if (customer.getLastName().equalsIgnoreCase(lastName)) {
                matches.add(customer);
            }
        }
        return handleSearchResults(matches);
    }

    private static Customer handleSearchResults(List<Customer> matches) {
        if (matches.isEmpty()) {
            System.out.println("No customer found.");
            return null;
        } else if (matches.size() == 1) {
            return matches.get(0);
        } else {
            System.out.println("Multiple customers found. Please choose one:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + ". " + matches.get(i).getFirstName() + " " + matches.get(i).getLastName() + " (User ID: " + matches.get(i).getUserID() + ")");
            }
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over
            return matches.get(choice - 1);
        }
    }

    public static void inquireBalance() {
        Customer customer = findCustomerByName();
        if (customer != null) {
            customer.displayAccountInfo();
        } else {
            System.out.println("Customer not found.");
        }
    }

    public static void depositMoney(Customer customer) {
        double depositAmount = 0;
        boolean validInput = false;
    
        // Loop until the user provides a valid input
        while (!validInput) {
            System.out.println("Enter the amount to deposit:");
            try {
                depositAmount = scanner.nextDouble();
                scanner.nextLine(); // Clear input buffer
                if (depositAmount <= 0) {
                    throw new IllegalArgumentException("Deposit amount must be positive.");
                }
                validInput = true; // Input is valid, exit the loop
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine(); // Clear the invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    
        System.out.println("Select the account to deposit into:");
        Account account = selectAccount(scanner, customer);
    
        if (account != null) {
            try {
                account.deposit(depositAmount, customer.getName(), true);
                System.out.println("Deposit successful.");
            } catch (IllegalArgumentException e) {
                System.out.println("Deposit failed: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid account selection.");
        }
    }
    
    public static void withdrawMoney(Customer customer) {
        double withdrawAmount = 0;
        boolean validInput = false;
    
        // Loop until the user provides valid input
        while (!validInput) {
            System.out.println("Enter the amount to withdraw:");
            try {
                withdrawAmount = scanner.nextDouble();
                scanner.nextLine(); // Clear input buffer
                if (withdrawAmount <= 0) {
                    throw new IllegalArgumentException("Withdrawal amount must be positive.");
                }
                validInput = true; // Input is valid, exit the loop
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine(); // Clear the invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    
        System.out.println("Select the account to withdraw from:");
        Account account = selectAccount(scanner, customer);
    
        if (account != null) {
            try {
                account.withdraw(withdrawAmount, customer.getName(), true);
                System.out.println("Withdrawal successful.");
            } catch (IllegalArgumentException e) {
                System.out.println("Withdrawal failed: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid account selection.");
        }
    }
    
    public static void transferMoneyForCustomer(Customer customer) {
        System.out.println("Select the source account for the transfer:");
        Account sourceAccount = selectAccount(scanner, customer);
    
        System.out.println("Select the target account to receive the funds:");
        Account targetAccount = selectAccount(scanner, customer);
    
        if (sourceAccount == targetAccount) {
            System.out.println("Source and target accounts must be different.");
            return;
        }
    
        double transferAmount = 0;
        boolean validInput = false;
    
        // Loop until the user provides valid input
        while (!validInput) {
            System.out.println("Enter transfer amount:");
            try {
                transferAmount = scanner.nextDouble();
                scanner.nextLine(); // Clear input buffer
                if (transferAmount <= 0) {
                    throw new IllegalArgumentException("Transfer amount must be positive.");
                }
                validInput = true; // Input is valid, exit the loop
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine(); // Clear the invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    
        if (sourceAccount != null && targetAccount != null) {
            try {
                sourceAccount.withdraw(transferAmount, customer.getName(), true);
                targetAccount.deposit(transferAmount, customer.getName(), true);
                System.out.println("Successfully transferred $" + transferAmount);
            } catch (IllegalArgumentException e) {
                System.out.println("Transfer failed: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid account selection.");
        }
    }
        
    private static Account selectAccount(Scanner scanner, Customer customer) {
        System.out.println("1. Checking Account");
        System.out.println("2. Savings Account");
        System.out.println("3. Credit Account");
        System.out.print("Choose an account by number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Clear input buffer
    
        switch (choice) {
            case 1:
                return customer.getCheckingAccount();
            case 2:
                return customer.getSavingAccount();
            case 3:
                return customer.getCreditAccount();
            default:
                System.out.println("Invalid choice.");
                return null;
        }
    }
    
    public static void paySomeoneForCustomer(Customer payer) {
        System.out.println("Enter the recipient's first name:");
        String recipientFirstName = scanner.nextLine().trim();
        System.out.println("Enter the recipient's last name:");
        String recipientLastName = scanner.nextLine().trim();
    
        Customer recipient = findCustomer(recipientFirstName, recipientLastName);
        if (recipient == null) {
            System.out.println("Recipient not found.");
            return;
        }
    
        System.out.println("Select your account to pay from:");
        Account payerAccount = selectAccount(scanner, payer);
    
        System.out.println("Select the recipient's account to receive the payment:");
        Account recipientAccount = selectAccount(scanner, recipient);
    
        System.out.println("Enter the amount to pay:");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Clear input buffer
    
        if (payerAccount != null && recipientAccount != null) {
            try {
                payerAccount.withdraw(amount, payer.getName(), true);
                recipientAccount.deposit(amount, recipient.getName(), true);
                System.out.println("Successfully paid $" + amount + " to " + recipient.getFirstName());
            } catch (IllegalArgumentException e) {
                System.out.println("Payment failed: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid account selection.");
        }
    }
    
    private static Customer findCustomerByName() {
        System.out.println("Search by (1) First Name or (2) Last Name:");
        String searchType = scanner.nextLine().trim();
        Customer customer = null;
        
        if (searchType.equals("1")) {
            System.out.println("Enter First Name:");
            String firstName = scanner.nextLine().trim();
            customer = searchCustomerByFirstName(firstName);
        } else if (searchType.equals("2")) {
            System.out.println("Enter Last Name:");
            String lastName = scanner.nextLine().trim();
            customer = searchCustomerByLastName(lastName);
        } else {
            System.out.println("Invalid input.");
        }
        
        return customer;
    }

    private static double generateCreditLimit(int creditScore) {
        Random random = new Random();
        if (creditScore <= 580) {
            return 100 + random.nextInt(600);
        } else if (creditScore <= 669) {
            return 700 + random.nextInt(4300);
        } else if (creditScore <= 739) {
            return 5000 + random.nextInt(2500);
        } else if (creditScore <= 799) {
            return 7500 + random.nextInt(8500);
        } else {
            return 16000 + random.nextInt(9000);
        }
    }
    
    private static Customer findCustomerByNameOrId() {
        System.out.println("Search by (1) Name or (2) ID:");
        String searchType = scanner.nextLine().trim();

        if ("1".equals(searchType)) {
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine().trim();
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine().trim();
            return findCustomer(firstName, lastName);
        } else if ("2".equals(searchType)) {
            System.out.print("Enter User ID: ");
            try {
                int userId = Integer.parseInt(scanner.nextLine().trim());
                return customers.get(userId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
            }
        }
        return null;
    }



    // Bank Manager Access

    private static void bankManagerAccess() {
        System.out.print("Enter Bank Manager Password: ");
        String inputPassword = scanner.nextLine().trim();
    
        if (inputPassword.equals(MANAGER_PASSWORD)) {
            System.out.println("Access granted. Welcome Bank Manager! Choose an option:");
            System.out.println("A. Inquire account by name.");
            System.out.println("B. Inquire account by type/number.");
            System.out.println("C. Process transactions from file.");
            System.out.println("D. Generate bank statement for a user.");
    
            String managerChoice = scanner.nextLine().trim().toUpperCase();
    
            switch (managerChoice) {
                case "A":
                    inquireAccountByName();
                    break;
                case "B":
                    inquireAccountByTypeAndNumber();
                    break;
                case "C":
                    processTransactionsFromFile("Transactions.csv");
                    break;
                case "D":
                    System.out.println("Enter customer name or ID to generate statement:");
                    Customer customer = findCustomerByNameOrId();
                    if (customer != null) {
                        generateBankStatement(customer);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                default:
                    System.out.println("Invalid option. Returning to main menu.");
                    break;
            }
        } else {
            System.out.println("Access denied. Incorrect password.");
        }
    }
    
    private static void inquireAccountByName() {
        System.out.println("Whose account would you like to inquire about?");
        String managerCustomerName = scanner.nextLine().trim();
    
        // Loop through the customers map and check names
        for (Customer customer : customers.values()) {
            if (customer.getFirstName().equalsIgnoreCase(managerCustomerName)) {
                customer.displayAccountInfo();
                return;
            }
        }
        System.out.println("No customer found with the name: " + managerCustomerName);
    }

    private static void inquireAccountByTypeAndNumber() {
        System.out.println("What is the account type? (Checking/Saving/Credit)");
        String accountType = scanner.nextLine().trim();
        System.out.println("Enter the account number:");
        String accountNumber = scanner.nextLine().trim();
    
        // Loop through all customers to find the account by type and number
        for (Customer customer : customers.values()) {
            Account account = getAccountByType(customer, accountType);
            if (account != null && account.getAccountNum().equals(accountNumber)) {
                System.out.println("Account found for customer: " + customer.getFirstName() + " " + customer.getLastName());
                customer.displayAccountInfo();
                return;
            }
        }
        System.out.println("No account found with type " + accountType + " and account number " + accountNumber);
    }

    // Generate a Bank Statement for a specific user (only accessible by manager)
    public static void generateBankStatement(Customer customer) {
            // Set the file name as "[FirstName_LastName]_Bank_Statement.txt"
            String fileName = customer.getFirstName() + "_" + customer.getLastName() + "_Bank_Statement.txt";
            
            try (FileWriter writer = new FileWriter(fileName)) {
                // Write header information for the bank statement
                writer.write("----- Bank Statement -----\n");
                writer.write("Date: " + LocalDate.now() + "\n\n");
    
                // Write customer information
                writer.write("Customer Information:\n");
                writer.write("ID: " + customer.getUserID() + "\n");
                writer.write("Name: " + customer.getFirstName() + " " + customer.getLastName() + "\n");
                writer.write("Date of Birth: " + customer.getDob() + "\n");
                writer.write("Address: " + customer.getAddress() + "\n");
                writer.write("Phone Number: " + customer.getPhoneNumber() + "\n\n");
    
                // Write account information
                writer.write("Account Information:\n");
                writer.write("Checking Account Number: " + customer.getCheckingAccount().getAccountNum() + "\n");
                writer.write("Checking Starting Balance: $" + customer.getCheckingAccount().getStartingBalance() + "\n");
                writer.write("Savings Account Number: " + customer.getSavingAccount().getAccountNum() + "\n");
                writer.write("Savings Starting Balance: $" + customer.getSavingAccount().getStartingBalance() + "\n");
                writer.write("Credit Account Number: " + customer.getCreditAccount().getAccountNum() + "\n");
                writer.write("Credit Max: $" + ((Credit) customer.getCreditAccount()).getCreditLimit() + "\n");
                writer.write("Credit Starting Balance: $" + customer.getCreditAccount().getStartingBalance() + "\n\n");
    
                // Write transaction history
                writer.write("Transaction History (Current Session):\n");
                List<String> transactionHistory = TransactionLogger.getInstance().getTransactionHistory(); // Assuming you have session-based transaction tracking
                if (transactionHistory.isEmpty()) {
                    writer.write("No transactions recorded in this session.\n");
                } else {
                    for (String transaction : transactionHistory) {
                        writer.write(transaction + "\n");
                    }
                }
    
                System.out.println("Bank statement generated: " + fileName);
            } catch (IOException e) {
                System.out.println("Error writing bank statement.");
                e.printStackTrace();
            }
        }

    //new bank manager CSV 
    public static void processTransactionsFromFile(String transactionFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(transactionFilePath))) {
            String line = br.readLine();  // Skip the header line
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                // Check if the array length meets the expected number of fields
                if (data.length < 8) {
                    System.out.println("Transaction skipped due to incomplete data: " + line);
                    continue;
                }

                String fromFirstName = data[0].trim();
                String fromLastName = data[1].trim();
                String fromWhere = data[2].trim();
                String action = data[3].trim().toLowerCase();
                String toFirstName = data[4].trim();
                String toLastName = data[5].trim();
                String toWhere = data[6].trim();
                double amount = 0.0;

                // Attempt to parse the amount, if present
                try {
                    if (!data[7].trim().isEmpty()) {
                        amount = Double.parseDouble(data[7].trim());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount in transaction: " + line);
                    continue;
                }

                // Execute the action based on the type of transaction
                switch (action) {
                    case "pays":
                        handlePaysTransaction(fromFirstName, fromLastName, fromWhere, toFirstName, toLastName, toWhere, amount);
                        break;
                    case "transfers":
                        handleTransfersTransaction(fromFirstName, fromLastName, fromWhere, toWhere, amount);
                        break;
                    case "inquires":
                        handleInquiresTransaction(fromFirstName, fromLastName, fromWhere);
                        break;
                    case "withdraws":
                        handleWithdrawsTransaction(fromFirstName, fromLastName, fromWhere, amount);
                        break;
                    case "deposits":
                        handleDepositsTransaction(toFirstName, toLastName, toWhere, amount);
                        break;
                    default:
                        System.out.println("Unknown action: " + action + " in transaction: " + line);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions file: " + e.getMessage());
        }
    }
    
    // Updated findCustomer method to check both specified files for customer data
    private static Customer findCustomer(String firstName, String lastName) {
        System.out.println("Attempting to find customer: " + firstName + " " + lastName);
        Customer customer = getCustomerFromFile("CS3331-BankUsers.csv", firstName, lastName);
        
        if (customer == null) {
            System.out.println("Customer " + firstName + " " + lastName + " not found in CS3331-BankUsers.csv. Searching in customers.csv...");
            customer = getCustomerFromFile("customers.csv", firstName, lastName);
        }
    
        if (customer == null) {
            System.out.println("Customer " + firstName + " " + lastName + " not found in either file.");
        } else {
            System.out.println("Customer found: " + customer.getFirstName() + " " + customer.getLastName());
        }
        
        return customer;
    }

    private static Customer getCustomerFromFile(String fileName, String firstName, String lastName) {
        System.out.println("Searching for customer in file: " + fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();  // Skip header line
    
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                // Adjust parsing based on file format
                boolean isBankUsersFile = fileName.equals("CS3331-BankUsers.csv");
                int requiredLength = isBankUsersFile ? 13 : 15;
    
                if (data.length < requiredLength) {
                    System.out.println("Skipping incomplete line: " + line);
                    continue;
                }
    
                // Extract first and last names based on file format
                String fileFirstName, fileLastName;
                if (isBankUsersFile) {
                    fileFirstName = data[1].trim(); // CS3331-BankUsers.csv has separate first name
                    fileLastName = data[2].trim();  // and last name columns
                } else {
                    // customers.csv has full name in one field (data[1]), split it
                    String[] nameParts = data[1].trim().split(" ");
                    if (nameParts.length < 2) {
                        System.out.println("Skipping line with incomplete name: " + line);
                        continue;
                    }
                    fileFirstName = nameParts[0];
                    fileLastName = nameParts[1];
                }
    
                if (fileFirstName.equalsIgnoreCase(firstName) && fileLastName.equalsIgnoreCase(lastName)) {
                    System.out.println("Customer " + firstName + " " + lastName + " found in file: " + fileName);
    
                    // Parse fields according to format
                    int userID = Integer.parseInt(data[0].trim());
                    String dobRaw = data[isBankUsersFile ? 3 : 2].trim();
    
                    // Normalize the Date of Birth to a standard format (e.g., YYYY-MM-DD)
                    String dobNormalized = normalizeDate(dobRaw);
    
                    if (dobNormalized == null) {
                        System.out.println("Skipping line due to invalid DOB format: " + dobRaw);
                        continue;
                    }
    
                    String address = data[isBankUsersFile ? 4 : 3].trim();
                    String city = data[isBankUsersFile ? 5 : 4].trim();
                    String state = data[isBankUsersFile ? 6 : 5].trim();
                    String zip = data[isBankUsersFile ? 7 : 6].trim();
                    String phoneNumber = data[isBankUsersFile ? 8 : 7].trim();
    
                    // Accounts (Checking, Savings, Credit)
                    String checkingAccountNum = data[isBankUsersFile ? 9 : 8].trim();
                    double checkingBalance = Double.parseDouble(data[isBankUsersFile ? 10 : 9].trim());
                    Account checking = new Checking(checkingAccountNum, checkingBalance);
    
                    String savingAccountNum = data[isBankUsersFile ? 11 : 10].trim();
                    double savingBalance = Double.parseDouble(data[isBankUsersFile ? 12 : 11].trim());
                    Account saving = new Saving(savingAccountNum, savingBalance);
    
                    // For credit account, handle cases where fields may be missing
                    String creditAccountNum = data.length > (isBankUsersFile ? 13 : 12) ? data[isBankUsersFile ? 13 : 12].trim() : "";
                    double creditBalance = 0.0;
                    double creditLimit = 0.0;
                    try {
                        creditBalance = data.length > (isBankUsersFile ? 14 : 13) ? Double.parseDouble(data[isBankUsersFile ? 14 : 13].trim()) : 0.0;
                        creditLimit = data.length > (isBankUsersFile ? 15 : 14) ? Double.parseDouble(data[isBankUsersFile ? 15 : 14].trim()) : 0.0;
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping credit fields due to format issue: " + line);
                    }
                    Account credit = new Credit(creditAccountNum, creditBalance, creditLimit);
    
                    return new Customer(userID, fileFirstName, fileLastName, dobNormalized, address, city, state, zip, phoneNumber, checking, saving, credit);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + fileName);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing customer data from file: " + fileName);
            e.printStackTrace();
        }
        System.out.println("Customer " + firstName + " " + lastName + " not found in file: " + fileName);
        return null;
    }
    
    

    // Transaction handling methods...

    private static void handlePaysTransaction(String fromFirstName, String fromLastName, String fromWhere,
                                              String toFirstName, String toLastName, String toWhere, double amount) {
        Customer payer = findCustomer(fromFirstName, fromLastName);
        Customer payee = findCustomer(toFirstName, toLastName);

        if (payer == null || payee == null) {
            System.out.println("Transaction failed: Payer or payee not found.");
            return;
        }

        Account fromAccount = getAccountByType(payer, fromWhere);
        Account toAccount = getAccountByType(payee, toWhere);

        if (fromAccount != null && toAccount != null) {
            try {
                fromAccount.withdraw(amount, payer.getName(), false);
                toAccount.deposit(amount, payee.getName(), false);
                System.out.println("Transaction successful: " + fromFirstName + " paid " + toFirstName + " $" + amount);
            } catch (IllegalArgumentException e) {
                System.out.println("Transaction failed: " + e.getMessage());
            }
        } else {
            System.out.println("Transaction failed: Account not found.");
        }
    }

    private static void handleTransfersTransaction(String firstName, String lastName, String fromWhere, String toWhere, double amount) {
        Customer customer = findCustomer(firstName, lastName);
        if (customer == null) {
            System.out.println("Transaction failed: Customer not found.");
            return;
        }

        Account fromAccount = getAccountByType(customer, fromWhere);
        Account toAccount = getAccountByType(customer, toWhere);

        if (fromAccount != null && toAccount != null) {
            try {
                fromAccount.withdraw(amount, customer.getName(), false);
                toAccount.deposit(amount, customer.getName(), false);
                System.out.println("Transaction successful: Transferred $" + amount + " from " + fromWhere + " to " + toWhere);
            } catch (IllegalArgumentException e) {
                System.out.println("Transaction failed: " + e.getMessage());
            }
        } else {
            System.out.println("Transaction failed: Account not found.");
        }
    }

    private static void handleInquiresTransaction(String firstName, String lastName, String fromWhere) {
        Customer customer = findCustomer(firstName, lastName);
        if (customer == null) {
            System.out.println("Inquiry failed: Customer not found.");
            return;
        }

        Account account = getAccountByType(customer, fromWhere);
        if (account != null) {
            System.out.println("Balance inquiry: " + fromWhere + " balance is $" + account.showBalance());
        } else {
            System.out.println("Inquiry failed: Account not found.");
        }
    }

    private static void handleWithdrawsTransaction(String firstName, String lastName, String fromWhere, double amount) {
        Customer customer = findCustomer(firstName, lastName);
        if (customer == null) {
            System.out.println("Transaction failed: Customer not found.");
            return;
        }

        Account account = getAccountByType(customer, fromWhere);
        if (account != null) {
            try {
                account.withdraw(amount, customer.getName(), false);
                System.out.println("Transaction successful: Withdrew $" + amount + " from " + fromWhere);
            } catch (IllegalArgumentException e) {
                System.out.println("Transaction failed: " + e.getMessage());
            }
        } else {
            System.out.println("Transaction failed: Account not found.");
        }
    }

    private static void handleDepositsTransaction(String firstName, String lastName, String toWhere, double amount) {
        Customer customer = findCustomer(firstName, lastName);
        if (customer == null) {
            System.out.println("Transaction failed: Customer not found.");
            return;
        }

        Account account = getAccountByType(customer, toWhere);
        if (account != null) {
            account.deposit(amount, customer.getName(), false);
            System.out.println("Transaction successful: Deposited $" + amount + " to " + toWhere);
        } else {
            System.out.println("Transaction failed: Account not found.");
        }
    }



    // Helper method to get the account based on type
    private static Account getAccountByType(Customer customer, String accountType) {
        switch (accountType.toLowerCase()) {
            case "checking":
                return customer.getCheckingAccount();
            case "saving":
            case "savings":
                return customer.getSavingAccount();
            case "credit":
                return customer.getCreditAccount();
            default:
                System.out.println("Unknown account type: " + accountType);
                return null;
        }
    }


    
    private static void handleCustomerLogin() {
        System.out.println("Please log in:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Password (Your Date of Birth in YYYYMMDD format or other valid formats, e.g., 5-May-41): ");
        String passwordInput = scanner.nextLine().trim();
        
        Customer customer = findCustomer(firstName, lastName);
        
        if (customer != null) {
            // Get the stored Date of Birth in the expected format (YYYYMMDD)
            String expectedPassword = customer.getDob().replaceAll("-", "");
            
            // Normalize the user input to the expected format
            String normalizedPassword = normalizeDate(passwordInput);
    
            // Validate the password
            if (normalizedPassword != null && normalizedPassword.equals(expectedPassword)) {
                System.out.println("Login successful. Welcome " + customer.getFirstName() + "!");
                handleCustomerActions(customer);
            } else {
                System.out.println("Invalid password. Access denied.");
            }
        } else {
            System.out.println("Customer not found. Please try again.");
        }
    }
    
    /**
     * Normalizes user input for the password to the format YYYYMMDD.
     * 
     * @param dobInput The user input for date of birth.
     * @return The normalized password in YYYYMMDD format, or null if the input is invalid.
     */
    private static String normalizeDate(String dobInput) {
        // Define multiple date formats the user might enter
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("yyyyMMdd"),     // Format: 19410505
            DateTimeFormatter.ofPattern("d-MMM-yy", Locale.ENGLISH), // Format: 5-May-41
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),   // Format: 05/05/1941
            DateTimeFormatter.ofPattern("yyyy-MM-dd")    // Format: 1941-05-05
        };
    
        // Try parsing the input with each format
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate date = LocalDate.parse(dobInput, formatter);
                // Return the normalized format: YYYYMMDD
                return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            } catch (DateTimeParseException ignored) {
                // Try the next format if this one fails
            }
        }
    
        System.out.println("Invalid date format. Please enter a valid date of birth.");
        return null;
    }
    
    

    private static void handleCustomerActions(Customer customer) {
        String action;
    
        do {
            System.out.println("\nHello " + customer.getFirstName() + "! What would you like to do?");
            System.out.println("1. Inquire Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Pay Someone");
            System.out.println("6. Generate Transaction Statement");
            System.out.println("Type EXIT to log out.");
    
            action = scanner.nextLine().trim().toUpperCase();
    
            switch (action) {
                case "1":
                    customer.displayAccountInfo();
                    break;
    
                case "2":
                    depositMoney(customer);
                    break;
    
                case "3":
                    withdrawMoney(customer);
                    break;
    
                case "4":
                    transferMoneyForCustomer(customer);
                    break;
    
                case "5":
                    paySomeoneForCustomer(customer);
                    break;
    
                case "6":
                    generateTransactionStatement(customer);
                    break;
    
                default:
                    if (!action.equals("EXIT")) {
                        System.out.println("Invalid option. Please try again.");
                    }
            }
        } while (!action.equals("EXIT"));
    
        System.out.println("You have successfully logged out. Have a great day!");
    }
    

    public static void main(String[] args) {
        loadCustomersFromCSV(); 
        String action;
    
        do {
            System.out.println("Welcome to El Paso Miners Bank! Please select your role:");
            System.out.println("1. Customer");
            System.out.println("2. Bank Manager");
            System.out.println("Type EXIT to leave.");
    
            action = scanner.nextLine().trim().toUpperCase();
    
            switch (action) {
                case "1": // Customer
                    handleCustomerLogin();
                    break;
    
                case "2": // Bank Manager
                    bankManagerAccess();
                    break;
    
                default:
                    if (!action.equals("EXIT")) {
                        System.out.println("Invalid option. Please try again.");
                    }
            }
        } while (!action.equals("EXIT"));
    
        System.out.println("Thank you for using El Paso Miners Bank. Goodbye!");
    }
}    