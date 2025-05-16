package se.kth.iv1350.retailstore;

import se.kth.iv1350.retailstore.controller.Controller;
import se.kth.iv1350.retailstore.view.View;
import se.kth.iv1350.retailstore.model.CashRegister;
import se.kth.iv1350.retailstore.integration.*;
import se.kth.iv1350.retailstore.util.TotalRevenueFileOutput;
import se.kth.iv1350.retailstore.util.error.*;

/**
 * The main entry point of the retail store application.
 * This class simulates a complete sale by using the system’s
 * Controller and View layers.
 */
public class Main {
    public static void main(String[] args) {
        ExternalInventorySystem externalInventorySystem = ExternalInventorySystem.getInstance();
        CashRegister cashRegister = new CashRegister();
        ExternalAccountingSystem externalAccountingSystem = new ExternalAccountingSystem();
        Printer printer = new Printer();
        ErrorManager errorManager = new ErrorManager();
        errorManager.setStrategy(new FileLogStrategy());
        Controller controller = new Controller(externalInventorySystem, cashRegister, externalAccountingSystem, printer, errorManager);

        View view = new View(controller);
        errorManager.addObserver(view);

        TotalRevenueFileOutput fileOutput = new TotalRevenueFileOutput();
        controller.addRevenueObserver(fileOutput);

        view.startSale();
        view.scanItem("1001", 10);
        view.scanItem("1002", 5);
        view.scanItem("1003", 10);
        view.scanItem("1004", 5);
        view.scanItem("1005", 5);
        view.scanItem("1001", 5);
        view.scanItem("2001", 5);
        view.scanItem("1009", 2);
        view.scanItem("1001", 5);
        view.scanItem("1006", 7);
        view.scanItem("1001", 5);
        view.scanItem("1003", 10);
        view.pay(700.0);

        final SaleDTO endedSale = view.endSale();
        view.printReceipt();
    }
}
