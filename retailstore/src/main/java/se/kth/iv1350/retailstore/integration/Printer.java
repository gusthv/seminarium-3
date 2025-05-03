package se.kth.iv1350.retailstore.integration;

/**
 * Represents an external printer used to print receipts.
 * The printer receives a receipt as a formatted string and handles its
 * printing.
 */
public class Printer {
    /**
     * Sends the provided receipt string to the printer for output.
     *
     * @param receipt The formatted receipt to be printed.
     */
    public void printReceipt(String receipt) {
        System.out.println(receipt);
    }
}
