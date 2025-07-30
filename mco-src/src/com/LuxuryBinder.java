package com;
// TODO: NEED TO ADD A SELLING METHOD
public class LuxuryBinder extends Binders implements Sellable {

    private double customPrice;

    public LuxuryBinder(String name){
        super(name);
        customPrice = -1;
    }

    public void addCard(Cards card) {
        if (!card.getVariant().equalsIgnoreCase("NORMAL")) {
            super.addCard(card);
        } else {
            System.out.println("Invalid card. Must NOT be a NORMAL card.");
        }
    }

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

    @Override
    public void sell() {
            TCIS.addMoney(getSaleValue());
    }
}

