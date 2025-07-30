package com;

/**
 * The NonCuratedBinder class represents a type of binder that holds trading cards.
 * This binder does not impose any restrictions on the types of cards it can hold,
 * meaning any cards can be added to it.
 */
public class NonCuratedBinder extends Binders {

    /**
     * Initializes the NonCuratedBinder with a given name and an empty card list.
     *
     * @param name - the name of the binder
     */
    public NonCuratedBinder(String name){
        super(name);
    }
}
