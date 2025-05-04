package se.kth.iv1350.retailstore.model;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import se.kth.iv1350.retailstore.integration.*;

/**
 * Represents an ongoing or completed sale. Handles all logic for
 * adding items, calculating totals, handling payment, and generating a receipt.
 */
public class Sale {
    private SaleDTO saleDTO; // Data Transfer Object holding sale data
    private CashRegister cashRegister; // Reference to the store's cash register
    private ExternalAccountingSystem externalAccountingSystem; // System to log accounting info
    private CashPayment cashPayment; // Represents payment made
    private ExternalInventorySystem externalInventorySystem; // System for looking up item info

    /**
     * Creates a new sale with provided dependencies and initial sale data.
     * @param saleDTO Contains the state of the current sale.
     * @param cashRegister Reference to the cash register.
     * @param externalAccountingSystem Reference to accounting system.
     * @param externalInventorySystem Reference to inventory system.
     */
    public Sale(SaleDTO saleDTO, CashRegister cashRegister, ExternalAccountingSystem externalAccountingSystem, ExternalInventorySystem externalInventorySystem) {
        this.saleDTO = saleDTO;
        this.externalAccountingSystem = externalAccountingSystem;
        this.cashRegister = cashRegister;
        this.externalInventorySystem = externalInventorySystem;
    }

    /**
     * Finalizes the sale by calculating change, registering the payment,
     * updating the cash register, and sending information to external systems.
     * @param initialPayment The payment initiated by the customer.
     */
    public void pay(CashPayment initialPayment) {
        this.saleDTO = updateSaleInformation(initialPayment);
        final CashPayment finalPayment = new CashPayment(saleDTO.totalCost(), saleDTO.change());
        this.cashPayment = finalPayment;
        this.cashRegister.addPayment(finalPayment);
        this.externalAccountingSystem.sendSaleInformation(this.saleDTO, finalPayment);
    }

    /**
     * Updates the SaleDTO with payment info and calculates change.
     * @param initialPayment The customer's payment.
     * @return An updated SaleDTO marked as complete.
     */
    public SaleDTO updateSaleInformation(CashPayment initialPayment) {
        double paidAmount = initialPayment.getPaidAmount();
        double change = paidAmount - saleDTO.totalCost();
        this.saleDTO = createSaleDTO(
            this.saleDTO.itemsList(),
            this.saleDTO.totalCost(),
            this.saleDTO.totalVAT(),
            change,
            this.saleDTO.timeOfSale(),
            true
        );
        return this.saleDTO;
    }

    /**
     * Creates a new SaleDTO with the given data.
     * @param itemsList List of item IDs and quantities.
     * @param totalCost Total cost of the sale.
     * @param totalVAT Total VAT for the sale.
     * @param change Change to return to the customer.
     * @param timeOfSale The time the sale occurred.
     * @param isComplete Whether the sale is completed.
     * @return A new SaleDTO instance.
     */
    
    public SaleDTO createSaleDTO(ArrayList itemsList, double totalCost, double totalVAT, double change, java.time.LocalDateTime timeOfSale, boolean isComplete){
        return new SaleDTO(
            itemsList,
            totalCost,
            totalVAT,
            change,
            timeOfSale,
            isComplete
        );
    }

    /**
     * Adds an item and quantity to the sale. If the item already exists,
     * increases the quantity instead.
     * @param itemDTO The item to add.
     * @param quantity The quantity of the item.
     * @return Updated SaleDTO after the item is added.
     */
    public SaleDTO addItemToSale(ItemDTO itemDTO, int quantity) {
        final ArrayList itemsList = new ArrayList(saleDTO.itemsList());

        // Calculate total cost and VAT
        double currentTotalCost = saleDTO.totalCost();
        double currentItemCost = itemDTO.getItemPrice() * quantity;
        double newCurrentTotalCost = currentTotalCost + currentItemCost;

        double currentTotalVAT = saleDTO.totalVAT();
        double currentItemVAT = itemDTO.getItemVAT() * currentItemCost;
        double newCurrentTotalVAT = currentTotalVAT + currentItemVAT;

        // Loopa igenom itemsList där varje udda index innehåller antal (quantity) och varje jämnt index innehåller ett itemID. Vi stegar med 2 eftersom varje varupost består av två på varandra följande element: itemID (i) och quantity (i+1).
        for (int i = 0; i < itemsList.size(); i += 2){
            int oldQuantity = (int) itemsList.get(i + 1);
            if (itemDTO.getItemID().equals(itemsList.get(i))) {
                itemsList.set(i + 1, oldQuantity + quantity);
                this.saleDTO = createSaleDTO(itemsList, newCurrentTotalCost, newCurrentTotalVAT, 0.0, java.time.LocalDateTime.now(), false);
                return this.saleDTO;
            }
        }

        // Add new item if not found
        itemsList.add(itemDTO.getItemID());
        itemsList.add(quantity);
        this.saleDTO = createSaleDTO(itemsList, newCurrentTotalCost, newCurrentTotalVAT, 0.0, java.time.LocalDateTime.now(), false);
        return this.saleDTO;
    }

    /**
     * Finalizes the sale without applying any new changes.
     * @return The final SaleDTO.
     */
    public SaleDTO endSale(){
        return this.saleDTO;
    }

    /**
     * Returns a receipt for the finalized sale.
     * @return A receipt object with sale and payment info.
     */
    public Receipt getReceipt() {
        return new Receipt(this.saleDTO, this.cashPayment, this.externalInventorySystem);
    }
}
