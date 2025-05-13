package se.kth.iv1350.retailstore.model;

/**
 * Represents a cash payment made during a sale.
 * Stores the amount paid and the change to return to the customer.
 */
public class CashPayment {
    private final double paidAmount;
    private final double change;

    /**
     * Creates a new instance representing a cash payment.
     * @param paidAmount The amount of money paid by the customer.
     * @param change The amount of change to return to the customer.
     */
    public CashPayment(double paidAmount, double change) {
        this.paidAmount = paidAmount;
        this.change = change;
    }

    /**
     * Gets the amount of money paid by the customer.
     * @return The paid amount.
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * Gets the amount of change in the payment.
     * @return The change amount.
     */
    public double getChangeAmount() {
        return change;
    }
}
