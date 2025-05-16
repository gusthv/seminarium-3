package se.kth.iv1350.retailstore.view;

import se.kth.iv1350.retailstore.util.RevenueFileReader;
import se.kth.iv1350.retailstore.util.RevenueObserver;

/* 
 * Displays and maintains the total revenue accumulated.
 * Implementes RevenueObserver interface to receive revenue updates.
 * Initializes with previously stored total accumulated revenue and accumulates new revenues as they occur.
 */
public class TotalRevenueView implements RevenueObserver {
    private double totalRevenue;

    /**
     * Updates the total revenue with a new sale amount.
     * This method is called whenever a new sale is completed.
     *
     * @param revenue The amount to add to the total revenue
     */
    public TotalRevenueView() {
        this.totalRevenue = RevenueFileReader.getLatestTotalRevenue("total_revenue.txt");
    }

    /**
     * Gets the current total revenue.
     * 
     * @return The accumulated total revenue
     */
    @Override
    public void newRevenue(double revenue) {
        totalRevenue += revenue;
    }
}
