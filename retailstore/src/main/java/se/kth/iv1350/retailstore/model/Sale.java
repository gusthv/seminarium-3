package se.kth.iv1350.retailstore.model;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import se.kth.iv1350.retailstore.integration.*;
import se.kth.iv1350.retailstore.util.*;

/**
 * Represents an ongoing or completed sale. Handles all logic for
 * adding items, calculating totals, handling payment, and generating a receipt.
 */
public class Sale {
    private SaleDTO saleDTO;
    private CashRegister cashRegister; 
    private ExternalAccountingSystem externalAccountingSystem; 
    private CashPayment cashPayment; 
    private ExternalInventorySystem externalInventorySystem;

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
     * List of registered observers that will be notified when new revenue is
     * generated.
     * Initialized as empty ArrayList.
     */
    private final List<RevenueObserver> revenueObservers = new ArrayList<>();

    /**
     * Registers a new observer to receive revenue updates.
     * The observer will be notified whenever new revenue is generated.
     * @param observer The RevenueObserver implementation to register
     */
    public void addRevenueObserver(RevenueObserver observer) {
        revenueObservers.add(observer);
    }

    /**
     * Notifies all registered observers about new revenue.
     * All observer's newRevenue() methods will be called with the given revenue amount.
     * @param revenue The revenue amount to notify observers about
     */
    private void notifyRevenueObservers(double revenue) {
        for (RevenueObserver obs : revenueObservers) {
            obs.newRevenue(revenue);
        }
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
        notifyRevenueObservers(saleDTO.totalCost());
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
    
    public SaleDTO createSaleDTO(List<ItemAndQuantity> itemsList, double totalCost, double totalVAT, double change, java.time.LocalDateTime timeOfSale, boolean isComplete){
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
        final List<ItemAndQuantity> itemsList = new ArrayList<>(saleDTO.itemsList());

        double currentTotalCost = saleDTO.totalCost();
        double currentItemCost = itemDTO.getItemPrice() * quantity;
        double newCurrentTotalCost = currentTotalCost + currentItemCost;

        double currentTotalVAT = saleDTO.totalVAT();
        double currentItemVAT = itemDTO.getItemVAT() * currentItemCost;
        double newCurrentTotalVAT = currentTotalVAT + currentItemVAT;

        boolean isFound = false;

        for (ItemAndQuantity lineItem : itemsList) {
            if (itemDTO.getItemID().equals(lineItem.getItem().getItemID())) {
                lineItem.increaseQuantity(quantity);
                this.saleDTO = createSaleDTO(itemsList, newCurrentTotalCost, newCurrentTotalVAT, 0.0, java.time.LocalDateTime.now(), false);
                isFound = true;
            }
        }
        if (!isFound) {
            itemsList.add(new ItemAndQuantity(itemDTO, quantity));
            this.saleDTO = createSaleDTO(itemsList, newCurrentTotalCost, newCurrentTotalVAT, 0.0, java.time.LocalDateTime.now(), false);
        }
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
