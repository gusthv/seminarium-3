package se.kth.iv1350.retailstore;

import se.kth.iv1350.retailstore.controller.Controller;
import se.kth.iv1350.retailstore.view.View;
import se.kth.iv1350.retailstore.integration.SaleDTO;
import se.kth.iv1350.retailstore.model.CashRegister;
import se.kth.iv1350.retailstore.integration.*;

/**
 * The main entry point of the retail store application.
 * This class simulates a complete sale by using the system’s
 * Controller and View layers.
 */
public class Main {
    public static void main(String[] args) {
        ExternalInventorySystem externalInventorySystem = new ExternalInventorySystem();
        CashRegister cashRegister = new CashRegister();
        ExternalAccountingSystem externalAccountingSystem = new ExternalAccountingSystem();
        Printer printer = new Printer();
        // Create the controller that manages the application logic
        Controller controller = new Controller(externalInventorySystem, cashRegister, externalAccountingSystem, printer);

        // Create the view that simulates interaction with the cashier
        View view = new View(controller);

        // Simulate a sale sequence
        view.startSale(); // Start a new sale

        // Simulate scanning several items
        view.scanItem("1001", 10);
        view.scanItem("1002", 5);
        view.scanItem("1003", 10);
        view.scanItem("1004", 5);
        view.scanItem("1005", 5);
        view.scanItem("1001", 5); // Scanning an already added item to test quantity update

        // Simulate payment
        view.pay(700.0);

        // End the sale
        final SaleDTO endedSale = view.endSale();

        // Print the receipt
        view.printReceipt();
    }
}
