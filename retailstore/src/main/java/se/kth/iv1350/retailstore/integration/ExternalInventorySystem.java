package se.kth.iv1350.retailstore.integration;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an external system that provides item information used in the
 * retail process.
 * It stores items in a HashMap using their item ID as the key.
 */
public class ExternalInventorySystem {
    private final Map<String, ItemDTO> externalInventorySystem;

    /**
     * Creates a new instance of the external inventory system and populates it with
     * example items.
     */
    public ExternalInventorySystem() {
        externalInventorySystem = new HashMap<>();
        populateInventory();
    }

    /**
     * Populates the inventory system with predefined <code>ItemDTO</code> objects.
     * These are mock entries for simulation and testing purposes.
     */
    private void populateInventory() {
        externalInventorySystem.put("1001", new ItemDTO("1001", "tomat", "det är en tomat", 5.0, 0.06));
        externalInventorySystem.put("1002", new ItemDTO("1002", "chips", "svenska chips", 30.0, 0.06));
        externalInventorySystem.put("1003", new ItemDTO("1003", "gräddfil", "det är en gräddfil", 25.0, 0.06));
        externalInventorySystem.put("1004", new ItemDTO("1004", "dipp", "det är en dipp", 10.0, 0.06));
        externalInventorySystem.put("1005", new ItemDTO("1005", "guacamole", "det är en guacamole", 30.0, 0.06));
    }

    /**
     * Retrieves the item associated with the given item ID from the external
     * inventory system.
     *
     * @param itemID The unique identifier for the requested item.
     * @return The <code>ItemDTO</code> corresponding to the given item ID, or
     *         <code>null</code> if not found.
     */
    public ItemDTO getItemDTO(String itemID){
        return externalInventorySystem.get(itemID);
    }
}