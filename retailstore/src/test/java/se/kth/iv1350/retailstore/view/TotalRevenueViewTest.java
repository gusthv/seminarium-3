package se.kth.iv1350.retailstore.view;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TotalRevenueViewTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private TotalRevenueView revenueView;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        revenueView = new TotalRevenueView();
    }

    @AfterEach
    void restoreTerminal() {
        System.setOut(originalOut);
    }

     @Test
    void testTotalRevenueOutput(){
        revenueView.newRevenue(100.0);
        String output = outContent.toString();
        String normalizedOutput = output.replaceAll("\\r?\\n", " ");
        assertTrue(normalizedOutput.matches(".*Total revenue: \\d+\\,\\d{2} SEK.*"));
    }

    @Test
    void testTotalRevenueAccumulates() {
        revenueView.newRevenue(100.0);
        revenueView.newRevenue(100.0);
        String output = outContent.toString();
        String[] lines = output.split("\n");
        assertTrue(lines[lines.length - 2].contains("Total revenue:"));
        assertTrue(lines[lines.length - 1].contains("Total revenue:"));
    }
 }