package se.kth.iv1350.retailstore.integration;

import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.retailstore.util.error.InventoryErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExternalInventorySystemTest {
    private ExternalInventorySystem inventorySystem;

    @BeforeEach
    void setUp() {
        ExternalInventorySystem.resetInstanceForTest();
        inventorySystem = ExternalInventorySystem.getInstance();
    }

    @Test
    void testGetExistingItem() {
        ItemDTO item = inventorySystem.getItemDTO("1002");
        assertNotNull(item, "Item with ID 1002 should exist");
        assertEquals("chips", item.getItemName(), "Item name should be 'chips'");
        assertEquals(30.0, item.getItemPrice(), 0.01, "Item price should be 30.0");
    }

    @Test
    void testGetNonExistingItemIsNull() {
        ItemDTO item = inventorySystem.getItemDTO("9999");
        assertNull(item, "Item with ID 9999 should not exist");
    }

    @Test
    void InventoryNotWorkingThrowsError() throws InventoryErrorException {
        assertThrows(InventoryErrorException.class, () -> {
            inventorySystem.getItemDTO("2001");
        }, "Expected InventoryErrorException when fetching item ID '2001'");
    }

    @Test
    void testGetItemDescription() {
        ItemDTO item = inventorySystem.getItemDTO("1001");
        assertEquals("det är en tomat", item.getItemInfo(), "Description should match for tomat");
    }

    @Test
    void testVATForItem() {
        ItemDTO item = inventorySystem.getItemDTO("1005");
        assertEquals(0.06, item.getItemVAT(), 0.001, "VAT should be 0.06 for guacamole");
    }
}
