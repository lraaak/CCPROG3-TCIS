package com;
// TODO: NEED TO ADD A SELLING METHOD
public class RaresBinder extends Binders implements Sellable{

    public RaresBinder(String name){
        super(name);
    }
    @Override
    public void addCard(Cards card){
        if (card.getRarity().equalsIgnoreCase("RARE") || card.getRarity().equalsIgnoreCase("LEGENDARY")){
            super.addCard(card);
        }
        else {
            System.out.println("Invalid card. Must be either a Rare or Legendary card.");
        }
    }
    @Override
    public double getSaleValue(){
        double totalValue = 0;
        for (Cards c : this.getCard()){
            totalValue += c.getFinalValue();
        }
        return totalValue * 1.10;
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
