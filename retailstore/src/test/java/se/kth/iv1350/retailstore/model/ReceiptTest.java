package se.kth.iv1350.retailstore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import se.kth.iv1350.retailstore.integration.*;
import se.kth.iv1350.retailstore.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReceiptTest {
    private Receipt receipt;
    private ExternalInventorySystem inventory;
    private ExternalAccountingSystem accounting;
    private CashRegister cashRegister;

    @BeforeEach
    public void setUp() {
        ExternalInventorySystem.resetInstanceForTest();
        inventory = ExternalInventorySystem.getInstance();
        accounting = new ExternalAccountingSystem();
        cashRegister = new CashRegister();

        ArrayList<ItemAndQuantity> items = new ArrayList<ItemAndQuantity>();
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
        ArrayList<ItemAndQuantity> singleItem = new ArrayList<>();
        ItemDTO item = inventory.getItemDTO("1003");
        singleItem.add(new ItemAndQuantity(item, 1));

        double price = item.getItemPrice();
        double vat = item.getItemVAT() * price;

        SaleDTO sale = new SaleDTO(singleItem, price, vat, 0, LocalDateTime.now(), true);
        Receipt singleReceipt = new Receipt(sale, new CashPayment(price, 0), inventory);

        String receiptText = singleReceipt.toString();

        assertTrue(receiptText.contains("gräddfil"));
        assertTrue(receiptText.contains("25,00"));
    }
}
