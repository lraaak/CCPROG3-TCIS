package com;
import java.util.*;

    /**
    * TCIS (Trading Card Inventory System) class manages various operations such as creating,
    * deleting, viewing decks, binders, and managing cards in the collection.
    */
    public class TCIS {

        private ArrayList<Decks> decks;
        private ArrayList<Binders> binders;
        private Collection collection;
        private static double money;

        /**
         * Constructor that initializes the TCIS system, including decks, binders, and the collection.
         * Sets initial money to 0.
         */
        public TCIS() {
            this.decks = new ArrayList<>();
            this.binders = new ArrayList<>();
            this.collection = new Collection();
            money = 0;
        }
        /*
        this is for the new lines of codes for gui
         */

        public void addDeck (Decks deck){
            decks.add(deck);
        }

        public void addBinder(Binders binder){
            binders.add(binder);
        }

        /**
         * Checks if the TCIS system is valid (i.e., decks, binders, and collection are not null).
         *
         * @return true if the TCIS is valid, false otherwise.
         */
        public boolean isValid() {
            return this.decks != null && this.binders != null && this.collection != null;
        }

        /**
         * Returns the collection object.
         *
         * @return the collection object.
         */
        public Collection getCollection() {
            return collection;
        }

        /**
         * Returns the list of decks.
         *
         * @return the list of decks.
         */
        public ArrayList<Decks> getDecks() {
            return decks;
        }

        /**
         * Returns the list of binders.
         *
         * @return the list of binders.
         */
        public ArrayList<Binders> getBinders() {
            return binders;
        }

        /**
         * Displays the current money available in the system.
         */
        public static void showMoney() {
            System.out.printf("Your current money: $%.2f%n", money);
        }

        /**
         * Adds money to the current balance.
         *
         * @param amount the amount of money to add.
         */
        public static void addMoney(double amount) {
            money += amount;
        }

        /**
         * Returns the current money balance.
         *
         * @return the current money balance.
         */
        public static double getMoney() {
            return money;
        }

        //delete
        public void setBinders(ArrayList<Binders> b) {
            this.binders = b;
        }

        public void setDecks(ArrayList<Decks> d) {
            this.decks = d;
        }

        /**
         * Sells a binder and removes it from the binders list if it's sellable.
         *
         * @param binder the binder to sell.
         */

        public void sellBinder(Binders binder) {
            if (binder instanceof Sellable ) {
                System.out.printf("%s has a value of $%.2f%n", binder.getName(), ((Sellable) binder).getSaleValue());
                if (Helper.confirmAction()) {
                    System.out.println("You have sold the binder: " + binder.getName());
                    ((Sellable) binder).sell();
                    binders.remove(binder);
                }
            } else {
                System.out.println("This binder cannot be sold.");
            }

        }

        /**
         * Sells a deck and removes it from the decks list if it's sellable.
         *
         * @param deck the deck to sell.
         */
        public void sellDeck(Decks deck) {
            if (deck instanceof Sellable ) {
                System.out.printf("%s has a value of $%.2f%n", deck.getName(), ((Sellable) deck).getSaleValue());
                if (Helper.confirmAction()) {
                    ((Sellable) deck).sell();
                    System.out.println("You have sold the deck: " + deck.getName());
                    decks.remove(deck);
                }
            } else {
                System.out.println("This deck cannot be sold.");
            }
        }

        /**
         * Creates a new deck with the given name if it does not already exist.
         *
         * @param name the name of the deck to create.
         */
        public void createDeck(String name) {

            if (Helper.findDeck(name, decks) == null) {
                System.out.println("Select Deck Type:");
                System.out.println("[1] Sellable Deck");
                System.out.println("[2] Non-Sellable Deck");
                System.out.println("[0] Exit");
                Scanner sc = new Scanner(System.in);
                int choice;
                do {
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1:
                            if (Helper.confirmAction()) {
                                SellableDeck sellableDeck = new SellableDeck(name);
                                decks.add(sellableDeck);
                                System.out.println("Sellable Deck has been created");
                            }
                            choice = 0;
                            break;
                        case 2:
                            if (Helper.confirmAction()) {
                                Decks deck = new Decks(name);
                                decks.add(deck);
                                System.out.println("Non-Sellable Deck has been created");
                            }
                            choice = 0;
                            break;
                        case 0:
                            System.out.println("Exiting");
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid deck type.");
                    }
                } while (choice != 0);
            } else {
                System.out.println("Deck already exists");
            }
        }

        /**
         * Creates a new binder with the given name if it does not already exist.
         *
         * @param name the name of the binder to create.
         */
        public void createBinder(String name) {
            Scanner sc = new Scanner(System.in);
            if (Helper.findBinder(name, binders) == null) {
                int choice;
                System.out.println("Select Binder Type:");
                System.out.println("[1] Pauper Binder (Sellable)");
                System.out.println("[2] Rares Binder (Sellable)");
                System.out.println("[3] Luxury Binder (Sellable)");
                System.out.println("[4] Non-curated Binder (Non-Sellable)");
                System.out.println("[5] Collector Binder (Non-Sellable)");
                System.out.println("[0] Exit");
                do {
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            if (Helper.confirmAction()) {
                                PauperBinder pauperBinder = new PauperBinder(name);
                                binders.add(pauperBinder);
                                System.out.println("Pauper Binder has been created");
                            }
                            choice = 0;
                            break;
                        case 2:
                            if (Helper.confirmAction()) {
                                RaresBinder raresBinder = new RaresBinder(name);
                                binders.add(raresBinder);
                                System.out.println("Rares Binder has been created");
                            }
                            choice = 0;
                            break;
                        case 3:
                            if (Helper.confirmAction()) {
                                LuxuryBinder luxuryBinder = new LuxuryBinder(name);
                                binders.add(luxuryBinder);
                                System.out.println("Luxury Binder has been created");
                            }
                            choice = 0;
                            break;
                        case 4:
                            if (Helper.confirmAction()) {
                                Binders nonCuratedBinder = new NonCuratedBinder(name);
                                binders.add(nonCuratedBinder);
                                System.out.println("Non-curated Binder has been created");
                            }
                            choice = 0;
                            break;
                        case 5:
                            if (Helper.confirmAction()) {
                                CollectorBinder collectorBinder = new CollectorBinder(name);
                                binders.add(collectorBinder);
                                System.out.println("Collector Binder has been created");
                            }
                            choice = 0;
                            break;
                        case 0:
                            System.out.println("Exiting");
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid binder type.");
                    }
                } while (choice != 0);
            } else {
                System.out.println("Binder already exists");
            }
        }

        /**
         * Deletes a deck from the system and adds all its cards back to the collection.
         *
         * @param deck the deck to delete.
         */
        public void deleteDeck(Decks deck) {

            System.out.println("Deck has been deleted");
            for (Cards card : deck.getCard()) {
                Collection.addCard(card.getName(), card.getRarity(), card.getVariant(), card.getBaseValue());
                Collection.increaseTotalCount();
            }

            decks.remove(deck);
            Decks.decreaseDeckCount();

        }

        /**
         * Deletes a binder from the system and adds all its cards back to the collection.
         *
         * @param binder the binder to delete.
         */
        public void deleteBinder(Binders binder) {

            System.out.println("Binder has been deleted");
            for (Cards card : binder.getCard()) {
                Collection.addCard(card.getName(), card.getRarity(), card.getVariant(), card.getBaseValue());
                Collection.increaseTotalCount();
            }

            binders.remove(binder);
            Binders.decreaseBinderCount();

        }

        /**
         * Displays the list of binders with their names and indexes.
         */
        public void viewBinders() {
            System.out.println("=== BINDERS LIST ===");

            if (binders.isEmpty()) {
                System.out.println("No binders available.");
                return;
            }

            System.out.println("╔═════╦════════════════════════╗");
            System.out.println("║ No. ║ Binder Name            ║");
            System.out.println("╠═════╬════════════════════════╣");

            for (int i = 0; i < binders.size(); i++) {
                System.out.printf("║ %-3d ║ %-22s ║%n", i + 1, binders.get(i).getName());
            }

            System.out.println("╚═════╩════════════════════════╝");

        }

        /**
         * Displays the list of decks with their names and indexes.
         */
        public void viewDecks() {

            if (decks.isEmpty()) {
                System.out.println("No decks available.");
                return;
            }

            System.out.println("╔═════╦════════════════════════╗");
            System.out.println("║ No. ║ Deck Name              ║");
            System.out.println("╠═════╬════════════════════════╣");

            for (int i = 0; i < decks.size(); i++) {
                System.out.printf("║ %-3d ║ %-22s ║%n", i + 1, decks.get(i).getName());
            }

            System.out.println("╚═════╩════════════════════════╝");
        }


        /**
         * Displays Main Menu for the TCIS Program
         */
        public void displayMenu() {
            System.out.println("Welcome to the TCIS");
            Scanner sc = new Scanner(System.in);

            int userChoice;
            do {
                System.out.println("=== Trading Card Inventory System (TCIS) ===");
                showMoney();
                System.out.println("[0] - Exit");
                System.out.println("[1] - Add Card");
                System.out.println("[2] - Add Deck");
                System.out.println("[3] - Add Binder");
                System.out.println("[4] - View Collection");
                int option = 5;
                boolean existingBinder = false;
                boolean existingDeck = false;

                if (!binders.isEmpty()) {
                    existingBinder = true;
                    System.out.println("[" + option + "] - Manage Binder");
                    option++;
                }
                if (!decks.isEmpty()) {
                    existingDeck = true;
                    System.out.println("[" + option + "] - Manage Decks");
                }
                System.out.println("============================================");

                System.out.print("Select an option:");
                userChoice = sc.nextInt();
                sc.nextLine();

                if (userChoice == 1) {
                    Cards newCard = Helper.generateCard(sc);
                    if (newCard != null) {
                        Helper.addingCard(collection, newCard.getName(), newCard.getRarity(), newCard.getVariant(), newCard.getBaseValue());
                    } else {
                        System.out.println("Card input cancelled.");
                    }
                } else if (userChoice == 2) {
                    System.out.print("Name:");
                    String name = sc.nextLine();
                    createDeck(name);
                } else if (userChoice == 3) {
                    System.out.print("Name:");
                    String name = sc.nextLine();
                    createBinder(name);

                } else if (userChoice == 4) {
                    if (!collection.getCard().isEmpty()) {
                        String cardName;
                        do {
                            collection.displayAllCards();
                            System.out.print("Enter card name to view details and EXIT to go back:");
                            cardName = sc.nextLine();
                            Cards card = Helper.findCard(cardName, collection.getCard());
                            if (card != null) {
                                int choice;
                                do {
                                    System.out.println("╔══════════════════════════════╗");
                                    System.out.println("║ [0] Back to Collection       ║");
                                    System.out.println("║ [1] View Details             ║");
                                    System.out.println("║ [2] Increase Count           ║");
                                    System.out.println("║ [3] Decrease Count           ║");
                                    System.out.println("║ [4] Sell Card                ║");
                                    System.out.println("║ [5] Return to Menu           ║");
                                    System.out.println("╚══════════════════════════════╝");
                                    System.out.print("Enter choice:");
                                    choice = sc.nextInt();
                                    sc.nextLine();
                                    switch (choice) {
                                        case 1:
                                            Helper.displayCardDetails(card.getName(), collection.getCard());
                                            break;
                                        case 2:
                                            if (Helper.confirmAction()) {
                                                collection.increaseCardCount(card.getName());
                                                System.out.println("Card count has been increased");
                                            }
                                            break;
                                        case 3:
                                            if (Helper.confirmAction()) {
                                                collection.decreaseCardCount(card.getName());
                                                System.out.println("Card count has been decreased");
                                                card = Helper.findCard(cardName, collection.getCard());
                                                if (card == null ){
                                                    choice = 0;
                                                }
                                            }
                                            break;
                                        case 4:
                                            collection.sellCard(card);
                                            choice = 0;
                                            break;
                                        case 5:
                                            choice = 0;
                                            cardName = "exit";
                                            break;
                                        case 0:
                                            break;
                                        default:
                                            System.out.println("Invalid choice");
                                    }
                                } while (choice != 0 && !collection.getCard().isEmpty());
                            } else if (!cardName.equalsIgnoreCase("exit") && !collection.getCard().isEmpty()) {
                                System.out.println("Card not found");
                            }
                        } while (!cardName.equalsIgnoreCase("exit") && !collection.getCard().isEmpty());
                    } else {
                        System.out.println("Collection is empty");
                    }
                } else if (userChoice == 5 && existingBinder) {
                    String name;
                    do {
                        viewBinders();
                        System.out.print("Enter binder name or EXIT to go back:");
                        name = sc.nextLine();
                        Binders binder = Helper.findBinder(name, binders);
                        if (binder != null && (binder instanceof NonCuratedBinder || binder instanceof CollectorBinder)) {
                            int binderChoice;
                            do {
                                System.out.println("Binder: " + binder.getName());
                                System.out.println("╔══════════════════════════════╗");
                                System.out.println("║ [0] Exit                     ║");
                                System.out.println("║ [1] Add Card                 ║");
                                System.out.println("║ [2] Remove Card              ║");
                                System.out.println("║ [3] View Cards               ║");
                                System.out.println("║ [4] Trade                    ║");
                                System.out.println("║ [5] Delete Binder            ║");
                                System.out.println("║ [6] Return to menu           ║");
                                System.out.println("╚══════════════════════════════╝");
                                System.out.print("Enter choice:");
                                binderChoice = sc.nextInt();
                                sc.nextLine();

                                switch (binderChoice) {
                                    case 1:
                                        collection.displayAllCards();
                                        if (!collection.getCard().isEmpty()) {
                                            System.out.println("Enter card name to add: ");
                                            String cardName = sc.nextLine();
                                            Cards cardToAdd = Helper.findCard(cardName, collection.getCard());

                                            if (cardToAdd != null) {
                                                if (Helper.confirmAction()) {
                                                    binder.addCard(cardToAdd);
                                                }
                                            } else {
                                                System.out.println("Card is not found");
                                            }
                                        }
                                        break;
                                    case 2:
                                        binder.displayBinder();
                                        if (!binder.getCard().isEmpty()) {
                                            String binderCard = sc.nextLine();
                                            Cards cardToRemove = Helper.findCard(binderCard, binder.getCard());

                                            if (cardToRemove != null) {
                                                binder.removeCard(cardToRemove);
                                                System.out.println("Card has been removed");
                                            } else {
                                                System.out.println("Card is not found");
                                            }
                                        }
                                        break;

                                    case 3:
                                        binder.displayBinder();
                                        break;

                                    case 4:
                                        binder.displayBinder();
                                        if (!binder.getCard().isEmpty()) {
                                            System.out.print("Enter card name:");
                                            String card = sc.nextLine();
                                            Cards ownCard = Helper.findCard(card, binder.getCard());
                                            if (ownCard != null) {
                                                System.out.println("Please input details of incoming card");
                                                Cards incomingCard = Helper.generateCard(sc);
                                                if (incomingCard != null) {
                                                    int cardIndex = Helper.findIndex(ownCard, binder.getCard());
                                                    binder.tradeCards(ownCard, incomingCard, cardIndex);
                                                } else {
                                                    System.out.println("Trade was not successful");
                                                }
                                            } else {
                                                System.out.println("Card is not found");
                                            }
                                        }
                                        break;
                                    case 5:
                                        String answer;
                                        boolean isDone = false;
                                        do {
                                            System.out.println("Are you sure you want to delete " + binder.getName() + "?" + "(yes/no)");

                                            answer = sc.nextLine();

                                            if (answer.equalsIgnoreCase("yes")) {
                                                deleteBinder(binder);
                                                System.out.println("Cards have been returned to the collection");
                                                isDone = true;
                                                binderChoice = 0;
                                                name = "EXIT";
                                            } else if (answer.equalsIgnoreCase("no")) {
                                                isDone = true;
                                            } else {
                                                System.out.println("Invalid input");
                                            }
                                        } while (!isDone);
                                        break;
                                    case 6:
                                        binderChoice = 0;
                                        name = "exit";
                                        break;
                                    case 0:
                                        System.out.println("Exiting");
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                }
                            } while (binderChoice != 0);
                        } else if (binder != null && (binder instanceof Sellable) && !(binder instanceof LuxuryBinder)) {
                            int binderChoice;
                            do {
                                System.out.println("Binder: " + binder.getName());
                                System.out.println("╔══════════════════════════════╗");
                                System.out.println("║ [0] Exit                     ║");
                                System.out.println("║ [1] Add Card                 ║");
                                System.out.println("║ [2] Remove Card              ║");
                                System.out.println("║ [3] View Cards               ║");
                                System.out.println("║ [4] Sell Binder              ║");
                                System.out.println("║ [5] Delete Binder            ║");
                                System.out.println("║ [6] Return to menu           ║");
                                System.out.println("╚══════════════════════════════╝");
                                System.out.print("Enter choice:");
                                binderChoice = sc.nextInt();
                                sc.nextLine();

                                switch (binderChoice) {
                                    case 1:
                                        collection.displayAllCards();
                                        if (!collection.getCard().isEmpty()) {
                                            System.out.println("Enter card name to add: ");
                                            String cardName = sc.nextLine();
                                            Cards cardToAdd = Helper.findCard(cardName, collection.getCard());

                                            if (cardToAdd != null) {
                                                if (Helper.confirmAction()) {
                                                    binder.addCard(cardToAdd);
                                                }
                                            } else {
                                                System.out.println("Card is not found");
                                            }
                                        }
                                        break;
                                    case 2:
                                        binder.displayBinder();
                                        if (!binder.getCard().isEmpty()) {
                                            String binderCard = sc.nextLine();
                                            Cards cardToRemove = Helper.findCard(binderCard, binder.getCard());

                                            if (cardToRemove != null) {
                                                binder.removeCard(cardToRemove);
                                                System.out.println("Card has been removed");
                                            } else {
                                                System.out.println("Card is not found");
                                            }
                                        }
                                        break;

                                    case 3:
                                        binder.displayBinder();
                                        break;

                                    case 4:
                                        sellBinder(binder);
                                        binderChoice = 0;
                                        break;
                                    case 5:
                                        String answer;
                                        boolean isDone = false;
                                        do {
                                            System.out.println("Are you sure you want to delete " + binder.getName() + "?" + "(yes/no)");

                                            answer = sc.nextLine();

                                            if (answer.equalsIgnoreCase("yes")) {
                                                deleteBinder(binder);
                                                System.out.println("Cards have been returned to the collection");
                                                isDone = true;
                                                binderChoice = 0;
                                                name = "EXIT";
                                            } else if (answer.equalsIgnoreCase("no")) {
                                                isDone = true;
                                            } else {
                                                System.out.println("Invalid input");
                                            }
                                        } while (!isDone);
                                        break;
                                    case 6:
                                        binderChoice = 0;
                                        name = "exit";
                                        break;
                                    case 0:
                                        System.out.println("Exiting");
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                }
                            } while (binderChoice != 0);

                        } else if (binder instanceof LuxuryBinder){
                            int binderChoice;
                            do {
                                System.out.println("Binder: " + binder.getName());
                                System.out.println("╔══════════════════════════════╗");
                                System.out.println("║ [0] Exit                     ║");
                                System.out.println("║ [1] Add Card                 ║");
                                System.out.println("║ [2] Remove Card              ║");
                                System.out.println("║ [3] View Cards               ║");
                                System.out.println("║ [4] Set Price                ║");
                                System.out.println("║ [5] Sell Binder              ║");
                                System.out.println("║ [6] Delete Binder            ║");
                                System.out.println("║ [7] Return to menu           ║");
                                System.out.println("╚══════════════════════════════╝");
                                System.out.print("Enter choice:");
                                binderChoice = sc.nextInt();
                                sc.nextLine();

                                switch (binderChoice) {
                                    case 1:
                                        collection.displayAllCards();
                                        if (!collection.getCard().isEmpty()) {
                                            System.out.println("Enter card name to add: ");
                                            String cardName = sc.nextLine();
                                            Cards cardToAdd = Helper.findCard(cardName, collection.getCard());

                                            if (cardToAdd != null) {
                                                if (Helper.confirmAction()) {
                                                    binder.addCard(cardToAdd);
                                                }
                                            } else {
                                                System.out.println("Card is not found");
                                            }
                                        }
                                        break;
                                    case 2:
                                        binder.displayBinder();
                                        if (!binder.getCard().isEmpty()) {
                                            String binderCard = sc.nextLine();
                                            Cards cardToRemove = Helper.findCard(binderCard, binder.getCard());

                                            if (cardToRemove != null) {
                                                binder.removeCard(cardToRemove);
                                                System.out.println("Card has been removed");
                                            } else {
                                                System.out.println("Card is not found");
                                            }
                                        }
                                        break;

                                    case 3:
                                        binder.displayBinder();
                                        break;

                                    case 4:
                                        if (((LuxuryBinder) binder).getSaleValue() > 0) {
                                            System.out.printf("Binder has a total card price of %.2f%n", ((LuxuryBinder) binder).getCardValue());
                                            System.out.print("Enter custom price: ");
                                            double newPrice = sc.nextDouble();
                                            sc.nextLine();
                                            ((LuxuryBinder) binder).setCustomPrice(newPrice);
                                        }
                                        else {
                                            System.out.println("Binder is empty and has no value");
                                        }
                                        break;
                                    case 5:
                                        sellBinder(binder);
                                        binderChoice = 0;
                                        break;
                                    case 6:
                                        String answer;
                                        boolean isDone = false;
                                        do {
                                            System.out.println("Are you sure you want to delete " + binder.getName() + "?" + "(yes/no)");

                                            answer = sc.nextLine();

                                            if (answer.equalsIgnoreCase("yes")) {
                                                deleteBinder(binder);
                                                System.out.println("Cards have been returned to the collection");
                                                isDone = true;
                                                binderChoice = 0;
                                                name = "EXIT";
                                            } else if (answer.equalsIgnoreCase("no")) {
                                                isDone = true;
                                            } else {
                                                System.out.println("Invalid input");
                                            }
                                        } while (!isDone);
                                        break;
                                    case 7:
                                        binderChoice = 0;
                                        name = "exit";
                                        break;
                                    case 0:
                                        System.out.println("Exiting");
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                }
                            } while (binderChoice != 0);
                        }
                        else if (name.equalsIgnoreCase("exit")) {
                            System.out.println("Binder not found");
                        }
                    } while (!name.equalsIgnoreCase("EXIT") && !binders.isEmpty());
                } else if ((userChoice == 5 && existingDeck) || userChoice == 6 && existingDeck) {
                    String name = "";
                    do {

                        viewDecks();
                        if (!decks.isEmpty()) {
                            System.out.print("Enter deck name or EXIT to go back:");
                            name = sc.nextLine();
                            Decks deck = Helper.findDeck(name, decks);
                            if (deck != null && !(deck instanceof Sellable)) {
                                int deckChoice;
                                do {
                                    System.out.println("Deck: " + deck.getName());
                                    System.out.println("╔══════════════════════════════╗");
                                    System.out.println("║ [0] Exit                     ║");
                                    System.out.println("║ [1] Add Card                 ║");
                                    System.out.println("║ [2] Remove Card              ║");
                                    System.out.println("║ [3] View Cards               ║");
                                    System.out.println("║ [4] Delete Deck              ║");
                                    System.out.println("║ [5] Return to Main Menu      ║");
                                    System.out.println("╚══════════════════════════════╝");
                                    System.out.print("Enter choice:");
                                    deckChoice = sc.nextInt();
                                    sc.nextLine();
                                    switch (deckChoice) {
                                        case 1:
                                            collection.displayAllCards();
                                            if (!collection.getCard().isEmpty()) {
                                                System.out.println("Enter Card name: ");
                                                String deckCard = sc.nextLine();
                                                Cards cardToAdd = Helper.findCard(deckCard, collection.getCard());
                                                if (cardToAdd != null) {
                                                    if (Helper.confirmAction()) {
                                                        deck.addCard(cardToAdd);
                                                    }
                                                } else {
                                                    System.out.println("Card is not found");
                                                }
                                            } else {
                                                System.out.println("Collection is empty");
                                            }
                                            break;

                                        case 2:
                                            deck.displayDeck();
                                            if (!deck.getCard().isEmpty()) {
                                                String binderCard = sc.nextLine();
                                                Cards cardToRemove = Helper.findCard(binderCard, deck.getCard());

                                                if (cardToRemove != null) {
                                                    if (Helper.confirmAction()) {
                                                        deck.removeCard(cardToRemove);
                                                        System.out.println("Card has been removed");
                                                    }
                                                } else {
                                                    System.out.println("Card is not found");
                                                }
                                            }
                                            break;

                                        case 3:
                                            deck.displayDeck();
                                            if (!deck.getCard().isEmpty()) {
                                                String cardName;
                                                do {
                                                    System.out.print("Enter card name to view details and EXIT to go back: ");
                                                    cardName = sc.nextLine();
                                                    Cards card = Helper.findCard(cardName, deck.getCard());
                                                    if (card != null) {
                                                        Helper.displayDeckCard(cardName, deck.getCard());
                                                    } else if (!cardName.equalsIgnoreCase("exit")) {
                                                        System.out.println("Card not found");
                                                    }
                                                } while (!cardName.equalsIgnoreCase("exit"));
                                            }
                                            break;

                                        case 4:
                                            String answer;
                                            boolean isDone = false;
                                            do {
                                                System.out.println("Are you sure you want to delete " + deck.getName() + "? (yes/no)");
                                                answer = sc.nextLine();

                                                if (answer.equalsIgnoreCase("yes")) {
                                                    deleteDeck(deck);
                                                    isDone = true;
                                                    deckChoice = 0;
                                                    name = "EXIT";
                                                } else if (answer.equalsIgnoreCase("no")) {
                                                    isDone = true;
                                                } else {
                                                    System.out.println("Invalid input");
                                                }
                                            } while (!isDone);
                                            break;

                                        case 5:
                                            System.out.println("Returning to main menu...");
                                            deckChoice = 0;
                                            name = "exit";
                                            break;

                                        case 0:
                                            System.out.println("Exiting");
                                            break;

                                        default:
                                            System.out.println("Invalid choice");
                                    }
                                } while (deckChoice != 0);
                            } else if (deck != null && (deck instanceof Sellable)) {
                                int deckChoice;
                                do {
                                    System.out.println("Deck: " + deck.getName());
                                    System.out.println("╔══════════════════════════════╗");
                                    System.out.println("║ [0] Exit                     ║");
                                    System.out.println("║ [1] Add Card                 ║");
                                    System.out.println("║ [2] Remove Card              ║");
                                    System.out.println("║ [3] View Cards               ║");
                                    System.out.println("║ [4] Delete Deck              ║");
                                    System.out.println("║ [5] Sell Deck                ║");
                                    System.out.println("║ [6] Return to Main Menu      ║");
                                    System.out.println("╚══════════════════════════════╝");
                                    System.out.print("Enter choice:");
                                    deckChoice = sc.nextInt();
                                    sc.nextLine();
                                    switch (deckChoice) {
                                        case 1:
                                            collection.displayAllCards();
                                            if (!collection.getCard().isEmpty()) {
                                                System.out.println("Enter Card name: ");
                                                String deckCard = sc.nextLine();
                                                Cards cardToAdd = Helper.findCard(deckCard, collection.getCard());
                                                if (cardToAdd != null) {
                                                    if (Helper.confirmAction()) {
                                                        deck.addCard(cardToAdd);
                                                    }
                                                } else {
                                                    System.out.println("Card is not found");
                                                }
                                            } else {
                                                System.out.println("Collection is empty");
                                            }
                                            break;

                                        case 2:
                                            deck.displayDeck();
                                            if (!deck.getCard().isEmpty()) {
                                                String binderCard = sc.nextLine();
                                                Cards cardToRemove = Helper.findCard(binderCard, deck.getCard());

                                                if (cardToRemove != null) {
                                                    if (Helper.confirmAction()) {
                                                        deck.removeCard(cardToRemove);
                                                        System.out.println("Card has been removed");
                                                    }
                                                } else {
                                                    System.out.println("Card is not found");
                                                }
                                            }
                                            break;

                                        case 3:
                                            deck.displayDeck();
                                            if (!deck.getCard().isEmpty()) {
                                                String cardName;
                                                do {
                                                    System.out.print("Enter card name to view details and EXIT to go back: ");
                                                    cardName = sc.nextLine();
                                                    Cards card = Helper.findCard(cardName, deck.getCard());
                                                    if (card != null) {
                                                        Helper.displayDeckCard(cardName, deck.getCard());
                                                    } else if (!cardName.equalsIgnoreCase("exit")) {
                                                        System.out.println("Card not found");
                                                    }
                                                } while (!cardName.equalsIgnoreCase("exit"));
                                            }
                                            break;

                                        case 4:
                                            String answer;
                                            boolean isDone = false;
                                            do {
                                                System.out.println("Are you sure you want to delete " + deck.getName() + "? (yes/no)");
                                                answer = sc.nextLine();

                                                if (answer.equalsIgnoreCase("yes")) {
                                                    deleteDeck(deck);
                                                    isDone = true;
                                                    deckChoice = 0;
                                                    name = "EXIT";
                                                } else if (answer.equalsIgnoreCase("no")) {
                                                    isDone = true;
                                                } else {
                                                    System.out.println("Invalid input");
                                                }
                                            } while (!isDone);
                                            break;

                                        case 5:
                                            sellDeck(deck);
                                            deckChoice = 0;
                                            break;

                                        case 6:
                                            System.out.println("Returning to main menu...");
                                            deckChoice = 0;
                                            name = "exit";
                                            break;

                                        case 0:
                                            System.out.println("Exiting");
                                            break;

                                        default:
                                            System.out.println("Invalid choice");
                                    }
                                } while (deckChoice != 0 && !decks.isEmpty());
                            } else if (!name.equalsIgnoreCase("exit")) {
                                System.out.println("Deck is not found");
                            }

                        }
                    } while (!name.equalsIgnoreCase("EXIT") && !decks.isEmpty());

                } else if (userChoice == 0) {
                    System.out.println("Exiting");
                } else {
                    System.out.println("Invalid input");
                }

            } while (userChoice != 0);
        }
    }




/* Selling of binders almost done, decks is just the same. cannot sell if the value is lower than the total cards. */