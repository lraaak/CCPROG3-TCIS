package com;

/**
 * SellableDeck class extends the Decks class and implements the Sellable interface.
 * It represents a deck of cards that can be sold. The sale value of the deck
 * is the total value of all its cards, and selling the deck will add the value
 * to the TCIS money.
 */
public class SellableDeck extends Decks implements Sellable {

    /**
     * Constructor to create a new SellableDeck with the given name.
     *
     * @param name the name of the SellableDeck.
     */
    public SellableDeck(String name) {
        super(name);
    }

    /**
     * Calculates the sale value of the deck. The sale value is the sum of the
     * final values of all the cards in the deck.
     *
     * @return the total sale value of the deck.
     */
    public double getSaleValue() {
        double totalValue = 0;
        // Iterate through the cards and sum their final values
        for (Cards c : this.getCard()) {
            totalValue += c.getFinalValue();
        }
        return totalValue;
    }

    /**
     * Sells the deck and adds the sale value to the total money in the TCIS.
     * It invokes the {@link TCIS#addMoney(double)} method to add the money earned
     * from the sale to the system's total money.
     */
    @Override
    public void sell() {
        // Add the sale value of the deck to the TCIS money
        TCIS.addMoney(getSaleValue());
    }
}

    

