package se.kth.iv1350.retailstore;

import se.kth.iv1350.retailstore.controller.Controller;
import se.kth.iv1350.retailstore.view.View;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        View view = new View(controller);
        view.startSale();
        view.scanItem("1001", 4);
        view.scanItem("1002", 3);
        view.scanItem("1003", 2);
        view.scanItem("1004", 1);
        view.scanItem("1005", 100);
    }
}