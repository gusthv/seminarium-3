package se.kth.iv1350.retailstore.integration;
import se.kth.iv1350.retailstore.model.*;

/**
 * Represents the external system that receives sale information. The method <code>sendSaleInformation</code> takes 
 * <code>saleDTO</code> and <code>finalPayment</code> parameters
 */
public class ExternalAccountingSystem {
    /**
     * Send sale information to the external accounting system.
     * 
     * @param saleDTO      The sale data transfer object to update.
     * @param finalPayment The payment details for the completed payment.
     */
    public void sendSaleInformation(SaleDTO saleDTO, CashPayment finalPayment){
    }
}
