package se.kth.iv1350.retailstore.util.error;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages error handling using a strategy pattern for error handling and the observer pattern for notifying.
 * Contains the {@link ErrorObserver} interface which determines how observers handle errors.
 */
public class ErrorManager {
    private ErrorStrategy strategy;

    /**
     * Observer interface for receiving error notifications.
     */
    public interface ErrorObserver {
        void handleError(Exception e);
    }

    private final List<ErrorObserver> observers = new ArrayList<>();

    /**
     * Sets the strategy to use for handling errors.
     * 
     * @param strategy The error handling strategy.
     */
    public void setStrategy(ErrorStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Adds an observer to be notified on error.
     * 
     * @param observer The error observer.
     */
    public void addObserver(ErrorObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies the strategy and all observers of an error.
     * 
     * @param e The exception to handle.
     */
    public void notifyError(Exception e) {
        if (strategy != null) {
            strategy.handle(e);
        }

        for (ErrorObserver observer : observers) {
            observer.handleError(e);
        }
    }
}
