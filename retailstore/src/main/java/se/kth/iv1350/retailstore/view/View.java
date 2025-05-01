package se.kth.iv1350.retailstore.view;

import se.kth.iv1350.retailstore.controller.Controller;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void startSale() {
        this.controller.startSale();
    }

    public void scanItem(String itemID, int quantity){
        controller.scanItem(itemID, quantity);
    }
}
