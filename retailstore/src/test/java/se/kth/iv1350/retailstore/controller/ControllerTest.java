package se.kth.iv1350.retailstore.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.retailstore.integration.ExternalAccountingSystem;
import se.kth.iv1350.retailstore.integration.ExternalInventorySystem;
import se.kth.iv1350.retailstore.integration.ItemDTO;
import se.kth.iv1350.retailstore.integration.SaleDTO;
import se.kth.iv1350.retailstore.integration.Printer;
import se.kth.iv1350.retailstore.model.CashRegister;
import se.kth.iv1350.retailstore.model.Receipt;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller controller;

@BeforeEach
public void setUp() {
    ExternalInventorySystem inventory = new ExternalInventorySystem();
    CashRegister cashRegister = new CashRegister();
    ExternalAccountingSystem accounting = new ExternalAccountingSystem();
    Printer printer = new Printer();

    controller = new Controller(inventory, cashRegister, accounting, printer);
    controller.startSale();
}


    @Test
    public void testStartSaleInitializesSaleDTO() {
        SaleDTO result = controller.endSale();
        assertNotNull(result, "SaleDTO should not be null after starting a sale and ending it.");
    }

    @Test
    public void testScanItemReturnsCorrectItem() {
        ItemDTO item = controller.scanItem("1001", 1); // förutsätter att "1001" finns
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
        assertNotNull(receipt, "Receipt should be created after payment.");
    }
}



