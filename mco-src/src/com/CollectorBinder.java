package com;

public class CollectorBinder extends Binders {

    public CollectorBinder(String name){
        super(name);
    }

    public void addCard(Cards card){
        if ((card.getRarity().equalsIgnoreCase("LEGENDARY") || card.getRarity().equalsIgnoreCase("Rare")) &&
                !card.getName().equalsIgnoreCase("NORMAL")){
            super.addCard(card);
        }
        else {
            System.out.println("Invalid card. Must be either a Legendary or Rare card and not NORMAL.");
        }
    }
}
