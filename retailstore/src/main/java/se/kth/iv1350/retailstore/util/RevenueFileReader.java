package se.kth.iv1350.retailstore.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads total revenue data from a file. The file should contain double values with the most recent figure on the last line.
  */
public class RevenueFileReader {
    /* 
     * Retrieves the latest revenue figure from the last line
     * @param filePath The path to the file containing revenue data
     * @return The latest total revenue data entry as a double, or -1 if:
     *          - The file could not be read (IOException)
     *          - The last line doesnt contain a valid number (NumberFormatException)
     */
    public static double getLatestTotalRevenue(String filePath) {
        String lastLine = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
        } catch (IOException e) {
            System.err.println("Could not read revenue file: " + e.getMessage());
            return -1;
        }

        try {
            return Double.parseDouble(lastLine.trim());
        } catch (NumberFormatException e) {
            System.err.println("Could not parse revenue number: " + e.getMessage());
            return -1;
        }
    }
}
