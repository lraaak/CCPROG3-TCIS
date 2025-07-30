package com;

/**
 * The RaresBinder class represents a binder that can hold only cards with "RARE" or "LEGENDARY" rarity.
 * It includes functionality to add cards to the binder, calculate the sale value of the binder,
 * and sell the binder.
 */
public class RaresBinder extends Binders implements Sellable {

    /**
     * Initializes the RaresBinder with a given name and an empty card list.
     *
     * @param name - the name of the binder
     */
    public RaresBinder(String name) {
        super(name);
    }

    /**
     * Adds a card to the binder if it has a rarity of "RARE" or "LEGENDARY".
     * Otherwise, it outputs an error message.
     *
     * @param card - the card to be added to the binder
     */
    @Override
    public void addCard(Cards card) {
        if (card.getRarity().equalsIgnoreCase("RARE") || card.getRarity().equalsIgnoreCase("LEGENDARY")) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card. Must be either a Rare or Legendary card.");
        }
    }

    /**
     * Calculates the sale value of the binder by summing the value of all cards inside
     * and applying a 10% increase.
     *
     * @return double - the sale value of the binder
     */
    @Override
    public double getSaleValue() {
        double totalValue = 0;
        for (Cards c : this.getCard()) {
            totalValue += c.getFinalValue();
        }
        return totalValue * 1.10; // Apply 10% increase
    }

    /**
     * Sells the binder by adding its sale value to the total money in the TCIS system.
     */
    @Override
    public void sell() {
        TCIS.addMoney(getSaleValue());
    }
}
