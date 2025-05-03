package se.kth.iv1350.retailstore.model;

import java.time.LocalDate;
import java.util.ArrayList;

import se.kth.iv1350.retailstore.integration.ExternalInventorySystem;
import se.kth.iv1350.retailstore.integration.ItemDTO;
import se.kth.iv1350.retailstore.integration.SaleDTO;

/**
 * Represents a printed receipt for a completed sale. The receipt includes
 * details such as purchased items, time of sale, <code>totalCost</code>,
 * <code>totalVAT</code>, <code>payment</code>, and <code>change</code>.
 */
public class Receipt {
    private final SaleDTO saleDTO; // Data Transfer Object containing all sale details
    private final CashPayment cashPayment; // Object representing the payment details
    private final ExternalInventorySystem externalInventorySystem; // Used to retrieve item info by item ID

    /**
     * Creates a new Receipt instance with all required information to print a complete receipt.
     * @param saleDTO The sale data including item list, totals, VAT, etc.
     * @param cashPayment The payment made by the customer.
     * @param itemDTO A sample ItemDTO, currently not directly used.
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
     *
     * @return A string representing the list of items on the receipt.
     */
    public String generatedItemList() {
        String myStr = "";
        ArrayList<Object> itemsList = this.saleDTO.itemsList();
        for (int i = 0; i < itemsList.size(); i += 2) {
            String itemID = (String) itemsList.get(i);
            int quantity = (int) itemsList.get(i + 1);
            ItemDTO itemDTO = externalInventorySystem.getItemDTO(itemID);
            myStr += String.format("%-15s %3d x %6.2f SEK  %8.2f SEK\n",
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
               "Time of Sale: " + LocalDate.now() + " " + this.saleDTO.timeOfSale() +
               "\n\n" + generatedItemList() +
               String.format("\n%-20s %10.2f SEK", "Total:", this.saleDTO.totalCost()) +
               String.format("\n%-20s %10.2f SEK", "VAT:", this.saleDTO.totalVAT()) +
               String.format("\n\n%-20s %10.2f SEK", "Cash:", this.cashPayment.getPaidAmount() + this.saleDTO.change()) +
               String.format("\n%-20s %10.2f SEK", "Change:", this.saleDTO.change()) +
               "\n---------------End Receipt--------------\n";
    }
}
