package com;

public class NonCuratedBinder extends Binders{

    public NonCuratedBinder(String name){
        super(name);
    }
    @Override
    public void addCard(Cards card){
        super.addCard(card);
    }
}
