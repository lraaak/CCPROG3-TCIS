package com;

public class SellableDeck extends Decks implements Sellable{

    public SellableDeck(String name){
        super(name);
    }

    public double getSaleValue(){
        double totalValue = 0;
        for (Cards c : this.getCard()){
            totalValue += c.getFinalValue();
        }
        return totalValue;
    }
    @Override
    public void sell() {
        System.out.println(getName()+ " has a value of " + getSaleValue());
        System.out.print("Would you like to sell it? ");
        if (Helper.confirmAction()){
            TCIS.addMoney(getSaleValue());
            TCIS.showMoney();
        }
    }
}
    

