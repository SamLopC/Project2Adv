package project2adv;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The RunBank class is the main class that runs the banking system for El Paso Miners Bank.
 * It allows users to perform actions like balance inquiry, deposit, withdrawal, and transfer.
 */
public class RunBank {
    /**
     * The main method that runs the interactive bank system for managing customer accounts.
     *
     * @param args command-line arguments (not used)
     */


     public static Map<String, String> readCSV(String fileName) {
        Map<String, String> userInfo = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String headerLine = br.readLine();
            if (headerLine == null) return userInfo;
            String[] headers = headerLine.split(",");
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                for (int i = 0; i < headers.length; i++) {
                    userInfo.put(headers[i], data[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
    public static void main(String[] args) {
        // Creating Checking, Saving, and Credit Accounts for each customer
        Checking checkingAcc1 = new Checking("12345", 500.00);  // Mickey's Checking
        Saving savingAcc1 = new Saving("54321", 1000.00);       // Donald's Saving
        Credit creditAcc1 = new Credit("67890", -50.00, 1000.00); // Goofy's Credit
        Saving savingAcc2 = new Saving("13579", 1500.00);       // Minnie's Saving
        Checking checkingAcc2 = new Checking("24680", 750.00);  // Daisy's Checking
        Credit creditAcc2 = new Credit("98765", -100.00, 1500.00); // Pluto's Credit

        // Creating Customers
        Customer customer1 = new Customer("Mickey Mouse", checkingAcc1);
        Customer customer2 = new Customer("Donald Duck", savingAcc1);
        Customer customer3 = new Customer("Goofy", creditAcc1);
        Customer customer4 = new Customer("Minnie Mouse", savingAcc2);
        Customer customer5 = new Customer("Daisy Duck", checkingAcc2);
        Customer customer6 = new Customer("Pluto", creditAcc2);

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
            System.out.println("Type EXIT to leave.");

            action = scanner.nextLine().trim().toUpperCase();

            switch (action) {
                case "1":  // Inquire balance
                    System.out.println("Which customer are you (Mickey, Donald, Goofy, Minnie, Daisy, Pluto)?");
                    String customerName = scanner.nextLine().trim();
                    if (customerName.equalsIgnoreCase("Mickey")) {
                        customer1.displayAccountInfo();
                    } else if (customerName.equalsIgnoreCase("Donald")) {
                        customer2.displayAccountInfo();
                    } else if (customerName.equalsIgnoreCase("Goofy")) {
                        customer3.displayAccountInfo();
                    } else if (customerName.equalsIgnoreCase("Minnie")) {
                        customer4.displayAccountInfo();
                    } else if (customerName.equalsIgnoreCase("Daisy")) {
                        customer5.displayAccountInfo();
                    } else if (customerName.equalsIgnoreCase("Pluto")) {
                        customer6.displayAccountInfo();
                    }
                    break;

                case "2":  // Deposit Money
                    System.out.println("Which customer are you (Mickey, Donald, Goofy, Minnie, Daisy, Pluto)?");
                    customerName = scanner.nextLine().trim();
                    System.out.println("Enter amount to deposit:");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();  // Clear input buffer
                    if (customerName.equalsIgnoreCase("Mickey")) {
                        customer1.depositMoney(depositAmount);
                    } else if (customerName.equalsIgnoreCase("Donald")) {
                        customer2.depositMoney(depositAmount);
                    } else if (customerName.equalsIgnoreCase("Goofy")) {
                        customer3.depositMoney(depositAmount);
                    } else if (customerName.equalsIgnoreCase("Minnie")) {
                        customer4.depositMoney(depositAmount);
                    } else if (customerName.equalsIgnoreCase("Daisy")) {
                        customer5.depositMoney(depositAmount);
                    } else if (customerName.equalsIgnoreCase("Pluto")) {
                        customer6.depositMoney(depositAmount);
                    }
                    break;

                case "3":  // Withdraw Money
                    System.out.println("Which customer are you (Mickey, Donald, Goofy, Minnie, Daisy, Pluto)?");
                    customerName = scanner.nextLine().trim();
                    System.out.println("Enter amount to withdraw:");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine();  // Clear input buffer
                    if (customerName.equalsIgnoreCase("Mickey")) {
                        customer1.withdrawMoney(withdrawAmount);
                    } else if (customerName.equalsIgnoreCase("Donald")) {
                        customer2.withdrawMoney(withdrawAmount);
                    } else if (customerName.equalsIgnoreCase("Goofy")) {
                        customer3.withdrawMoney(withdrawAmount);
                    } else if (customerName.equalsIgnoreCase("Minnie")) {
                        customer4.withdrawMoney(withdrawAmount);
                    } else if (customerName.equalsIgnoreCase("Daisy")) {
                        customer5.withdrawMoney(withdrawAmount);
                    } else if (customerName.equalsIgnoreCase("Pluto")) {
                        customer6.withdrawMoney(withdrawAmount);
                    }
                    break;

                case "4":  // Transfer Money
                    System.out.println("Who is sending the money (Mickey, Donald, Goofy, Minnie, Daisy, Pluto)?");
                    String sender = scanner.nextLine().trim();
                    System.out.println("Who is receiving the money (Mickey, Donald, Goofy, Minnie, Daisy, Pluto)?");
                    String recipient = scanner.nextLine().trim();
                    System.out.println("Enter transfer amount:");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine();  // Clear input buffer

                    if (sender.equalsIgnoreCase("Mickey") && recipient.equalsIgnoreCase("Donald")) {
                        customer1.transferMoney(customer2, transferAmount);
                    } else if (sender.equalsIgnoreCase("Mickey") && recipient.equalsIgnoreCase("Goofy")) {
                        customer1.transferMoney(customer3, transferAmount);
                    } else if (sender.equalsIgnoreCase("Donald") && recipient.equalsIgnoreCase("Mickey")) {
                        customer2.transferMoney(customer1, transferAmount);
                    } else if (sender.equalsIgnoreCase("Donald") && recipient.equalsIgnoreCase("Goofy")) {
                        customer2.transferMoney(customer3, transferAmount);
                    } // Add more if-else cases for other customers if needed
                    break;

                case "5":  // Pay Someone
                    System.out.println("Who is paying (Mickey, Donald, Goofy, Minnie, Daisy, Pluto)?");
                    sender = scanner.nextLine().trim();
                    System.out.println("Who are you paying (Mickey, Donald, Goofy, Minnie, Daisy, Pluto)?");
                    recipient = scanner.nextLine().trim();
                    System.out.println("Enter payment amount:");
                    double payAmount = scanner.nextDouble();
                    scanner.nextLine();  // Clear input buffer

                    if (sender.equalsIgnoreCase("Mickey") && recipient.equalsIgnoreCase("Donald")) {
                        customer1.paySomeone(customer2, payAmount);
                    } else if (sender.equalsIgnoreCase("Mickey") && recipient.equalsIgnoreCase("Goofy")) {
                        customer1.paySomeone(customer3, payAmount);
                    } // Add more if-else cases for other customers if needed
                    break;

                case "6":  // Bank Manager Access
                    System.out.println("Welcome Bank Manager! How would you like to inquire?");
                    System.out.println("A. Inquire account by name.");
                    System.out.println("B. Inquire account by type/number.");
                    String managerChoice = scanner.nextLine().trim().toUpperCase();

                    if (managerChoice.equals("A")) {
                        System.out.println("Whose account would you like to inquire about?");
                        String managerCustomerName = scanner.nextLine().trim();
                        if (managerCustomerName.equalsIgnoreCase("Mickey")) {
                            customer1.displayAccountInfo();
                        } else if (managerCustomerName.equalsIgnoreCase("Donald")) {
                            customer2.displayAccountInfo();
                        } else if (managerCustomerName.equalsIgnoreCase("Goofy")) {
                            customer3.displayAccountInfo();
                        } else if (managerCustomerName.equalsIgnoreCase("Minnie")) {
                            customer4.displayAccountInfo();
                        } else if (managerCustomerName.equalsIgnoreCase("Daisy")) {
                            customer5.displayAccountInfo();
                        } else if (managerCustomerName.equalsIgnoreCase("Pluto")) {
                            customer6.displayAccountInfo();
                        }
                    } else if (managerChoice.equals("B")) {
                        System.out.println("What is the account type? (Checking/Saving/Credit)");
                        String accountType = scanner.nextLine().trim();
                        System.out.println("Enter the account number:");
                        String accountNumber = scanner.nextLine().trim();

                        if (accountType.equalsIgnoreCase("Checking") && accountNumber.equals("12345")) {
                            customer1.displayAccountInfo();
                        } else if (accountType.equalsIgnoreCase("Saving") && accountNumber.equals("54321")) {
                            customer2.displayAccountInfo();
                        } else if (accountType.equalsIgnoreCase("Credit") && accountNumber.equals("67890")) {
                            customer3.displayAccountInfo();
                        }
                    }
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
}
