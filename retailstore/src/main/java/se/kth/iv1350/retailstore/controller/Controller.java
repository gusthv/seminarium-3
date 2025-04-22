package se.kth.iv1350.retailstore.controller;

import se.kth.iv1350.retailstore.integration.Printer;
import se.kth.iv1350.retailstore.model.Sale;

public class Controller {
    private Sale sale;
    private Printer printer;

    public Controller() {
        this.printer = new Printer();
    }

    public void startSale() {
        this.sale = new Sale();
    }

    public void printReceipt() {
        printer.printReceipt(sale.getReceipt());
    }
}