package se.kth.iv1350.retailstore;

import se.kth.iv1350.retailstore.controller.Controller;
import se.kth.iv1350.retailstore.integration.ExternalAccountingSystem;
import se.kth.iv1350.retailstore.integration.ExternalInventorySystem;
import se.kth.iv1350.retailstore.integration.Printer;
import se.kth.iv1350.retailstore.integration.SaleDTO;
import se.kth.iv1350.retailstore.model.CashRegister;
import se.kth.iv1350.retailstore.view.View;

/**
 * The main entry point of the retail store application.
 * This class simulates a complete sale by using the system’s
 * Controller and View layers.
 */
public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();
        CashRegister cashRegister = new CashRegister();
        ExternalInventorySystem externalInventorySystem = new ExternalInventorySystem();
        ExternalAccountingSystem externalAccountingSystem = new ExternalAccountingSystem();

        Controller controller = new Controller(externalInventorySystem, cashRegister, externalAccountingSystem, printer);
        View view = new View(controller);

        view.startSale();
        view.scanItem("1001", 10);
        view.scanItem("1002", 5);
        view.scanItem("1003", 10);
        view.scanItem("1004", 5);
        view.scanItem("1005", 5);
        view.scanItem("1001", 5);

        view.pay(700.0);

        final SaleDTO endedSale = view.endSale();

        view.printReceipt();
    }
}
