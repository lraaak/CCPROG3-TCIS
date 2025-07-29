package com;

/**
 * Represents a SellableDeck, which is a type of deck that can be sold.
 * This class extends from the {@link Decks} class and implements the {@link Sellable} interface.
 * A SellableDeck allows cards to be added and calculates its sale value based on the total value
 * of the cards within it. The deck can then be sold, and the total value will be added to the user's money.
 */
public class SellableDeck extends Decks implements Sellable {

    /**
     * Constructor to initialize a SellableDeck with the given name.
     *
     * @param name The name of the SellableDeck.
     */
    public SellableDeck(String name){
        super(name);
    }

    /**
     * Calculates the sale value of the SellableDeck.
     * The sale value is determined by summing up the final value of all the cards in the deck.
     *
     * @return The total sale value of the deck.
     */
    public double getSaleValue(){
        double totalValue = 0;
        for (Cards c : this.getCard()){
            totalValue += c.getFinalValue();
        }
        return totalValue;
    }

    /**
     * Sells the SellableDeck, adding its sale value to the TCIS money pool.
     * Prompts the user for confirmation before proceeding with the sale.
     */
    @Override
    public void sell() {
        System.out.println(getName() + " has a value of " + getSaleValue());
        System.out.print("Would you like to sell it? ");
        if (Helper.confirmAction()){
            TCIS.addMoney(getSaleValue());  // Adds the sale value to the TCIS money pool.
            TCIS.showMoney();  // Displays the updated money after the sale.
        }
    }
}

    

