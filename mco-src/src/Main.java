import com.Collection;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import com.*;
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

        // 2. Create binders
        ArrayList<Binders> binders = new ArrayList<>(Arrays.asList(
                new NonCuratedBinder("BinderA"),
                new PauperBinder("BinderB"),
                new RaresBinder("BinderC"),
                new LuxuryBinder("BinderD"),
                new CollectorBinder("BinderE")
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

        // 6. Final binder status
        System.out.println("\n📦 Final Binder Summary:");
        for (Binders binder : binders) {
            System.out.print("🔍 " + binder.getName() + ": ");
            if (binder.getCard().isEmpty()) {
                System.out.println("Empty.");
            } else {
                System.out.println(binder.getCard().size() + " card(s).");
            }
        }
        for (Binders b : binders) {
            System.out.println("📁 " + b.getName() + " contains " + b.getCard().size() + " card(s):");
            for (Cards card : b.getCard()) {
                System.out.println("   - " + card.getName());
            }
            System.out.println(); // add space between binders
        }
        tcis.setBinders(binders);
        Collection.addCard("alpha", "COMMON", "normal", 10);
        Collection.addCard("beta", "UNCOMMON", "normal", 15);
        Collection.addCard("gamma", "RARE", "EXTENDED-ART", 25);
        Collection.addCard("delta", "LEGENDARY", "FULL-ART", 40);
        Collection.addCard("epsilon", "RARE", "normal", 30);
        Collection.addCard("zeta", "LEGENDARY", "normal", 60);
        tcis.displayMenu();
    }
}
