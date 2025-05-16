package se.kth.iv1350.retailstore.util.error;

public class InventoryErrorException extends RuntimeException {
    /**
    * Creates a new instance with the specified error message.
    * @param message The message that describes the error.
    */
    public InventoryErrorException(String message) {
        super(message);
    }
}
