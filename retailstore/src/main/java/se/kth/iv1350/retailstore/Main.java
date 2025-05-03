package se.kth.iv1350.retailstore;

import se.kth.iv1350.retailstore.controller.Controller;
import se.kth.iv1350.retailstore.integration.SaleDTO;
import se.kth.iv1350.retailstore.view.View;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
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