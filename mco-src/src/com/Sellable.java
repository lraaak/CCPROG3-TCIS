package com;

/**
 * The Sellable interface defines methods for objects that can be sold.
 * Implementing classes must provide functionality for selling an item and determining its sale value.
 */
public interface Sellable {

    /**
     * Sells the item, usually by adding the sale value to a user's balance or performing similar actions.
     * This method defines the behavior that happens when the item is sold.
     */
    void sell();

    /**
     * Calculates the sale value of the item.
     *
     * @return double - the sale value of the item
     */
    double getSaleValue();
}
