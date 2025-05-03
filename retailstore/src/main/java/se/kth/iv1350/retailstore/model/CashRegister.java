package se.kth.iv1350.retailstore.model;

/**
 * Represents the store's cash register.
 * Keeps track of the total amount of money collected during sales.
 */
public class CashRegister {
    // Total amount of cash currently in the register
    private double amountInRegister;

    /**
     * Creates a new cash register and initializes the amount to zero.
     */
    public CashRegister() {
        this.amountInRegister = 0;
    }

    /**
     * Gets the amount of cash in the cash register.
     *
     * @return The amount of cash in register.
     */
    public double getAmountInRegister() {
        return this.amountInRegister;
    }

    /**
     * Adds a payment to the register's total.
     * @param finalPayment The final cash payment to add.
     */
    public void addPayment(CashPayment finalPayment) {
        this.amountInRegister += finalPayment.getPaidAmount();
    }
}
