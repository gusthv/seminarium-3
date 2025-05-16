package se.kth.iv1350.retailstore.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TotalRevenueFileOutput implements RevenueObserver {
    private double totalRevenue = 0;
    private static final String LOG_FILE = "total_revenue.txt";

    public TotalRevenueFileOutput() {
        this.totalRevenue = RevenueFileReader.getLatestTotalRevenue(LOG_FILE);
        if (this.totalRevenue < 0) {
            this.totalRevenue = 0;
        }
    }

    @Override
    public void newRevenue(double revenue) {
        totalRevenue += revenue;
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println("[" + java.time.LocalDateTime.now() + "] Total revenue in SEK: \n" + totalRevenue);
        } catch (IOException e) {
            System.err.println("Could not write to revenue log file: " + e.getMessage());
        }
    }
}
