package com;

/**
 * The LuxuryBinder class represents a special type of binder that holds trading cards.
 * This binder only allows cards with variants other than "NORMAL" to be added.
 * It also supports setting a custom price for the binder and calculating its sale value.
 */
public class LuxuryBinder extends Binders implements Sellable {

    private double customPrice;

    /**
     * Initializes the LuxuryBinder with the specified name and sets the custom price to -1 by default.
     *
     * @param name - the name of the binder
     */
    public LuxuryBinder(String name) {
        super(name);
        customPrice = -1;
    }

    /**
     * Adds a card to the LuxuryBinder if the card's variant is not "NORMAL".
     * If the card is "NORMAL", it will not be added to the binder.
     *
     * @param card - the card to be added to the binder
     */
    @Override
    public void addCard(Cards card) {
        if (!card.getVariant().equalsIgnoreCase("NORMAL")) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card. Must NOT be a NORMAL card.");
        }
    }

    /**
     * Sets a custom price for the binder. The custom price must be greater than the minimum price
     * of the cards currently in the binder. If valid, the price is set, otherwise, an error message is shown.
     *
     * @param price - the custom price to set for the binder
     */
    public void setCustomPrice(double price) {
        // Ensure there are cards in the binder before setting the price
        if (this.getCard().isEmpty()) {
            System.out.println("Cannot set custom price. No cards in the binder.");
            return;
        }

        double minPrice = getCardValue();

        if (minPrice < price) {
            this.customPrice = price;
            System.out.println("Binder price has been changed to $" + price);
        } else {
            System.out.println("Custom Price must be greater than the minimum price of the cards in the binder ($" + minPrice + ").");
        }
    }

    /**
     * Returns the sale value of the binder, either based on the custom price or the value of the cards in the binder.
     * A 10% increase is applied to the sale value.
     *
     * @return the sale value of the binder
     */
    public double getSaleValue() {
        if (customPrice != -1) {
            return customPrice * 1.10;
        } else {
            double totalValue = getCardValue();
            return totalValue * 1.10;
        }
    }

    /**
     * Calculates the total value of the cards in the binder.
     *
     * @return the total value of the cards in the binder
     */
    public double getCardValue() {
        double totalValue = 0;
        for (Cards c : this.getCard()) {
            totalValue += c.getFinalValue();
        }
        return totalValue;
    }

    /**
     * Sells the binder, adding its sale value to the system's total money.
     * The binder is removed after being sold.
     */
    @Override
    public void sell() {
        TCIS.addMoney(getSaleValue());
    }
}
