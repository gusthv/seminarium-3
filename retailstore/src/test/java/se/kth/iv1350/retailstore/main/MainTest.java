package se.kth.iv1350.retailstore.main;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import se.kth.iv1350.retailstore.Main;
import se.kth.iv1350.retailstore.model.CashRegister;


import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    void testMainOutput() {
        PrintStream originalOut = System.out;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            Main.main(new String[]{});
        String output = outContent.toString();
        assertTrue(output.contains("Add 5 item(s) with item id 1005:"));
        assertTrue(output.contains("Item ID: 1005"));
        assertTrue(output.contains("Item name: guacamole"));
        } finally {
        System.setOut(originalOut);
    }
    }
}
