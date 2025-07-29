package com;

public class CollectorBinder extends Binders {


    /**
     *
     * This class extends from the Binders Class that cannot be sold
     *
     * @param name name of the Collector Binder
     */
    public CollectorBinder(String name){
        super(name);
    }


    /**
     *
     * Adds Rare and Legendary cards whose variant is not normal to the Collector Binder
     *
     * @param card - the card to be added to the binder
     *
     */
    public void addCard(Cards card){
        if ((card.getRarity().equalsIgnoreCase("LEGENDARY") || card.getRarity().equalsIgnoreCase("RARE")) &&
                !card.getVariant().equalsIgnoreCase("NORMAL")){
            super.addCard(card);
        }
        else {
            System.out.println("Invalid card. Must be either a Legendary or Rare card and not NORMAL.");
        }
    }
}
