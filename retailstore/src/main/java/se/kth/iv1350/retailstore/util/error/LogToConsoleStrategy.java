// File: LogToConsoleStrategy.java
package se.kth.iv1350.retailstore.util.error;

/**
 * A strategy that logs errors to the console.
 */
public class LogToConsoleStrategy implements ErrorStrategy {
    @Override
    public void handle(Exception e) {
        System.out.println("\n[ERROR] " + e.getMessage());
    }
}