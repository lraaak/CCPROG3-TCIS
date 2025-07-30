package com;

/**
 * The PauperBinder class represents a binder specifically designed for holding
 * "Common" and "Uncommon" rarity trading cards. This binder is also sellable, meaning
 * it can be sold for its total card value.
 */
public class PauperBinder extends Binders implements Sellable {

    /**
     * Initializes the PauperBinder with the specified name and an empty card list.
     *
     * @param name - the name of the PauperBinder
     */
    public PauperBinder(String name) {
        super(name);
    }

    /**
     * Adds a card to the binder only if the card's rarity is either "COMMON" or "UNCOMMON".
     * If the card does not meet these conditions, it is not added to the binder.
     *
     * @param card - the card to be added to the binder
     */
    @Override
    public void addCard(Cards card) {
        if (card.getRarity().equalsIgnoreCase("COMMON") || card.getRarity().equalsIgnoreCase("UNCOMMON")) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card. Must be either a Normal or Uncommon card.");
        }
    }

    /**
     * Calculates the total sale value of the cards in the PauperBinder.
     * The value is the sum of the final values of all cards in the binder.
     *
     * @return double - the total sale value of the cards in the binder
     */
    @Override
    public double getSaleValue() {
        double totalValue = 0;
        for (Cards c : this.getCard()) {
            totalValue += c.getFinalValue();
        }
        return totalValue;
    }

    /**
     * Sells the PauperBinder by adding its sale value to the total money in the system.
     */
    @Override
    public void sell() {
        TCIS.addMoney(getSaleValue());
    }
}
