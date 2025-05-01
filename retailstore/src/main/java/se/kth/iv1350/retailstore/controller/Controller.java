package se.kth.iv1350.retailstore.controller;

import se.kth.iv1350.retailstore.model.Sale;
import se.kth.iv1350.retailstore.integration.*;

public class Controller {
    private Sale sale;
    private ExternalInventorySystem externalInventorySystem;
 
    public Controller() {
        this.externalInventorySystem = new ExternalInventorySystem();
    }
    
    public void startSale() {
        this.sale = new Sale();
    }

    public void scanItem(String itemID, int quantity){
        ItemDTO item = externalInventorySystem.getItemInfo(itemID);
        System.out.println(item.toString());
    }
}