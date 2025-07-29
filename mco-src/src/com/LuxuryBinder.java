package com;

// TODO: NEED TO ADD A SELLING METHOD
/**
 * Represents a LuxuryBinder, which is a special type of binder that can hold cards
 * and can be sold for a custom price. The LuxuryBinder class extends the {@link Binders} class
 * and implements the {@link Sellable} interface to allow selling functionality.
 */
public class LuxuryBinder extends Binders implements Sellable {

    private double customPrice; // Custom price for the binder

    /**
     * Constructor to initialize a LuxuryBinder with the given name.
     * The custom price is initialized to -1, indicating no custom price is set.
     *
     * @param name The name of the LuxuryBinder.
     */
    public LuxuryBinder(String name) {
        super(name);
        customPrice = -1;
    }

    /**
     * Adds a card to the LuxuryBinder.
     * Only cards with a variant other than "NORMAL" can be added to this binder.
     *
     * @param card The card to add to the binder.
     */
    public void addCard(Cards card) {
        if (!card.getVariant().equalsIgnoreCase("NORMAL")) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card. Must NOT be a NORMAL card.");
        }
    }

    /**
     * Sets a custom price for the LuxuryBinder.
     * The custom price must be greater than the minimum price of all cards in the binder.
     * If the custom price is valid, it is multiplied by 1.10 to calculate the final sale value.
     *
     * @param price The custom price to be set for the binder.
     */
    public void setCustomPrice(double price) {
        double minPrice = 0;
        // Calculate the total value of the cards in the binder
        for (Cards c : this.getCard()) {
            minPrice += c.getFinalValue();
        }

        if (minPrice > price) {
            customPrice = price * 1.10; // Multiply the custom price by 1.10 (10% increase) for sale value
        } else {
            System.out.println("Custom Price must be greater than the minimum price of the cards in the binder.");
        }
    }

    /**
     * Gets the sale value of the LuxuryBinder.
     * If a custom price is set, it returns that price.
     * Otherwise, it calculates the total value of the cards in the binder and applies a 10% increase.
     *
     * @return The sale value of the binder.
     */
    public double getSaleValue() {
        if (customPrice != -1) {
            return customPrice; // Return custom price if set
        } else {
            double totalValue = 0;
            // Calculate the total value of the cards in the binder
            for (Cards c : this.getCard()) {
                totalValue += c.getFinalValue();
            }
            return totalValue * 1.10; // Multiply total value by 1.10 (10% increase)
        }
    }

    /**
     * Sells the LuxuryBinder. Displays the binder's sale value and asks the user if they want to sell it.
     * If the user confirms, the sale value is added to the player's total money.
     */
    @Override
    public void sell() {
        System.out.println(getName() + " has a value of " + getSaleValue());
        System.out.print("Would you like to sell it? ");
        if (Helper.confirmAction()) {
            TCIS.addMoney(getSaleValue()); // Add the sale value to the player's money
        }
    }
}
