package com;

/**
 * The {@code NonSellable} interface defines the contract for objects that cannot be sold,
 * but can still be traded.
 * Classes implementing this interface should provide an implementation for the {@code canTrade} method.
 */
public interface NonSellable {

    /**
     * Determines if an item can be traded. Items that are marked as non-sellable may still be traded.
     *
     * @return {@code true} if the item can be traded, {@code false} otherwise.
     */
    boolean canTrade();
}
