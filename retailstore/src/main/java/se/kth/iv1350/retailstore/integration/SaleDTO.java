package se.kth.iv1350.retailstore.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import se.kth.iv1350.retailstore.model.*;

/**
 * Represents a Data Transfer Object (DTO) for a sale, encapsulating all the
 * necessary
 * information related to a completed or ongoing sale. This includes the list of
 * items,
 * total cost, VAT, change, time of sale, and sale status.
 */
public class SaleDTO {
    private List<ItemAndQuantity> itemsList;
    public final double totalCost;
    public final double totalVAT;
    public final double change;
    public final java.time.LocalDateTime timeOfSale;
    public final boolean isComplete;

    /**
     * Creates a new SaleDTO object with all relevant sale data.
     *
     * @param singleItem  List of items and their quantities, alternating [itemID,
     *                   quantity, ...].
     * @param totalCost  Total cost of all items including VAT.
     * @param totalVAT   Total value-added tax for the sale.
     * @param change     Change to return to the customer.
     * @param timeOfSale The time at which the sale occurred.
     * @param isComplete Flag indicating whether the sale is finalized.
     */
    public SaleDTO(
            List<ItemAndQuantity> singleItem, double totalCost, double totalVAT, double change,
            java.time.LocalDateTime timeOfSale, boolean isComplete) {
        this.itemsList = new ArrayList<ItemAndQuantity>(singleItem);
        this.totalCost = totalCost;
        this.totalVAT = totalVAT;
        this.change = change;
        this.timeOfSale = timeOfSale;
        this.isComplete = isComplete;
    }

    /**
     * Gets the list of items and their quantities.
     * 
     * @return A list of itemID and quantity indexed at i and i + 1 respectively.
     */
    public List<ItemAndQuantity> itemsList() {
        return Collections.unmodifiableList(itemsList);
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
    public java.time.LocalDateTime timeOfSale() {
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
}
