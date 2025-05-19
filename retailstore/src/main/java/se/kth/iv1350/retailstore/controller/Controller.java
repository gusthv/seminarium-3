package se.kth.iv1350.retailstore.controller;

import se.kth.iv1350.retailstore.model.*;
import se.kth.iv1350.retailstore.integration.*;
import se.kth.iv1350.retailstore.util.error.*;
import se.kth.iv1350.retailstore.util.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller class handles communication between the view and the model.
 * It manages the sale process by coordinatng calls to different parts of the system,
 * such as inventory, accounting, and payment.
 */
public class Controller {
    private SaleDTO saleDTO;
    private Sale sale;
    private ExternalInventorySystem externalInventorySystem;
    private ExternalAccountingSystem externalAccountingSystem;
    private CashRegister cashRegister;
    private Printer printer;
    private final ErrorManager errorManager;
 
     /**
     * Creates a new instance of Controller and initializes all external systems.
     */
    public Controller(ExternalInventorySystem externalInventorySystem, CashRegister cashRegister, ExternalAccountingSystem externalAccountingSystem, Printer printer, ErrorManager errorManager) {
        this.externalInventorySystem = externalInventorySystem;
        this.cashRegister = cashRegister;
        this.externalAccountingSystem = externalAccountingSystem;
        this.printer = printer;
        this.errorManager = errorManager;
    }

    private List<RevenueObserver> revenueObservers = new ArrayList<>();

    public void addRevenueObserver(RevenueObserver obs) {
        revenueObservers.add(obs);
    }
    
    /**
    * Starts a new sale by initialicing a new {@link SaleDTO} and {@link Sale} instance.
    */
    public void startSale() {
        this.saleDTO = new SaleDTO(new ArrayList<ItemAndQuantity>(),
            0.0,
            0.0,
            0.0,
            java.time.LocalDateTime.now(),
            false);
        this.sale = new Sale(this.saleDTO, this.cashRegister, this.externalAccountingSystem, this.externalInventorySystem);
        for (RevenueObserver obs : revenueObservers) {
            this.sale.addRevenueObserver(obs);
        }
    }

    /**
     * Scans an item by its ID and quantity. Updates the current {@link SaleDTO}.
     * @param itemID  The identifier for the item.
     * @param quantity The number of units of the item being scanned.
     * @return The scanned {@link ItemDTO}.
     */
    public ItemDTO scanItem(String itemID, int quantity) {
        try {
        ItemDTO itemDTO = externalInventorySystem.getItemDTO(itemID);
        if (itemDTO == null) {
            throw new ItemNotFoundException("Item with ID " + itemID + " was not found in inventory.");
        }
            this.saleDTO = sale.addItemToSale(itemDTO, quantity);
            return itemDTO;
        }
        catch (ItemNotFoundException | InventoryErrorException e) {
            errorManager.notifyError(e);
            return null;
        }
    }

    /**
     * Handles payment by creating a {@link CashPayment} and passing it to the sale.
     * @param initialPaidAmount The amount paid by the customer.
     */
    public void pay(double initialPaidAmount){
        final double change = 0;
        final CashPayment initialPayment = new CashPayment(initialPaidAmount, change);
        sale.pay(initialPayment);
    }

    /**
     * Ends the sale and returns the final {@link SaleDTO}.
     * @return The completed {@link SaleDTO}.
     */
    public SaleDTO endSale(){
        final SaleDTO concludedSale = this.sale.endSale(); 
        return concludedSale; 
    }

    /**
     * Retrieves the receipt for the completed sale.
     * 
     * @return A {@link Receipt} representing the transaction.
     */
    public Receipt getReceipt() {
        Receipt receipt = sale.getReceipt();
        printer.printReceipt(receipt.toString());
        return receipt;
    }
}