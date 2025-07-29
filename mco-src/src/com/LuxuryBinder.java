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
    public void setCustomPrice(double price){
        double minPrice = getCardValue();

        if (minPrice < price){
            if (Helper.confirmAction()) {
                this.customPrice = price;
                System.out.println("Binder price has been changed to "+ price);
            }
        }
        else {
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
     public double getSaleValue(){
        if (customPrice != -1){
            return customPrice * 1.10;
        }
        else {
            double totalValue = getCardValue();
            return totalValue * 1.10;
        }
    }


    public double getCardValue(){
        double totalValue = 0;
        for (Cards c : this.getCard()){
            totalValue += c.getFinalValue();
        }
        return totalValue;
    }
    /**
     * Sells the LuxuryBinder. 
     */
    @Override
    public void sell() {
            TCIS.addMoney(getSaleValue());
    }
}
