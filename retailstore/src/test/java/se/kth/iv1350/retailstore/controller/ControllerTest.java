package se.kth.iv1350.retailstore.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.retailstore.integration.ExternalAccountingSystem;
import se.kth.iv1350.retailstore.integration.ExternalInventorySystem;
import se.kth.iv1350.retailstore.integration.ItemDTO;
import se.kth.iv1350.retailstore.integration.SaleDTO;
import se.kth.iv1350.retailstore.integration.Printer;
import se.kth.iv1350.retailstore.model.CashRegister;
import se.kth.iv1350.retailstore.model.ItemAndQuantity;
import se.kth.iv1350.retailstore.model.Receipt;
import se.kth.iv1350.retailstore.util.error.*;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller controller;

@BeforeEach
public void setUp() {
    ExternalInventorySystem inventory = new ExternalInventorySystem();
    CashRegister cashRegister = new CashRegister();
    ExternalAccountingSystem accounting = new ExternalAccountingSystem();
    Printer printer = new Printer();
    ErrorManager errorManager = new ErrorManager();

    controller = new Controller(inventory, cashRegister, accounting, printer, errorManager);
    controller.startSale();
}

    @Test
    public void testStartSaleInitializesSaleDTO() {
        SaleDTO result = controller.endSale();
        assertTrue(result instanceof SaleDTO, "SaleDTO should be of type SaleDTO after starting a sale and ending it.");
        assertNotNull(result.itemsList(), "The items array should be initialized");
        assertEquals(0.0, result.totalCost(), "Total cost should be 0 initially");
        assertEquals(0.0, result.totalVAT(), "Total VAT should be 0 initially");
        assertEquals(0.0, result.change(), "Change should be 0 initially");
    }

    @Test
    public void testScanItemReturnsCorrectItem() {
        ItemDTO item = controller.scanItem("1001", 1);
        assertEquals("1001", item.getItemID(), "Scanned item ID should match requested ID.");
    }

    @Test
    public void testPaymentDoesNotThrowException() {
        assertDoesNotThrow(() -> controller.pay(100.0), "Payment should not throw any exception.");
    }

    @Test
    public void testGetReceiptAfterPaymentReturnsNonNull() {
        controller.scanItem("1001", 1);
        controller.pay(100.0);
        Receipt receipt = controller.getReceipt();
        assertTrue(receipt instanceof Receipt, "Receipt should be created after payment.");

        String receiptStr = receipt.toString();
        assertTrue(receiptStr.contains("100"), "Receipt should contain payment of 100");
        assertTrue(receiptStr.contains("tomat"), "Receipt should contain item 'tomat'");
        assertTrue(receiptStr.contains("5"), "Receipt should contain price of 'tomat'");
    }

    @Test
    void testGetNonExistingItemThrowsError() {
        assertThrows(ItemNotFoundException.class, () -> {
        controller.scanItem("1008", 2);
        }, "Should throw error: Item with ID 1008 was not found in invetory.");
    }
}



