package se.kth.iv1350.retailstore.model;

import se.kth.iv1350.retailstore.integration.*;
import se.kth.iv1350.retailstore.model.CashPayment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a printed receipt for a completed sale. The receipt includes
 * details such as purchased items, time of sale, <code>totalCost</code>,
 * <code>totalVAT</code>, <code>payment</code>, and <code>change</code>.
 */
public class Receipt {
    private final SaleDTO saleDTO;
    private final CashPayment cashPayment;
    private final ExternalInventorySystem externalInventorySystem;

    /**
     * Creates a new Receipt instance with all required information to print a complete receipt.
     * @param saleDTO The sale data including item list, totals, VAT, etc.
     * @param cashPayment The payment made by the customer.
     * @param externalInventorySystem Used to retrieve item details for the receipt.
     */
    public Receipt(SaleDTO saleDTO, CashPayment cashPayment, ExternalInventorySystem externalInventorySystem) {
        this.saleDTO = saleDTO;
        this.cashPayment = cashPayment;
        this.externalInventorySystem = externalInventorySystem;
    }

    /**
     * Generates a formatted string of all items in the sale with name, quantity,
     * unit price, and total item price.
     * @return A string representing the list of items on the receipt.
     */
    public String generatedItemList() {
        String myStr = "";
        List<ItemAndQuantity> itemsList = this.saleDTO.itemsList();
        for (int i = 0; i < itemsList.size(); i++) {
            ItemAndQuantity item = itemsList.get(i);
            ItemDTO itemDTO = item.getItem();
            int quantity = item.getQuantity();
            myStr += String.format("%-15s %3d x %6.2f  %8.2f SEK\n",
                itemDTO.getItemName(), quantity, itemDTO.getItemPrice(), (itemDTO.getItemPrice() * quantity));
        }
        return myStr;
    }

    /**
     * Returns the full receipt as a formatted string, including sale time, item list,
     * totals, VAT, paid amount, and change.
     * @return A formatted string representing the complete receipt.
     */
    public String toString() {
        return "\n--------------Begin Receipt-------------\n" +
               "Time of Sale: " + this.saleDTO.timeOfSale().toString().replace("T", " ").split("\\.")[0] +
               "\n\n" + generatedItemList() +
               String.format("\n%-20s %10.2f SEK", "Total:", this.saleDTO.totalCost()) +
               "\nVAT: " + this.saleDTO.totalVAT() +
               String.format("\n\n%-20s %10.2f SEK", "Cash:", this.cashPayment.getPaidAmount() + this.saleDTO.change()) +
               String.format("\n%-20s %10.2f SEK", "Change:", this.saleDTO.change()) +
               "\n---------------End Receipt--------------\n";
    }
}
