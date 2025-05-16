package se.kth.iv1350.retailstore.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.retailstore.integration.ExternalAccountingSystem;
import se.kth.iv1350.retailstore.integration.ExternalInventorySystem;
import se.kth.iv1350.retailstore.integration.ItemDTO;
import se.kth.iv1350.retailstore.integration.SaleDTO;

public class SaleTest {

    private Sale sale;
    private SaleDTO saleDTO;
    private CashRegister cashRegister;
    private ExternalInventorySystem externalInventorySystem;
    private ExternalAccountingSystem externalAccountingSystem;
    
    private ItemDTO itemDTO;
    
    @BeforeEach
    public void setUp() {
        saleDTO = new SaleDTO(new ArrayList<>(), 0.0, 0.0, 0.0, LocalDateTime.now(), false);
        cashRegister = new CashRegister();
        ExternalInventorySystem.resetInstanceForTest();
        externalInventorySystem = ExternalInventorySystem.getInstance();
        externalAccountingSystem = new ExternalAccountingSystem();

        itemDTO = new ItemDTO("123", "Test Item", "This is a test item", 10.0, 0.06);

        sale = new Sale(saleDTO, cashRegister, externalAccountingSystem, externalInventorySystem);
    }

    @Test
    public void testAddItemToSale() {
        sale.addItemToSale(itemDTO, 2);
        SaleDTO updatedSaleDTO = sale.endSale();

        assertEquals(1, updatedSaleDTO.itemsList().size(), "Item should be added to the sale");
        assertEquals(20,0, updatedSaleDTO.totalCost(), "Total cost should be 20.0 SEK after adding two items");
    }

    @Test
    public void testPay() {
        sale.addItemToSale(itemDTO, 2);
        CashPayment payment = new CashPayment(25.0, 5.0);
        sale.pay(payment);

        SaleDTO updatedSaleDTO = sale.endSale();
        assertTrue(updatedSaleDTO.isComplete(), "Sale should be marked as complete after payment");
        assertEquals(5,0, updatedSaleDTO.change(), "Change should be 5 SEK after payment");
    }

    @Test
    public void testGetReceipt() {
        sale.addItemToSale(itemDTO, 1);
        CashPayment payment = new CashPayment(15.0, 5.0);
        sale.pay(payment);
        
        Receipt receipt = sale.getReceipt();
        assertNotNull(receipt, "Receipt should not be null");
        assertEquals(15,0, payment.getPaidAmount(), "Total paid amount in the receipt should be 15 SEK");
    }

    @Test
    public void testSaleWithMultipleItems() {
        ItemDTO anotherItem1 = new ItemDTO("101", "Another Item1", "Another test item", 15.0, 0.06);
        ItemDTO anotherItem2 = new ItemDTO("102", "Another Item2", "Another test item", 15.0, 0.06);
        
        sale.addItemToSale(anotherItem1, 2);
        sale.addItemToSale(anotherItem2, 2);
        
        SaleDTO updatedSaleDTO = sale.endSale();
        
        assertEquals(2, (updatedSaleDTO.itemsList().size()), "Items list should contain 2 entries");
        assertEquals(60,0, updatedSaleDTO.totalCost(), "Total cost should be 60 SEK after adding both items");
    }
}
