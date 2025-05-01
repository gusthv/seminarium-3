package se.kth.iv1350.retailstore.integration;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class SaleDTO {
    public final List<Pair<ItemDTO, Integer>> itemsList;
    public final double totalCost;
    public final double totalVAT;
    public final double change;
    public final java.time.LocalTime timeOfSale;
    public final boolean isComplete;

    public SaleDTO(List<Pair<ItemDTO, Integer>> itemsList, double totalCost, double totalVAT, double change, java.time.LocalTime timeOfSale, boolean isComplete) {
        this.itemsList = itemsList;
        this.totalCost = totalCost;
        this.totalVAT = totalVAT;
        this.change = change;
        this.timeOfSale = timeOfSale;
        this.isComplete = isComplete;
    }

    public List<Pair<ItemDTO, Integer>> itemsList() {
        return itemsList;
    }
    public double totalCost() {
        return totalCost;
    }
    public double totalVAT() {
        return totalCost;
    }
    public double change() {
        return totalCost;
    }
    public java.time.LocalTime timeOfSale() {
        return timeOfSale;
    }
    public boolean isComplete() {
        return isComplete;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("itemsList:\n");
        for (Pair<ItemDTO, Integer> pair : itemsList) {
            sb.append(pair.getLeft()).append(", quantity: ").append(pair.getRight()).append("\n");
        }
        sb.append("tatalCost: ").append(totalCost)
          .append("\ntotalVAT: ").append(totalVAT)
          .append("\nvhange: ").append(change)
          .append("\ntimeOfSale: ").append(timeOfSale)
          .append("\nisComplete: ").append(isComplete);
        return sb.toString();
    }
}
