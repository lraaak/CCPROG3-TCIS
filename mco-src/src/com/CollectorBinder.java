package com;

/**
 * Represents a CollectorBinder, a special type of binder that can only hold "RARE"
 * and "LEGENDARY" cards whose variants are not "NORMAL". This class extends the
 * {@link Binders} class and does not implement the {@link Sellable} interface since
 * CollectorBinders cannot be sold.
 */
public class CollectorBinder extends Binders {

    /**
     * Constructor to initialize a CollectorBinder with the given name.
     *
     * @param name The name of the CollectorBinder.
     */
    public CollectorBinder(String name) {
        super(name);
    }

    /**
     * Adds a card to the CollectorBinder.
     * Only cards with rarity "RARE" or "LEGENDARY" and a variant other than "NORMAL"
     * can be added to this binder.
     *
     * @param card The card to be added to the binder.
     */
    public void addCard(Cards card) {
        // Check if the card is either "RARE" or "LEGENDARY" and not "NORMAL"
        if ((card.getRarity().equalsIgnoreCase("LEGENDARY") || card.getRarity().equalsIgnoreCase("RARE")) &&
                !card.getVariant().equalsIgnoreCase("NORMAL")) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card. Must be either a Legendary or Rare card and not NORMAL.");
        }
    }
}
