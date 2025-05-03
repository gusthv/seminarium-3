package se.kth.iv1350.retailstore.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExternalInventorySystemTest {
    private ExternalInventorySystem inventorySystem;

    @BeforeEach
    void setUp() {
        inventorySystem = new ExternalInventorySystem();
    }

    @Test
    void testGetExistingItem() {
        ItemDTO item = inventorySystem.getItemDTO("1002");
        assertNotNull(item, "Item with ID 1002 should exist");
        assertEquals("chips", item.getItemName(), "Item name should be 'chips'");
        assertEquals(30.0, item.getItemPrice(), 0.01, "Item price should be 30.0");
    }

    @Test
    void testGetNonExistingItem() {
        ItemDTO item = inventorySystem.getItemDTO("9999");
        assertNull(item, "Item with ID 9999 should not exist");
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
