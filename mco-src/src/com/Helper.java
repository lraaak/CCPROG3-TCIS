package com;

import java.util.*;

/**
 * The Helper class provides various utility methods for performing card-related operations
 * such as validating input, adding/removing cards, displaying card details, and more.
 */
public class Helper {

    /**
     * findCard
     *
     * @param name  the name of the card to search for
     * @param cards the list of cards to search through
     *
     * @return Cards - returns the card if found, or null if not found
     *
     * This method searches for a card in the list by its name and returns the card object.
     */
    public static Cards findCard(String name, ArrayList<Cards> cards) {
        for (Cards card : cards) {
            if (card.getName().equalsIgnoreCase(name)) {
                return card;  // returns the card object found
            }
        }
        return null;  // returns null if no card is found
    }

    /**
     * isCardConflicting
     *
     * @param cards    the list of cards to check against
     * @param name     the name of the card to check
     * @param rarity   the rarity of the card
     * @param variant  the variant of the card
     * @param baseValue the base value of the card
     *
     * @return boolean - returns true if a conflicting card is found, false otherwise
     *
     * This method checks if there is a card in the list with the same name but different details.
     */
    public static boolean isCardConflicting(ArrayList<Cards> cards, String name, String rarity, String variant, double baseValue) {
        for (Cards card : cards) {
            if (card.getName().equalsIgnoreCase(name) &&
                    (!card.getRarity().equals(rarity) || !card.getVariant().equals(variant) || card.getBaseValue() != baseValue)) {
                return true;  // returns true if a conflicting card is found
            }
        }
        return false;  // returns false if no conflict is found
    }

    /**
     * isValidRarity
     *
     * @param rarity the rarity of the card to validate
     *
     * @return boolean - returns true if the rarity is valid, false otherwise
     *
     * This method checks if the rarity is one of the valid types: COMMON, UNCOMMON, RARE, or LEGENDARY.
     */
    public static boolean isValidRarity(String rarity) {
        return rarity.equals("COMMON") || rarity.equals("UNCOMMON") || rarity.equals("RARE") || rarity.equals("LEGENDARY");
    }

    /**
     * isValidVariant
     *
     * @param variant the variant of the card to validate
     *
     * @return boolean - returns true if the variant is valid, false otherwise
     *
     * This method checks if the variant is one of the valid types: NORMAL, EXTENDED-ART, FULL-ART, or ALT-ART.
     */
    public static boolean isValidVariant(String variant) {
        return variant.equals("NORMAL") || variant.equals("EXTENDED-ART") ||
                variant.equals("FULL-ART") || variant.equals("ALT-ART");
    }

    /**
     * findBinder
     *
     * @param name    the name of the binder to find
     * @param binders the list of binders to search through
     *
     * @return Binders - returns the binder object if found, or null if not found
     *
     * This method searches for a binder in the list by its name and returns the binder object.
     */
    public static Binders findBinder(String name, ArrayList<Binders> binders) {
        for (Binders binder : binders) {
            if (binder.getName().equalsIgnoreCase(name)) {
                return binder;  // returns the binder object found
            }
        }
        return null;  // returns null if no binder is found
    }

    /**
     * findDecks
     *
     * @param name  the name of the deck to find
     * @param decks the list of decks to search through
     *
     * @return Decks - returns the deck object if found, or null if not found
     *
     * This method searches for a deck in the list by its name and returns the deck object.
     */
    public static Decks findDeck(String name, ArrayList<Decks> decks) {
        for (Decks deck : decks) {
            if (deck.getName().equalsIgnoreCase(name)) {
                return deck;  // returns the deck object found
            }
        }
        return null;  // returns null if no deck is found
    }

    /**
     * displayAlphabetically
     *
     * @param cards the list of cards to display
     *
     * 
     *
     * This method sorts the cards alphabetically by their name and then displays them along with their count.
     */
    public static void displayAlphabetically(ArrayList<Cards> cards) {
        ArrayList<Cards> sortedCards = new ArrayList<>(cards);

        Collections.sort(sortedCards, (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));  // sort alphabetically
        System.out.println("Cards:");
        for (Cards c : sortedCards) {
            System.out.println("Name: " + c.getName() + " | Count: " + c.getSelfCount());
        }
    }

