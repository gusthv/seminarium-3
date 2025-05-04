package se.kth.iv1350.retailstore.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.retailstore.integration.ExternalAccountingSystem;
import se.kth.iv1350.retailstore.integration.ExternalInventorySystem;
import se.kth.iv1350.retailstore.integration.SaleDTO;

public class ReceiptTest {
    private Receipt receipt;
    private ExternalInventorySystem inventory;
    private ExternalAccountingSystem accounting;
    private CashRegister cashRegister;

    @BeforeEach
    public void setUp() {
        inventory = new ExternalInventorySystem();
        accounting = new ExternalAccountingSystem();
        cashRegister = new CashRegister();

        ArrayList<Object> items = new ArrayList<>();
        items.add("1001");
        items.add(2);
        items.add("1002");
        items.add(1);

        SaleDTO initialSale = new SaleDTO(items, 0, 0, 0, LocalDateTime.now(), false);

        Sale sale = new Sale(initialSale, cashRegister, accounting, inventory);
        sale.addItemToSale(inventory.getItemDTO("1001"), 2);
        sale.addItemToSale(inventory.getItemDTO("1002"), 1);

        sale.pay(new CashPayment(50.00, 0));
        receipt = sale.getReceipt();
    }

    @Test
    public void testContainsItemInformation() {
        String receiptText = receipt.toString();
        assertTrue(receiptText.contains("tomat"));
        assertTrue(receiptText.contains("chips"));
        assertTrue(receiptText.contains("2"));
        assertTrue(receiptText.contains("1"));
    }

    @Test
    public void testContainsCorrectTotals() {
        String receiptText = receipt.toString();
        assertTrue(receiptText.contains("40,00"));
        assertTrue(receiptText.contains("2.4"));
        assertTrue(receiptText.contains("50,00"));
        assertTrue(receiptText.contains("10,00"));
    }

@Test
public void testSingleItemReceipt() {
    ArrayList<Object> singleItem = new ArrayList<>();
    singleItem.add("1003");
    singleItem.add(1);

    double price = inventory.getItemDTO("1003").getItemPrice();
    SaleDTO sale = new SaleDTO(singleItem, price, price * 0.06, 0, LocalDateTime.now(), true);
    Receipt singleReceipt = new Receipt(sale, new CashPayment(price, 0), inventory);

    String receiptText = singleReceipt.toString();

    assertTrue(receiptText.contains("gräddfil"));
    assertTrue(receiptText.contains("25,00"));
}

}
