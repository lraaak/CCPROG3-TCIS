package com;

/**
 * Represents a NonCuratedBinder, a type of binder that allows any card to be added
 * without any restrictions. This class extends from the {@link Binders} class and
 * does not implement the {@link Sellable} interface, meaning that NonCuratedBinders
 * cannot be sold.
 */
public class NonCuratedBinder extends Binders {

    /**
     * Constructor to initialize a NonCuratedBinder with the given name.
     *
     * @param name The name of the NonCuratedBinder.
     */
    public NonCuratedBinder(String name) {
        super(name);
    }

    /**
     * Adds a card to the NonCuratedBinder. There are no restrictions on what cards can be added
     * to this binder.
     *
     * @param card The card to be added to the binder.
     */
    @Override
    public void addCard(Cards card) {
        super.addCard(card);  // Simply adds the card without any restrictions.
    }
}
