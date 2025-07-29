package com;

import java.util.*;

/**
 * This class manages a collection of trading cards. It allows for adding, removing,
 * displaying, and managing the count of cards in the collection.
 */
public class Collection {
    private static ArrayList<Cards> cards;
    private static int totalCount = 0;

    public Collection(){
        Collection.cards = new ArrayList<>();
    }

    /**
     * Adds a new card to the collection if it doesn't already exist or increases
     * the count of an existing card if it does.
     *
     * @param name - the name of the card to be added
     * @param rarity - the rarity of the card (e.g., COMMON, UNCOMMON, RARE, LEGENDARY)
     * @param variant - the variant of the card (if applicable)
     * @param baseValue - the base dollar value of the card
     *
     *
     * @return void
     */
    public static void addCard(String name, String rarity, String variant, double baseValue) {

        Cards existingCard = Helper.findCard(name, cards);

        if (existingCard == null) {
            Cards newCard = new Cards(name, rarity, variant, baseValue);
            cards.add(newCard);
            increaseTotalCount();
            System.out.println("Card added to the collection: " + newCard.getName());
        } else {
            existingCard.increaseCount();
            increaseTotalCount();
            System.out.println("Card exists, count increased: " + existingCard.getName());
        }
    }

    /**
     * removeCard
     *
     * @param name - the name of the card to be removed
     *
     * Removes a card from the collection by decreasing its count or fully removing
     * it if its count reaches zero.
     *
     * @return void
     */
    public static void removeCard(String name){
        Cards foundCard = Helper.findCard(name, cards);
        if (foundCard != null) {
            if (foundCard.getSelfCount() > 1){
                foundCard.decreaseCount();
                decreaseTotalCount();
                System.out.println("Card count decreased. New Count: " + foundCard.getSelfCount());
            }
            else {
                cards.remove(foundCard);
                decreaseTotalCount();
                System.out.println("Card removed from collection: " + foundCard.getName());
            }
        } else {
            System.out.println("Card not found in collection.");
        }
    }

    public static void sellCard(String name){
        Cards foundCard = Helper.findCard(name, cards);

        if (foundCard == null) {
            System.out.println("Card not found in collection.");
        } else {
            if (Helper.confirmAction()) {
                TCIS.addMoney(foundCard.getFinalValue());
                removeCard(foundCard.getName());
                System.out.println("Card sold: " + foundCard.getName());
                System.out.println("New balance: $" + TCIS.getMoney());
            }
        }
    }



    /**
     * displayAllCards
     *
     * Displays all the cards in the collection in alphabetical order.
     * If no cards are found, it informs the user that the collection is empty.
     *
     * @return void
     */
    public void displayAllCards() {

        if (cards.isEmpty()) {
            System.out.println("No cards in collection.");
        } else {
            Helper.displayAlphabetically(cards);
        }
    }

    /**
     * increaseTotalCount
     *
     * Increases the total count of cards in the collection by 1.
     *
     * @return void
     */
    public static void increaseTotalCount() {
        totalCount++;
    }

    /**
     * decreaseTotalCount
     *
     * Decreases the total count of cards in the collection by 1, ensuring the count
     * does not go below zero.
     *
     * @return void
     */
    public static void decreaseTotalCount() {
        if (totalCount > 0)
            totalCount--;
    }

    /**
     * addCardCount
     *
     * @param name - the name of the card whose count is to be increased
     *
     * Increases the count of a specific card in the collection by 1.
     *
     * @return void
     */
    public void increaseCardCount(String name) {
        Cards foundCard = Helper.findCard(name, cards);
        if (foundCard != null) {
            foundCard.increaseCount();
            increaseTotalCount();
            System.out.println("Card count increased: " + foundCard.getName());
        } else {
            System.out.println("Card not found in collection.");
        }
    }

    /**
     * decreaseCardCount
     *
     * @param name - the name of the card whose count is to be decreased
     *
     * Decreases the count of a specific card in the collection by 1. If the count
     * reaches zero, the card is removed from the collection.
     *
     * @return void
     */
    public void decreaseCardCount(String name) {
        Cards foundCard = Helper.findCard(name, cards);
        if (foundCard != null) {
            if (foundCard.getSelfCount() > 1) {
                foundCard.decreaseCount();
                decreaseTotalCount();
                System.out.println("Card count decreased: " + foundCard.getName());
            } else {
                cards.remove(foundCard);
                decreaseTotalCount();
                System.out.println("Card removed from collection: " + foundCard.getName());
            }
        } else {
            System.out.println("Card not found in collection.");
        }
    }

    /**
     * getCard
     *
     * @return ArrayList<Cards> - returns the list of all cards in the collection
     *
     * Returns the list of all cards currently in the collection.
     */
    public ArrayList<Cards> getCard() {
        return cards;
    }

}
