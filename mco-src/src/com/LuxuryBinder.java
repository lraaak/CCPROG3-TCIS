package com;
// TODO: NEED TO ADD A SELLING METHOD
public class LuxuryBinder extends Binders implements Sellable {

    private double customPrice;

    public LuxuryBinder(String name){
        super(name);
        customPrice = -1;
    }

    public void addCard(Cards card){
        if (!card.getVariant().equalsIgnoreCase("NORMAL")){
            super.addCard(card);
        }
        else {
            System.out.println("Invalid card. Must NOT be a NORMAL card.");
        }
    }

    public void setCustomPrice(double price){
        double minPrice = 0;
        for (Cards c : this.getCard()){
            minPrice += c.getFinalValue();
        }

        if (minPrice > price){
            customPrice = price * 1.10;
        }
        else {
            System.out.println("Custom Price must be greater than the minimum price of the cards in the binder.");
        }
    }

    public double getSaleValue(){
        if (customPrice != -1){
            return customPrice;
        }
        else {
            double totalValue = 0;
            for (Cards c : this.getCard()){
                totalValue += c.getFinalValue();
            }
            return totalValue * 1.10;
        }
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

