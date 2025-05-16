package se.kth.iv1350.retailstore.util.error;

/**
 * Observer interface to be notified of errors.
 */
public interface ErrorObserver {
    void onError(Exception e);
}
