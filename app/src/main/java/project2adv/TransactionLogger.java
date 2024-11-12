package project2adv;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogger {
    private static TransactionLogger instance;
    private final List<String> transactionHistory;  // Store all transaction logs

    private TransactionLogger() {
        transactionHistory = new ArrayList<>();
    }

    public static TransactionLogger getInstance() {
        if (instance == null) {
            instance = new TransactionLogger();
        }
        return instance;
    }

    public void log(String message) {
        String logEntry = LocalDateTime.now() + " - " + message;
        transactionHistory.add(logEntry);  // Add to history list
        try (FileWriter writer = new FileWriter("transactions_log.txt", true)) {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);  // Return a copy of the history
    }
}
