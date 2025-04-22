package se.kth.iv1350.retailstore.model;

public class Sale {
    private String receipt;

    public Sale() {
        this.receipt = "mitt galnaste köp: totalt = 99 sek";
    }

    public String getReceipt() {
        return receipt;
    }
}