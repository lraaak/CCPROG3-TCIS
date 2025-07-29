package com;

/**
 * The {@code Sellable} interface represents items that can be sold.
 * It defines the methods required for an item to be sold, including calculating its sale value
 * and performing the sale action.
 */
public interface Sellable {

    /**
     * Performs the sale of the item. This method should handle the logic of selling the item,
     * such as updating the inventory and adding the sale amount to the user's balance.
     */
    void sell();

    /**
     * Calculates and returns the sale value of the item.
     * This value is usually based on the item's base value, rarity, variant, or other factors.
     *
     * @return the sale value of the item.
     */
    double getSaleValue();
}
