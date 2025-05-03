package se.kth.iv1350.retailstore.model;

import se.kth.iv1350.retailstore.integration.ExternalInventorySystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import se.kth.iv1350.retailstore.integration.*;

import java.time.LocalTime;
import java.util.ArrayList;

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

        // Create a complete sale
        ArrayList<Object> items = new ArrayList<>();
        items.add("1001"); // tomat
        items.add(2); // quantity
        items.add("1002"); // chips
        items.add(1); // quantity

        // Create initial sale DTO
        SaleDTO initialSale = new SaleDTO(items, 0, 0, 0, LocalTime.now(), false);

        // Create sale and add items
        Sale sale = new Sale(initialSale, cashRegister, accounting, inventory);
        sale.addItemToSale(inventory.getItemDTO("1001"), 2);
        sale.addItemToSale(inventory.getItemDTO("1002"), 1);

        // Complete payment (50 SEK for 40 SEK total)
        sale.pay(new CashPayment(50.00, 0));

        // Get the receipt
        receipt = sale.getReceipt();
    }

    @Test
    public void testContainsItemInformation() {
        String receiptText = receipt.toString();
        assertTrue(receiptText.contains("tomat"));
        assertTrue(receiptText.contains("chips"));
        assertTrue(receiptText.contains("2")); // quantity
        assertTrue(receiptText.contains("1")); // quantity
    }

    @Test
    public void testContainsCorrectTotals() {
        String receiptText = receipt.toString();
        // Verify calculations using inventory prices
        assertTrue(receiptText.contains("40,00")); // total (5*2 + 30)
        assertTrue(receiptText.contains("2,40")); // VAT
        assertTrue(receiptText.contains("50,00")); // cash paid (40 + 10 change)
        assertTrue(receiptText.contains("10,00")); // change
    }

    @Test
    public void testSingleItemReceipt() {
        ArrayList<Object> singleItem = new ArrayList<>();
        singleItem.add("1003"); // gräddfil
        singleItem.add(1);

        double price = inventory.getItemDTO("1003").getItemPrice();
        SaleDTO sale = new SaleDTO(singleItem, price, price * 0.06, 0, LocalTime.now(), true);
        Receipt singleReceipt = new Receipt(sale, new CashPayment(price, 0), inventory);

        String receiptText = singleReceipt.toString();
        assertTrue(receiptText.contains("gräddfil"));
        assertTrue(receiptText.contains("25,00")); // price from inventory
    }
}

/*
 * public class ReceiptTest {
    private Sale sale;

    @BeforeEach
    public void setUp() {
        sale = new Sale();
        ItemDTO milk = new ItemDTO(1, "Milk", "1 liter of milk", 2100, 12.0); // 21.00 SEK
        ItemDTO bread = new ItemDTO(2, "Bread", "Whole wheat bread", 3000, 6.0); // 30.00 SEK
        sale.addItem(milk);
        sale.addItem(milk); // Add milk twice
        sale.addItem(bread);
    }

    @Test
    public void testReceiptContainsHeaderAndFooter() {
        Receipt receipt = new Receipt(sale, 10000);
        String result = receipt.printReceipt();
        assertTrue(result.contains("Begin receipt"), "Receipt should contain 'Begin receipt'");
        assertTrue(result.contains("End receipt"), "Receipt should contain 'End receipt'");
    }

    @Test
    public void testReceiptIncludesItemsAndTotals() {
        Receipt receipt = new Receipt(sale, 10000);
        String result = receipt.printReceipt();
        assertTrue(result.contains("Milk"), "Receipt should contain item 'Milk'");
        assertTrue(result.contains("Bread"), "Receipt should contain item 'Bread'");
        assertTrue(result.contains("Total:"), "Receipt should contain 'Total'");
        assertTrue(result.contains("Vat:"), "Receipt should contain 'Vat'");
        assertTrue(result.contains("Cash:"), "Receipt should contain 'Cash'");
        assertTrue(result.contains("Change:"), "Receipt should contain 'Change'");
    }

    @Test
    public void testReceiptCalculatesChangeCorrectly() {
        int cash = 10000; // 100.00 SEK
        Receipt receipt = new Receipt(sale, cash);
        String result = receipt.printReceipt();
    
        int expectedTotal = sale.getTotal();
        int expectedChange = cash - expectedTotal;
        String expectedChangeStr = String.valueOf(expectedChange / 100.0);
    
        assertTrue(result.contains(expectedChangeStr), "Receipt should contain correct change: " + expectedChangeStr);
    }

    @Test
    public void testReceiptFormattingHasTimeOfSale() {
        Receipt receipt = new Receipt(sale, 10000);
        String result = receipt.printReceipt();
        assertTrue(result.contains("Time of sale:"), "Receipt should include the timestamp");
    }
}
 */