    /**
     * displayAlphabeticallyNoCount
     *
     * @param cards the list of cards to display
     *
     * 
     *
     * This method sorts the cards alphabetically by their name and then displays them without the count.
     */
    public static void displayAlphabeticallyNoCount(ArrayList<Cards> cards) {
        ArrayList<Cards> sortedCards = new ArrayList<>(cards);

        Collections.sort(sortedCards, (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));  // sort alphabetically
        System.out.println("Cards:");
        int i = 1;
        for (Cards c : sortedCards) {
            System.out.println(("[") + i + ("]") + ("-") + c.getName());
            i++;
        }
    }

    /**
     * confirmAction
     *
     * @return boolean - returns true if the user confirms the action, false if canceled
     *
     * This method asks the user to confirm an action by entering 'yes' or 'no'. It returns true if 'yes' is entered, false if 'no'.
     */
    public static boolean confirmAction() {
        Scanner sc = new Scanner(System.in);
        String answer;

        while (true) {
            System.out.print("Are you sure with your action? (yes/no): ");
            answer = sc.nextLine();

            if (answer.equalsIgnoreCase("yes")) {
                return true;  // user confirmed the action
            } else if (answer.equalsIgnoreCase("no")) {
                System.out.println("Action cancelled");
                return false;  // user canceled the action
            } else {
                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
            }
        }
    }

    /**
     * generateCard
     *
     * @param sc the Scanner object for user input
     *
     * @return Cards - returns the newly created card object, or null if the process is canceled or invalid
     *
     * This method generates a new card by prompting the user for details such as name, rarity, variant, and value.
     * It checks for valid input and returns the card object if valid, otherwise returns null.
     */
    public static Cards generateCard(Scanner sc) {
        String name = getInput(sc, "Enter Card Name or 'EXIT' to cancel:");
        if (name == null) {
            return null;  // user canceled the input
        }

        String rarity = getValidRarity(sc);
        if (rarity == null) {
            return null;  // user canceled the input
        }

        String variant = "NORMAL";  // default value for variant
        if (rarity.equals("RARE") || rarity.equals("LEGENDARY")) {
            variant = getValidVariant(sc);
            if (variant == null) {
                return null;  // user canceled the input
            }
        }

        double value = getValidValue(sc);
        if (value == 0) {
            return null;  // user canceled the input
        }

        Cards newCard = new Cards(name, rarity, variant, value);
        System.out.println("Card generated: " + newCard.getName());
        return newCard;
    }

    /**
     * getInput
     *
     * @param sc    the Scanner object for user input
     * @param prompt the message to display to the user
     *
     * @return String - returns the input from the user, or null if the user cancels by typing "EXIT"
     *
     * This method prompts the user for input and checks if the input is "EXIT". If "EXIT" is entered, it returns null.
     */
    public static String getInput(Scanner sc, String prompt) {
        System.out.print(prompt);
        String input = sc.nextLine().trim().toUpperCase();
        if (input.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting");
            return null;  // user canceled the input
        }
        return input;  // return the valid input
    }

    /**
     * getValidRarity
     *
     * @param sc the Scanner object for user input
     *
     * @return String - returns the valid rarity input from the user, or null if the input is canceled
     *
     * This method prompts the user to enter a valid rarity (COMMON, UNCOMMON, RARE, or LEGENDARY).
     * It checks if the input is valid and returns it, or cancels the process if "EXIT" is entered.
     */
    public static String getValidRarity(Scanner sc) {
        String rarity;
        do {
            rarity = getInput(sc, "Enter Rarity (COMMON, UNCOMMON, RARE, LEGENDARY) or 'EXIT' to cancel: ");
            if (rarity == null)
                return null;  // user canceled the input
            if (!isValidRarity(rarity.toUpperCase())) {
                System.out.println("Invalid rarity. Please enter one of the following: COMMON, UNCOMMON, RARE, LEGENDARY.");
            }
        } while (!isValidRarity(rarity));

        return rarity.toUpperCase();  // return the valid rarity input
    }

    /**
     * getValidVariant
     *
     * @param sc the Scanner object for user input
     *
     * @return String - returns the valid variant input from the user, or null if the input is canceled
     *
     * This method prompts the user to enter a valid variant (for RARE or LEGENDARY cards) and returns it,
     * or cancels the process if "EXIT" is entered.
     */
    public static String getValidVariant(Scanner sc) {
        String variant;
        do {
            variant = getInput(sc, "Enter Variant (NORMAL, EXTENDED-ART, FULL-ART, ALT-ART) or 'EXIT' to cancel: ");
            if (variant == null) return null;  // user canceled the input
            if (!isValidVariant(variant.toUpperCase())) {
                System.out.println("Invalid variant. For RARE and LEGENDARY cards, valid variants are NORMAL, EXTENDED-ART, FULL-ART, ALT-ART.");
            }
        } while (!isValidVariant(variant.toUpperCase()));

        return variant.toUpperCase();  // return the valid variant input
    }

