package se.kth.iv1350.retailstore.integration;

import se.kth.iv1350.retailstore.util.error.*;

import java.util.HashMap;
import java.util.Map;

import se.kth.iv1350.retailstore.util.error.InventoryErrorException;

/**
 * A singleton representing an external inventory system that stores available
 * items.
 * The inventory is initialized with example items upon first instantiation.
 */
public class ExternalInventorySystem {
    private static ExternalInventorySystem instance;
    private final Map<String, ItemDTO> externalInventorySystem;

    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the inventory system and populates it with example items.
     */
    private ExternalInventorySystem() {
        externalInventorySystem = new HashMap<>();
        populateInventory();
    }

    /**
     * Provides the global access point to the singleton instance of the inventory
     * system.
     * If no instance exists yet, one is created.
     * 
     * @return The singleton instance of ExternalInventorySystem
     */
    public static ExternalInventorySystem getInstance() {
        if (instance == null)
        {
            instance = new ExternalInventorySystem();
        }
        return instance;
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
     * @throws InventoryErrorException If a specific item identifier is fetched as a simulation.
     */
    public ItemDTO getItemDTO(String itemID) throws InventoryErrorException {
    if (itemID.equals("2001")) {
        throw new InventoryErrorException("Inventory is malfunctioning");
    }
    return externalInventorySystem.get(itemID);
    }
}