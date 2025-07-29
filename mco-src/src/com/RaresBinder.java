package com;

// TODO: NEED TO ADD A SELLING METHOD

/**
 * Represents a RaresBinder, which is a special type of binder that can only contain
 * "RARE" or "LEGENDARY" cards and can be sold for a value calculated from the total
 * value of its cards. The RaresBinder class extends the {@link Binders} class and implements
 * the {@link Sellable} interface to allow selling functionality.
 */
public class RaresBinder extends Binders implements Sellable {

    /**
     * Constructor to initialize a RaresBinder with the given name.
     *
     * @param name The name of the RaresBinder.
     */
    public RaresBinder(String name) {
        super(name);
    }

    /**
     * Adds a card to the RaresBinder.
     * Only cards with the rarity "RARE" or "LEGENDARY" can be added to this binder.
     *
     * @param card The card to add to the binder.
     */
    @Override
    public void addCard(Cards card) {
        // Check if the card is of type "RARE" or "LEGENDARY"
        if (card.getRarity().equalsIgnoreCase("RARE") || card.getRarity().equalsIgnoreCase("LEGENDARY")) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card. Must be either a Rare or Legendary card.");
        }
    }

    /**
     * Calculates the sale value of the RaresBinder.
     * The total value of the binder is calculated by summing the values of all cards
     * in the binder and multiplying by 1.10 (10% increase).
     *
     * @return The sale value of the binder.
     */
    @Override
    public double getSaleValue() {
        double totalValue = 0;
        // Calculate the total value of all cards in the binder
        for (Cards c : this.getCard()) {
            totalValue += c.getFinalValue();
        }
        return totalValue * 1.10; // Apply a 10% increase to the total value
    }

    /**
     * Sells the RaresBinder. Displays the binder's sale value and asks the user if they
     * want to sell it. If the user confirms, the sale value is added to the player's total money.
     */
    @Override
    public void sell() {
        TCIS.addMoney(getSaleValue());
    }
}
