package se.kth.iv1350.retailstore;

import se.kth.iv1350.retailstore.controller.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        System.out.println("yoooo vi har en main!");
        controller.startSale();
    }
}