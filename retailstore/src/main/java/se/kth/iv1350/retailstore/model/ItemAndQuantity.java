package se.kth.iv1350.retailstore.model;
import se.kth.iv1350.retailstore.integration.*;

/**
 * Represents an object in the SaleDTO's itemsList and contains the ItemDTO and it's respective quantity.
 */
public class ItemAndQuantity {
    private ItemDTO item;
    private int quantity;
    
    /**
     * Creates a new instance of <code>ItemAndQuantity</code> containing all relevant item details.
     *
     * @param item    The ItemDTO.
     * @param quantity  The quantity of the item.
     */
    public ItemAndQuantity(ItemDTO item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        }

    /**
     * Gets the item's ItemDTO.
     * 
     * @return The item's ItemDTO.
     */
    public ItemDTO getItem() {
        return item;
        }

    /**
     * Gets the item's quantity.
     * 
     * @return The quantity of the item.
     */
    public int getQuantity() {
        return quantity;
        }

    /**
     * Updates the item's quantity.
     * 
     * @return The new incremented quantity of the item.
     */
    public void increaseQuantity(int additionalQuantity) {
    this.quantity += additionalQuantity;
    }
}