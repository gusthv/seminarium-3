package se.kth.iv1350.retailstore.util.error;

public class ItemNotFoundException extends RuntimeException {
    /**
    * Creates a new instance with the specified error message.
    * @param message The message that describes the error.
    */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
