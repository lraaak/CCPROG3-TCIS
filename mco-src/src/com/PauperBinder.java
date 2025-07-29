package com;

/**
 * Represents a PauperBinder, which is a special type of binder that can only contain
 * "NORMAL" or "UNCOMMON" cards and can be sold for a value calculated from the total
 * value of its cards. The PauperBinder class extends the {@link Binders} class and implements
 * the {@link Sellable} interface to allow selling functionality.
 */
public class PauperBinder extends Binders implements Sellable {

    /**
     * Constructor to initialize a PauperBinder with the given name.
     *
     * @param name The name of the PauperBinder.
     */
    public PauperBinder(String name) {
        super(name);
    }

    /**
     * Adds a card to the PauperBinder.
     * Only cards with the variant "NORMAL" or "UNCOMMON" can be added to this binder.
     *
     * @param card The card to add to the binder.
     */
    @Override
    public void addCard(Cards card) {
        // Check if the card's variant is "NORMAL" or "UNCOMMON"
        if (card.getVariant().equalsIgnoreCase("NORMAL") || card.getVariant().equalsIgnoreCase("UNCOMMON")) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card. Must be either a Normal or Uncommon card.");
        }
    }

    /**
     * Calculates the sale value of the PauperBinder.
     * The total value of the binder is calculated by summing the values of all cards
     * in the binder (no multiplier applied for this binder type).
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
        return totalValue; // No multiplier for PauperBinder sale value
    }

    /**
     * Sells the PauperBinder. Displays the binder's sale value and asks the user if they
     * want to sell it. If the user confirms, the sale value is added to the player's total money.
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
