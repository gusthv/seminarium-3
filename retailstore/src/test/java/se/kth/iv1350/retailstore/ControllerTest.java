package se.kth.iv1350.retailstore.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    @Test
    void testStartSale() {
        Controller controller = new Controller();
        controller.startSale();
        assertNotNull(controller, "neeej");
    }

    @Test
    void testPrintReceipt() {
        Controller controller = new Controller();
        controller.startSale();
        assertDoesNotThrow(() -> controller.printReceipt(), "neeej");
    }
}