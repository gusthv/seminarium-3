package se.kth.iv1350.retailstore.view;

import se.kth.iv1350.retailstore.controller.Controller;
import se.kth.iv1350.retailstore.integration.ItemDTO;
import se.kth.iv1350.retailstore.integration.SaleDTO;
import se.kth.iv1350.retailstore.model.Receipt;

/**
 * This class represents the view of the application.
 * It simulates interaction with a cashier/user interface and
 * communicates with the controller to perform operations.
 */
public class View {
    private Controller controller;

    /**
     * Creates a new instance of the View.
     * @param controller The controller to communicate with.
     */
    public View(Controller controller) {
        this.controller = controller;
    }

    /**
     * Starts a new sale via the controller.
     */
    public void startSale() {
        this.controller.startSale();
    }

    /**
     * Scans an item and prints item information to the console.
     * @param itemID The ID of the item to scan.
     * @param quantity The quantity of the item.
     * @return The scanned item's DTO.
     */
    public ItemDTO scanItem(String itemID, int quantity){
        ItemDTO itemDTO = this.controller.scanItem(itemID, quantity);
        System.out.println("\nAdd " + quantity + " item(s) with item id " + itemID + ":");
        System.out.println(itemDTO.toString());
        return itemDTO;
    }

    /**
     * Sends the payment amount to the controller.
     * @param initialPaidAmount The amount paid by the customer.
     */
    public void pay(double initialPaidAmount){
        this.controller.pay(initialPaidAmount);
    }

    /**
     * Ends the current sale via the controller.
     * @return The completed SaleDTO.
     */
    public SaleDTO endSale() {
        final SaleDTO concludedSale = this.controller.endSale();
        return concludedSale;
    }

    /**
     * Retrieves and prints the receipt.
     * 
     * @return A string representing the printed receipt.
     */
    public String printReceipt() {
        Receipt receipt = controller.getReceipt();
        return receipt.toString();
    }
}
