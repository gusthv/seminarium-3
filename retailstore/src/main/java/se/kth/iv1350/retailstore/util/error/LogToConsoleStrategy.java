package se.kth.iv1350.retailstore.util.error;

/**
 * A strategy that logs technical error details to the console.
 * Should be used for developer debugging, not user-facing messages.
 */
public class LogToConsoleStrategy implements ErrorStrategy {
    @Override
    public void handle(Exception e) {
        System.err.println("\n[SYSTEM ERROR] " + e.getClass().getSimpleName());
        System.err.println("Details: " + e.getMessage());
    }
}