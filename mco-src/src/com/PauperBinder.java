package com;

public class PauperBinder extends Binders implements Sellable{

    public PauperBinder(String name){
        super(name);
    }
@Override
    public void addCard(Cards card){
        if (card.getVariant().equalsIgnoreCase("NORMAL") || card.getVariant().equalsIgnoreCase("UNCOMMON")) {
            super.addCard(card);
        }
        else {
            System.out.println("Invalid card. Must be either a Normal or Uncommon card.");
        }
    }
@Override
    public double getSaleValue(){
        double totalValue = 0;
        for (Cards c : this.getCard()){
            totalValue += c.getFinalValue();
        }
        return totalValue;
    }
@Override
    public void sell() {
        System.out.println(getName()+ "has a value of" + getSaleValue());
        System.out.print("Would you like to sell it? ");
        if (Helper.confirmAction()){
            TCIS.addMoney(getSaleValue());
        }
    }
}
