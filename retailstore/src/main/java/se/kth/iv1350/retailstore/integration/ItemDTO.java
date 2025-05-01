package se.kth.iv1350.retailstore.integration;

public class ItemDTO {
    private final String itemID;
    private final String itemName;
    private final String itemInfo;
    private final int itemPrice;
    private final int itemVAT;

    public ItemDTO(String itemID, String itemName, String itemInfo, int itemPrice, int itemVAT)  {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemInfo = itemInfo;
        this.itemPrice = itemPrice;
        this.itemVAT = itemVAT;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getItemVAT() {
        return itemVAT;
    }

    public String toString() {
        return("\nitemID: " + itemID + "\nitemName: " + itemName + "\nitemInfo: " + itemInfo + "\nitemPrice: " + itemPrice + "\nitemVAT: " + itemVAT);
    }
}
