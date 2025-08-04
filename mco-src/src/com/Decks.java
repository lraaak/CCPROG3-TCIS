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
     * Initializes the deck with a given name and initializes the list of cards.
     *
     * @param name the name of the deck
     */
    public Decks(String name) {
        this.cards = new ArrayList<>();
        this.name = name;
        totalDeck++;
    }

    /**
     * Adds a card to the deck if there is no duplicate card and if the deck has room (less than 10 cards).
     * It also removes the card from the collection after adding it to the deck.
     *
     * @param card the card to be added to the deck
     */
    public void addCard(Cards card) {
        if (Helper.findCard(card.getName(), this.cards) == null) {
            if (cards.size() < 10) {
                this.cards.add(card);
                Collection.removeCard(card.getName());  // Remove card from the collection after adding to deck
                System.out.println("Card has been added");
            } else {
                System.out.println("Deck is full");
            }
        } else {
            System.out.println("Duplicates are not allowed");
        }
    }

    /**
     * Removes a card from the deck if it exists and returns it to the collection.
     * If the deck is empty or the card does not exist in the deck, it shows an appropriate message.
     *
     * @param card the card to be removed from the deck
     */
    public void removeCard(Cards card) {
        if (Helper.findCard(card.getName(), this.cards) != null) {
            if (!cards.isEmpty()) {
                this.cards.remove(card);
                Collection.addCard(card.getName(), card.getRarity(), card.getVariant(), card.getBaseValue());  // Add removed card back to collection
            } else {
                System.out.println("Deck is empty");
            }
        } else {
            System.out.println("Card is not found");
        }
    }

    /**
     * Decreases the total number of decks by 1.
     */
    public static void decreaseDeckCount() {
        totalDeck--;
    }

    /**
     * Displays all the cards in the deck in alphabetical order.
     * If the deck is empty, it notifies the user.
     */
    public void displayDeck() {
        if (cards.isEmpty()) {
            System.out.println("Deck is empty");
        } else {
            Helper.displayAlphabeticallyNoCount(cards);  // Helper method to display cards in alphabetical order without counts
        }
    }

    /**
     * Returns the name of the deck.
     *
     * @return the name of the deck
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of cards in the deck.
     *
     * @return the list of cards in the deck
     */
    public ArrayList<Cards> getCard() {
        return cards;
    }

    /**
     * Returns the number of cards currently in the deck.
     *
     * @return the number of cards in the deck
     */
    public int getTotalCard() {
        return cards.size();
    }

    /**
     * Returns the total number of decks.
     *
     * @return the total number of decks
     */
    public static int getTotalDeck() {
        return totalDeck;
    }

    public String toString(){
        return name;
    }
}
