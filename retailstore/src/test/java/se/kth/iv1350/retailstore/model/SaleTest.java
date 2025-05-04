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
        // initialisering av alla objekt som behövs för testet
        saleDTO = new SaleDTO(new ArrayList<>(), 0.0, 0.0, 0.0, LocalDateTime.now(), false);
        cashRegister = new CashRegister();
        externalInventorySystem = new ExternalInventorySystem();
        externalAccountingSystem = new ExternalAccountingSystem();
        
        // skapar en testartikel
        itemDTO = new ItemDTO("123", "Test Item", "This is a test item", 10.0, 0.06);

        // skapar ett nytt försäljningsobjekt
        sale = new Sale(saleDTO, cashRegister, externalAccountingSystem, externalInventorySystem);
    }

    @Test
    public void testAddItemToSale() {
        // testar att lägga till en vara till försäljningen
        sale.addItemToSale(itemDTO, 2);
        SaleDTO updatedSaleDTO = sale.endSale();

        assertEquals(2, updatedSaleDTO.itemsList().size(), "Item should be added to the sale");
        assertEquals(20,0, updatedSaleDTO.totalCost(), "Total cost should be 20.0 SEK after adding two items");
    }

    @Test
    public void testPay() {
        // testar betalning
        sale.addItemToSale(itemDTO, 2);
        CashPayment payment = new CashPayment(25.0, 5.0); // betalning på 25 SEK, förväntad växel 5 SEK
        sale.pay(payment);

        SaleDTO updatedSaleDTO = sale.endSale();
        assertTrue(updatedSaleDTO.isComplete(), "Sale should be marked as complete after payment");
        assertEquals(5,0, updatedSaleDTO.change(), "Change should be 5 SEK after payment");
    }

    @Test
    public void testGetReceipt() {
        // testar att generera ett kvitto
        sale.addItemToSale(itemDTO, 1);
        CashPayment payment = new CashPayment(15.0, 5.0); // betalning på 15 SEK, växel 5 SEK
        sale.pay(payment);
        
        Receipt receipt = sale.getReceipt();
        assertNotNull(receipt, "Receipt should not be null");
        assertEquals(15,0, payment.getPaidAmount(), "Total paid amount in the receipt should be 15 SEK");
    }

    @Test
    public void testSaleWithMultipleItems() {
        // testar försäljning med flera artiklar
        ItemDTO anotherItem1 = new ItemDTO("101", "Another Item1", "Another test item", 15.0, 0.06);
        ItemDTO anotherItem2 = new ItemDTO("102", "Another Item2", "Another test item", 15.0, 0.06);
        
        sale.addItemToSale(anotherItem1, 2); // lägg till en artikel
        sale.addItemToSale(anotherItem2, 2); // lägg till en andra artikel
        
        SaleDTO updatedSaleDTO = sale.endSale();
        
        assertEquals(2, (updatedSaleDTO.itemsList().size() / 2), "Items list should contain 2 entries"); // vi delar på 2 för att listan innehåller artikeln på index i och dess kvantitet på i + 1
        assertEquals(60,0, updatedSaleDTO.totalCost(), "Total cost should be 60 SEK after adding both items");
    }
}
