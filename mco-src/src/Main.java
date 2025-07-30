import com.*;

import com.Collection;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        TCIS tcis = new TCIS();

        // 1. Add cards to collection
        Collection.addCard("alpha", "COMMON", "normal", 10);
        Collection.addCard("beta", "UNCOMMON", "normal", 15);
        Collection.addCard("gamma", "RARE", "EXTENDED-ART", 25);
        Collection.addCard("delta", "LEGENDARY", "FULL-ART", 40);
        Collection.addCard("epsilon", "RARE", "normal", 30);
        Collection.addCard("zeta", "LEGENDARY", "normal", 60);
        Collection.addCard("eta", "COMMON", "normal", 5);
        Collection.addCard("theta", "UNCOMMON", "normal", 7);
        Collection.addCard("iota", "RARE", "EXTENDED-ART", 22);
        Collection.addCard("kappa", "LEGENDARY", "FULL-ART", 45);
        Collection.addCard("lambda", "RARE", "normal", 20);
        Collection.addCard("mu", "LEGENDARY", "normal", 50);
        Collection.addCard("nu", "COMMON", "normal", 8);
        Collection.addCard("xi", "UNCOMMON", "normal", 12);
        Collection.addCard("omicron", "RARE", "EXTENDED-ART", 28);
        Collection.addCard("pi", "LEGENDARY", "FULL-ART", 48);
        Collection.addCard("rho", "RARE", "normal", 18);
        Collection.addCard("sigma", "LEGENDARY", "normal", 55);

        // 2. Create binders
        ArrayList<Binders> binders = new ArrayList<>(Arrays.asList(
                new NonCuratedBinder("NonCuratedBinderA"),
                new PauperBinder("PauperBinderB"),
                new RaresBinder("RaresBinderC"),
                new LuxuryBinder("LuxuryBinderD"),
                new CollectorBinder("CollectorBinderE")
        ));

        // 3. Loop through a copy of the card list to avoid ConcurrentModificationException
        for (Cards card : new ArrayList<>(tcis.getCollection().getCard())) {
            System.out.println("\n🎴 Checking card: " + card.getName());

            for (Binders binder : binders) {
                String binderType = binder.getClass().getSimpleName();
                boolean isValid = false;
                String rarity = card.getRarity();
                String variant = card.getVariant();

                // 4. Inline binder validation logic
                if (binderType.equals("NonCuratedBinder")) {
                    isValid = true;
                } else if (binderType.equals("PauperBinder")) {
                    isValid = rarity.equals("COMMON") || rarity.equals("UNCOMMON");
                } else if (binderType.equals("RaresBinder")) {
                    isValid = rarity.equals("RARE") || rarity.equals("LEGENDARY");
                } else if (binderType.equals("LuxuryBinder")) {
                    isValid = !variant.equals("NORMAL");
                } else if (binderType.equals("CollectorBinder")) {
                    isValid = (rarity.equals("RARE") || rarity.equals("LEGENDARY")) &&
                            !variant.equals("NORMAL");
                }

                // 5. Add if valid and display result
                System.out.print("👉 " + binder.getName() + " (" + binderType + "): ");
                if (isValid) {
                    binder.addCard(card);
                } else {
                    System.out.println("Card not valid for this binder.");
                }
            }
        }

        // 6. Add cards to decks (9 cards in total)
        ArrayList<Decks> decks = new ArrayList<>(Arrays.asList(
                new SellableDeck("SellableDeck1"),
                new SellableDeck("SellableDeck2"),
                new Decks("NormalDeck1")
        ));

        for (int i = 0; i < 9; i++) {
            String cardName = "card" + i;
            String rarity = i % 2 == 0 ? "RARE" : "COMMON";
            String variant = i % 2 == 0 ? "EXTENDED-ART" : "NORMAL";
            double value = 10 + (i * 5);
            Collection.addCard(cardName, rarity, variant, value);

            // Add cards to Decks
            if (i < 5) {
                decks.get(0).addCard(new Cards(cardName, rarity, variant, value)); // Add first 5 cards to SellableDeck1
            } else if (i < 9) {
                decks.get(1).addCard(new Cards(cardName, rarity, variant, value)); // Add next 4 cards to SellableDeck2
            } else {
                decks.get(2).addCard(new Cards(cardName, rarity, variant, value)); // Add last 1 card to NormalDeck1
            }
        }

        // 7. Final binder and deck status
        System.out.println("\n📦 Final Binder Summary:");
        for (Binders binder : binders) {
            System.out.print("🔍 " + binder.getName() + ": ");
            if (binder.getCard().isEmpty()) {
                System.out.println("Empty.");
            } else {
                System.out.println(binder.getCard().size() + " card(s).");
            }
        }

        System.out.println("\n📂 Final Deck Summary:");
        for (Decks deck : decks) {
            System.out.print("🔍 " + deck.getName() + ": ");
            if (deck.getCard().isEmpty()) {
                System.out.println("Empty.");
            } else {
                System.out.println(deck.getCard().size() + " card(s).");
            }
        }

        // 8. Set binders and decks
        tcis.setBinders(binders);
        tcis.setDecks(decks);

        // 9. Show the money
        TCIS.showMoney();

        // 10. Display menu
        tcis.displayMenu();
    }
}
