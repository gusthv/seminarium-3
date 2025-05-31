package se.kth.iv1350.retailstore.view;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.retailstore.controller.Controller;
import se.kth.iv1350.retailstore.integration.ExternalAccountingSystem;
import se.kth.iv1350.retailstore.integration.ExternalInventorySystem;
import se.kth.iv1350.retailstore.integration.Printer;
import se.kth.iv1350.retailstore.model.CashRegister;
import se.kth.iv1350.retailstore.util.error.ErrorManager;
import se.kth.iv1350.retailstore.util.error.ItemNotFoundException;
import se.kth.iv1350.retailstore.model.Receipt;

public class ViewTest {
    private Receipt receipt;
    private ExternalInventorySystem externalInventorySystem;
    private ExternalAccountingSystem externalAccountingSystem;
    private CashRegister cashRegister;
    private View view;
    private Controller controller;
    private ByteArrayOutputStream outContent;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        ExternalInventorySystem.resetInstanceForTest();
        externalInventorySystem = ExternalInventorySystem.getInstance();
        externalAccountingSystem = new ExternalAccountingSystem();
        cashRegister = new CashRegister();
        Printer printer = new Printer();
        ErrorManager errorManager = new ErrorManager();

        controller = new Controller(externalInventorySystem, cashRegister, externalAccountingSystem, printer);
        view = new View(controller, errorManager);
        controller.startSale();

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreTerminal() {
        System.setOut(originalOut);
    }

    @Test
    void testViewOutput(){
        view.scanItem("1005", 5);
        String output = outContent.toString();
        assertTrue(output.contains("Item ID: 1005"));
        assertTrue(output.contains("Item name: guacamole"));
    }

    @Test
    void testDisplaysError() {
        view.handleError(new ItemNotFoundException("Item with ID 1009 was not found in inventory."));
        assertTrue(outContent.toString().contains("The item could not be found. Please check the item ID and try again."));
    }
}