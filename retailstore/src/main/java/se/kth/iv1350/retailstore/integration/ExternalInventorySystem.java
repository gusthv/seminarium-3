package se.kth.iv1350.retailstore.integration;

import se.kth.iv1350.retailstore.integration.ItemDTO;

import java.util.HashMap;
import java.util.Map;

public class ExternalInventorySystem {
    private final Map<String, ItemDTO> externalInventorySystem;

    public ExternalInventorySystem() {
        externalInventorySystem = new HashMap<>();
        populateInventory();
    }

    private void populateInventory() {
        externalInventorySystem.put("1001", new ItemDTO("1001", "tomat", "det är en tomat", 5, 12));
        externalInventorySystem.put("1002", new ItemDTO("1002", "chips", "svenska chips", 30, 12));
        externalInventorySystem.put("1003", new ItemDTO("1003", "gräddfil", "det är en gräddfil", 25, 12));
        externalInventorySystem.put("1004", new ItemDTO("1004", "dipp", "det är en dipp", 10, 12));
        externalInventorySystem.put("1005", new ItemDTO("1005", "guacamole", "det är en guacamole", 30, 12));
    }

    public ItemDTO getItemInfo(String itemID){
        return externalInventorySystem.get(itemID);
    }
}