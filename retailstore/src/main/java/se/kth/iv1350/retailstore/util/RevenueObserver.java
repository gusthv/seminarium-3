package se.kth.iv1350.retailstore.util;

/**
 * Interface for observers interested in total revenue updates.
 */
public interface RevenueObserver {
    /**
     * Called when a sale has been completed and revenue has changed.
     * 
     * @param totalRevenue The total revenue after the new sale.
     */
    void newRevenue(double totalRevenue);
}
