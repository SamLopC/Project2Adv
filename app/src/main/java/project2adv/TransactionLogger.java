package project2adv;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TransactionLogger {
    private static TransactionLogger instance;

    private TransactionLogger() {}

    public static TransactionLogger getInstance() {
        if (instance == null) {
            instance = new TransactionLogger();
        }
        return instance;
    }

    public void log(String message) {
        try (FileWriter writer = new FileWriter("transactions_log.txt", true)) {
            writer.write(LocalDateTime.now() + " - " + message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }
}
