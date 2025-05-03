package se.kth.iv1350.retailstore.integration;

/**
 * Represents a Data Transfer Object (DTO) for an item in the retail system.
 * This class holds all relevant data about an item such as its ID, name, price,
 * VAT, and description.
 */
public class ItemDTO {
    private final String itemID;
    private final String itemName;
    private final String itemInfo;
    private final double itemPrice;
    private final double itemVAT;

    /**
     * Creates a new instance of <code>ItemDTO</code> containing all relevant item
     * details.
     *
     * @param itemID    The unique identifier for the item.
     * @param itemName  The name of the item.
     * @param itemInfo  A description or additional information about the item.
     * @param itemPrice The price of the item, excluding VAT.
     * @param itemVAT   The VAT rate for the item (as a decimal, e.g., 0.06 for 6%).
     */
    public ItemDTO(String itemID, String itemName, String itemInfo, double itemPrice, double itemVAT)  {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemInfo = itemInfo;
        this.itemPrice = itemPrice;
        this.itemVAT = itemVAT;
    }

    /**
     * Gets the unique ID of the item.
     *
     * @return The item's ID.
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * Gets the name of the item.
     *
     * @return The item's name.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the description or additional info of the item.
     *
     * @return The item's description.
     */
    public String getItemInfo() {
        return itemInfo;
    }

    /**
     * Gets the price of the item (excluding VAT).
     *
     * @return The item's price.
     */
    public double getItemPrice() {
        return itemPrice;
    }

    /**
     * Gets the VAT rate applied to the item.
     *
     * @return The item's VAT rate as a decimal.
     */
    public double getItemVAT() {
        return itemVAT;
    }

    /**
     * Returns a formatted string representation of the item for display purposes.
     *
     * @return A string containing the item's ID, name, price, VAT, and description.
     */
    public String toString() {
        return("Item ID: " + itemID + "\nItem name: " + itemName + "\nItem cost: " + itemPrice + " SEK" + "\nVAT: " + itemVAT * 100 + "%" + "\nItem description: " + itemInfo);
    }
}
