package se.kth.iv1350.retailstore.controller;

import java.util.ArrayList;

import se.kth.iv1350.retailstore.integration.ExternalAccountingSystem;
import se.kth.iv1350.retailstore.integration.ExternalInventorySystem;
import se.kth.iv1350.retailstore.integration.ItemDTO;
import se.kth.iv1350.retailstore.integration.Printer;
import se.kth.iv1350.retailstore.integration.SaleDTO;
import se.kth.iv1350.retailstore.model.CashPayment;
import se.kth.iv1350.retailstore.model.CashRegister;
import se.kth.iv1350.retailstore.model.Receipt;
import se.kth.iv1350.retailstore.model.Sale;

/**
 * The controller class handles communication between the view and the model.
 * It manages the sale process by coordinatng calls to different parts of the system,
 * such as inventory, accounting, and payment.
 */
public class Controller {
    private SaleDTO saleDTO;
    private Sale sale;
    private final ExternalInventorySystem externalInventorySystem;
    private final ExternalAccountingSystem externalAccountingSystem;
    private final CashRegister cashRegister;
    private final Printer printer;
 
     /**
     * Creates a new instance of Controller and initializes all external systems.
     */
    public Controller() {
        this.externalInventorySystem = new ExternalInventorySystem();
        this.cashRegister = new CashRegister();
        this.externalAccountingSystem = new ExternalAccountingSystem();
        this.printer = new Printer();
    }
    
    /**
    * Starts a new sale by initialicing a new {@link SaleDTO} and {@link Sale} instance.
    */
    public void startSale() {
        this.saleDTO = new SaleDTO(new ArrayList<>(),
            0.0,
            0.0,
            0.0,
            java.time.LocalTime.now(),
            false);
        this.sale = new Sale(this.saleDTO, this.cashRegister, this.externalAccountingSystem, this.externalInventorySystem);
    }

    /**
     * Scans an item by its ID and quantity. Updates the current {@link SaleDTO}.
     * @param itemID  The identifier for the item.
     * @param quantity The number of units of the item being scanned.
     * @return The scanned {@link ItemDTO}.
     */
    public ItemDTO scanItem(String itemID, int quantity){
        ItemDTO itemDTO = externalInventorySystem.getItemDTO(itemID);
        this.saleDTO = sale.addItemToSale(itemDTO, quantity);
        return itemDTO;
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
     * @return A {@link Receipt} representing the transaction.
     */
    public Receipt getReceipt(){
        Receipt receipt = sale.getReceipt();
        printer.printReceipt(receipt.toString());
        return receipt;
    }
}