package se.kth.iv1350.retailstore.integration;

import java.util.ArrayList;

/**
 * Represents a Data Transfer Object (DTO) for a sale, encapsulating all the
 * necessary
 * information related to a completed or ongoing sale. This includes the list of
 * items,
 * total cost, VAT, change, time of sale, and sale status.
 */
public class SaleDTO {
    public final ArrayList itemsList; // List containing alternating ItemDTO IDs and quantities
    public final double totalCost; // Total cost of the sale excluding change
    public final double totalVAT; // Total VAT for the sale
    public final double change; // The calculated change after payment
    public final java.time.LocalTime timeOfSale; // The time when the sale was made
    public final boolean isComplete; // Whether the sale is finalized

    /**
     * Creates a new SaleDTO object with all relevant sale data.
     *
     * @param itemsList  List of items and their quantities, alternating [itemID,
     *                   quantity, ...].
     * @param totalCost  Total cost of all items including VAT.
     * @param totalVAT   Total value-added tax for the sale.
     * @param change     Change to return to the customer.
     * @param timeOfSale The time at which the sale occurred.
     * @param isComplete Flag indicating whether the sale is finalized.
     */
    public SaleDTO(ArrayList itemsList, double totalCost, double totalVAT, double change,
            java.time.LocalTime timeOfSale, boolean isComplete) {
        this.itemsList = itemsList;
        this.totalCost = totalCost;
        this.totalVAT = totalVAT;
        this.change = change;
        this.timeOfSale = timeOfSale;
        this.isComplete = isComplete;
    }

    /**
     * Gets the list of items and their quantities.
     * 
     * @return A list of itemID and quantity pairs.
     */
    public ArrayList itemsList() {
        return itemsList;
    }

    /**
     * Gets the total cost of the sale.
     * 
     * @return Total cost.
     */
    public double totalCost() {
        return totalCost;
    }

    /**
     * Gets the total VAT of the sale.
     * 
     * @return Total VAT.
     */
    public double totalVAT() {
        return totalVAT;
    }

    /**
     * Gets the change to be returned to the customer.
     * 
     * @return Change amount.
     */
    public double change() {
        return change;
    }

    /**
     * Gets the time when the sale occurred.
     * 
     * @return Sale time.
     */
    public java.time.LocalTime timeOfSale() {
        return timeOfSale;
    }

    /**
     * Indicates whether the sale is complete.
     * 
     * @return true if the sale is complete, false otherwise.
     */
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Returns a string representation of the sale data.
     * 
     * @return A formatted string with all sale fields.
     */
    public String toString() {
        return ("\nitemsList: " + itemsList +
                "\ntotalCost: " + totalCost +
                "\ntotalVAT: " + totalVAT +
                "\nchange: " + change +
                "\nisComplete: " + isComplete);
    }
}
