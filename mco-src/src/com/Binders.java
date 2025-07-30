package com;

import java.util.*;

/**
 * Binders class represents a binder of cards. It contains methods to add, remove, trade cards,
 * and view binder information. A binder can hold up to 20 cards.
 */
public abstract class Binders {
    protected String name;
    protected ArrayList<Cards> cards;
    private static int totalBinders = 0;

    /**
     * Initializes the binder with a name and an empty card list, and increases
     * the count of total binders.
     *
     * @param name - the name of the binder
     */
    public Binders(String name){
        this.name = name;
        this.cards = new ArrayList<>();
        totalBinders++;
    }

    /**
     * Adds the card to the binder if there is space available. It removes the card
     * from the collection after adding it to the binder.
     *
     * @param card - the card to be added to the binder
     * 
     */
    public void addCard(Cards card){
        if (this.cards.size() < 20){
            this.cards.add(card);
            Collection.removeCard(card.getName());
            System.out.println("Card added to binder: " + card.getName());
        }
        else {
            System.out.println("Binder is full");
        }
    }

    /**
     * Removes the card from the binder and adds it back to the collection.
     *
     * @param card - the card to be removed from the binder
     * 
     */
    public void removeCard(Cards card){
        if (!this.cards.isEmpty()){
            this.cards.remove(card);
            Collection.addCard(card.getName(), card.getRarity(), card.getVariant(), card.getBaseValue());
            System.out.println("Card removed from binder: " + card.getName());
        }
        else {
            System.out.println("Card is not found in the binder or binder is empty");
        }
    }

    /**
     * Performs the trade by first adding the incoming card to the collection,
     * prompting the user for confirmation if the value difference between the two cards
     * is greater than or equal to $1, and updating the binder accordingly.
     *
     * @param ownCard - the card the user is giving up in the trade
     * @param otherCard - the card the user is receiving in the trade
     * @param ownCardIndex - the index of the card in the binder to be replaced
     * 
     */
    public void tradeCards(Cards ownCard, Cards otherCard, int ownCardIndex) {
        // Add the incoming card to the collection first
        Collection.addCard(otherCard.getName(), otherCard.getRarity(), otherCard.getVariant(), otherCard.getBaseValue());

        // Calculate the value difference between the outgoing (ownCard) and incoming (otherCard) cards
        double valueDifference = Math.abs(ownCard.getFinalValue() - otherCard.getFinalValue());

        String response;
        Scanner sc = new Scanner(System.in);
        do {
            if (valueDifference >= 1.0) {
                System.out.println("The value difference between the two cards is $" + valueDifference + ".");
            }
            System.out.print("Do you want to proceed with the trade? (yes/no): ");
            response = sc.nextLine().toLowerCase();
        } while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));

        if ("no".equalsIgnoreCase(response)) {
            System.out.println("Trade cancelled.");
            Collection.removeCard(otherCard.getName());
            return;
        }

        // Update the binder with the incoming card and remove the outgoing card
        this.cards.set(ownCardIndex, otherCard);
        Collection.removeCard(otherCard.getName());
        System.out.println("Trade successful! " + ownCard.getName() + " has been traded for " + otherCard.getName() + ".");
        System.out.println("Your new card in the binder: " + otherCard.getName());
    }

    /**
     * Decreases the total number of binders.
     *
     * 
     */
    public static void decreaseBinderCount() {
        totalBinders--;
    }

    /**
     * Displays all the cards in the binder in alphabetical order, or notifies
     * if the binder is empty.
     *
     * 
     */
    public void displayBinder() {
        if (cards.isEmpty()){
            System.out.println("Binder is empty");
        } else {
            Helper.displayAlphabeticallyNoCount(cards);
        }
    }

    /**
     * Returns the name of the binder.
     *
     * @return String - the name of the binder
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of cards in the binder.
     *
     * @return ArrayList - the list of cards in the binder
     */
    public ArrayList<Cards> getCard() {
        return cards;
    }

    /**
     * Returns the total count of binders.
     *
     * @return int - the total count of binders
     */
    public static int getTotalBinders() {
        return totalBinders;
    }
}
