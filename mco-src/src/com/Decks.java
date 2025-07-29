package com;
import java.util.*;

/**
 * The Decks class represents a collection of trading cards grouped together as a deck.
 * It allows for adding and removing cards, managing deck count, and displaying deck details.
 */
public class Decks {
    protected String name;  // Name of the deck
    protected ArrayList<Cards> cards;  // List of cards in the deck
    private static int totalDeck = 0;  // Static variable to keep track of total number of decks

    /**
     * Decks Constructor
     *
     * @param name - the name of the deck
     *
     * Initializes the deck with a given name and initializes the list of cards.
     */
    public Decks (String name){
        this.cards = new ArrayList<>();
        this.name = name;
        totalDeck++;
    }

    /**
     * addCard
     *
     * @param card - the card to be added to the deck
     *
     * This method adds a card to the deck if there is no duplicate card and if the deck has room (less than 10 cards).
     * It also removes the card from the collection after adding it to the deck.
     */
    public void addCard(Cards card){
        if (Helper.findCard(card.getName(), this.cards) == null){
            if (cards.size()< 10){
                this.cards.add(card);
                Collection.removeCard(card.getName());  // Remove card from the collection after adding to deck
                System.out.println("Card has been added");
            }
            else {
                System.out.println("Deck is full");
            }
        }
        else{
            System.out.println("Duplicates are not allowed");
        }
    }

    /**
     * removeCard
     *
     * @param card - the card to be removed from the deck
     *
     * This method removes a card from the deck if it exists and returns it to the collection.
     * If the deck is empty or the card does not exist in the deck, it shows an appropriate message.
     */
    public void removeCard(Cards card){
        if (Helper.findCard(card.getName(),this.cards) != null){
            if (!cards.isEmpty()){
                this.cards.remove(card);
                Collection.addCard(card.getName(), card.getRarity(), card.getVariant(), card.getBaseValue());  // Add removed card back to collection
            }
            else   {
                System.out.println("Deck is empty");
            }
        }
        else {
            System.out.println("Card is not found");
        }
    }

    /**
     * decreaseDeckCount
     *
     *
     * This static method decreases the total number of decks by 1.
     */
    public static void decreaseDeckCount(){
        totalDeck--;
    }

    /**
     * displayDeck
     *
     *
     * This method displays all the cards in the deck in alphabetical order. If the deck is empty, it notifies the user.
     */
    public void displayDeck(){
        if (cards.isEmpty()){
            System.out.println("Deck is empty");
        } else {
            Helper.displayAlphabeticallyNoCount(cards);  // Helper method to display cards in alphabetical order without counts
        }
    }

    /**
     * getName
     *
     * @return String - returns the name of the deck
     *
     * This method returns the name of the deck.
     */
    public String getName() {
        return name;
    }

    /**
     * getCard
     *
     * @return ArrayList<Cards> - returns the list of cards in the deck
     *
     * This method returns the list of cards in the deck.
     */
    public ArrayList<Cards> getCard() {
        return cards;
    }

    /**
     * getTotalCard
     *
     * @return int - returns the total number of cards in the deck
     *
     * This method returns the number of cards currently in the deck.
     */

    /**
     * getTotalDeck
     *
     * @return int - returns the total number of decks
     *
     * This static method returns the total number of decks.
     */
    public static int getTotalDeck() {
        return totalDeck;
    }
}
