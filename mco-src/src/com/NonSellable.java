package com;

/**
 * The NonSellable interface is intended for items (such as binders or decks)
 * that cannot be sold but may still be tradable.
 */
public interface NonSellable {

    /**
     * Determines if the item can be traded.
     *
     * @return boolean - returns true if the item can be traded, false otherwise
     */
    boolean canTrade();
}