    /**
     * getValidValue
     *
     * @param sc the Scanner object for user input
     *
     * @return double - returns the valid value input from the user, or 0 if the input is canceled
     *
     * This method prompts the user to enter a valid dollar value for the card. If the input is invalid or canceled,
     * it returns 0.
     */
    public static double getValidValue(Scanner sc) {
        double value = 0;
        do {
            System.out.print("Enter Value (dollar value) or 'EXIT' to cancel: ");
            if (sc.hasNextDouble()) {
                value = sc.nextDouble();
                sc.nextLine();  // consume the newline character
                if (value <= 0) {
                    System.out.println("Value must be greater than zero. Please enter a valid value.");
                }
            } else {
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("EXIT")) {
                    System.out.println("Card input cancelled.");
                    return 0;  // user canceled the input
                }
                System.out.println("Invalid input! Please enter a valid dollar value.");
            }
        } while (value <= 0);  // keep asking until a valid value is provided
        return value;  // return the valid dollar value
    }

    /**
     * addingCard
     *
     * @param collection the collection object to add the card to
     * @param name      the name of the card to add
     * @param rarity    the rarity of the card
     * @param variant   the variant of the card
     * @param value     the value of the card
     *
     * 
     *
     * This method adds a card to the collection after checking if it conflicts with any existing cards.
     * If a conflict is found, it cancels the addition; otherwise, it adds the card to the collection.
     */
    public static void addingCard(Collection collection, String name, String rarity, String variant, double value) {
        if (isCardConflicting(collection.getCard(), name, rarity, variant, value)) {
            System.out.println("A card with the same name but different details already exists.");
            System.out.println("Card input canceled.");
        } else {
            Collection.addCard(name, rarity, variant, value);
        }
    }

    /**
     * findIndex
     *
     * @param card  the card to find the index of
     * @param cards the list of cards to search through
     *
     * @return int - returns the index of the card in the list, or -1 if the card is not found
     *
     * This method finds the index of a given card in the list of cards.
     */
    public static int findIndex(Cards card, ArrayList<Cards> cards) {
        for (int i = 0; i < cards.size(); i++) {
            if (card == cards.get(i)) {
                return i;  // returns the index of the card
            }
        }
        return -1;  // returns -1 if the card is not found
    }

    /**
     * displayCardDetails
     *
     * @param name  the name of the card to display details of
     * @param cards the list of cards to search through
     *
     * 
     *
     * This method displays the details of a card (name, rarity, variant, value, and count) if the card is found.
     * Otherwise, it prints an error message.
     */
    public static void displayCardDetails(String name, ArrayList<Cards> cards) {
        Cards foundCard = findCard(name, cards);
        if (foundCard != null) {
            System.out.println("====== Card Details ======");
            System.out.println("Name    : " + foundCard.getName());
            System.out.println("Rarity  : " + foundCard.getRarity());
            System.out.println("Variant : " + foundCard.getVariant());
            System.out.println("Value   : $" + String.format("%.2f", foundCard.getFinalValue()));
            System.out.println("Count   : " + foundCard.getSelfCount());
            System.out.println("==========================");
        } else {
            System.out.println("Card not found in collection.");
        }
    }

    /**
     * displayDeckCard
     *
     * @param name  the name of the card to display details of
     * @param cards the list of cards to search through
     *
     * 
     *
     * This method displays the details of a card (name, rarity, variant, value, and count) if the card is found.
     * Otherwise, it prints an error message.
     */
    public static void displayDeckCard(String name, ArrayList<Cards> cards) {
        Cards foundCard = findCard(name, cards);
        if (foundCard != null) {
            System.out.println("====== Card Details ======");
            System.out.println("Name    : " + foundCard.getName());
            System.out.println("Rarity  : " + foundCard.getRarity());
            System.out.println("Variant : " + foundCard.getVariant());
            System.out.println("Value   : $" + String.format("%.2f", foundCard.getFinalValue()));
            System.out.println("Count   : 1");
            System.out.println("==========================");
        } else {
            System.out.println("Card not found in collection.");
        }
    }
}
