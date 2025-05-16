package se.kth.iv1350.retailstore.util.error;

/**
 * Strategy interface for handling errors.
 */
public interface ErrorStrategy {
    void handle(Exception e);
}
