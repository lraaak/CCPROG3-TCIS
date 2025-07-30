package com;

/**
 * The CollectorBinder class represents a type of binder that holds trading cards.
 * This binder only accepts "LEGENDARY" or "RARE" cards and ensures that the variant
 * is not "NORMAL".
 */
public class CollectorBinder extends Binders {

    /**
     * Initializes the CollectorBinder with a given name and an empty card list.
     *
     * @param name - the name of the binder
     */
    public CollectorBinder(String name) {
        super(name);
    }

    /**
     * Adds a card to the CollectorBinder only if the card is either "LEGENDARY" or "RARE"
     * and has a variant that is not "NORMAL".
     *
     * @param card - the card to be added to the binder
     */
    public void addCard(Cards card) {
        if ((card.getRarity().equalsIgnoreCase("LEGENDARY") || card.getRarity().equalsIgnoreCase("RARE")) &&
                !card.getVariant().equalsIgnoreCase("NORMAL")) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card. Must be either a Legendary or Rare card and not NORMAL.");
        }
    }
}
