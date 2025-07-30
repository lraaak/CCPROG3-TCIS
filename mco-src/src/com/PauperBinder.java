package com;

public class PauperBinder extends Binders implements Sellable{

    public PauperBinder(String name){
        super(name);
    }
@Override
    public void addCard(Cards card){
        if (card.getRarity().equalsIgnoreCase("COMMON") || card.getRarity().equalsIgnoreCase("UNCOMMON")) {
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
            TCIS.addMoney(getSaleValue());
        }
}
